package it.uniroma3.icr.dao.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.uniroma3.icr.dao.SymbolDao;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Symbol;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.icr.dao.SampleDaoCustom;
import it.uniroma3.icr.model.Sample;

@Repository
public class SampleDaoImpl implements SampleDaoCustom {

    @Autowired
    private SymbolDao symbolDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<Sample> findAllSamplesBySymbolId(long id) {
        String s = "FROM Sample s Where s.symbol.id = :id";
        Query query = this.entityManager.createQuery(s);
        query.setParameter("id", id);
        List<Sample> samples = query.getResultList();
        return samples;
    }

    public void insertSamples(String p, Manuscript manuscript) throws FileNotFoundException, IOException {
        File[] files = new File(p).listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                String typeSymbol = files[i].getName();
                File[] transcriptionsSymbol = files[i].listFiles();
                for (int j = 0; j < transcriptionsSymbol.length; j++) {
                    if (transcriptionsSymbol[j].isDirectory()) {
                        String transcriptionSymbol = transcriptionsSymbol[j].getName();
                        File[] images = transcriptionsSymbol[j].listFiles();
                        for (int m = 0; m < images.length; m++) {
                            if (!images[m].getName().equals(".DS_Store")) {
                                String nameComplete = images[m].getName();
                                String pathFile = images[m].getPath().replace("\\", "/");
                                String name = FilenameUtils.getBaseName(nameComplete);
                                String parts[] = name.split("_");
                                int width = Integer.valueOf(parts[0]);
                                int x = Integer.valueOf(parts[1]);
                                int y = Integer.valueOf(parts[2]);
                                BufferedInputStream in = null;
                                try {
                                    BufferedImage f = ImageIO.read(images[m]);
                                    Symbol s = this.symbolDao.findByTranscriptionAndManuscriptName(transcriptionSymbol, manuscript.getName());
                                    int height = f.getHeight();
                                    int xImg = x;
                                    int yImg = y;
                                    String path = pathFile.substring(pathFile.indexOf("img"), pathFile.length());
                                    String type = typeSymbol;
                                    Sample sample = new Sample(width, height, xImg, yImg, manuscript,
                                            type, path);
                                    sample.setSymbol(s);
                                    manuscript.addSample(sample);
                                } finally {
                                    if (in != null) {
                                        try {
                                            in.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
