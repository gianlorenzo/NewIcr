package it.uniroma3.icr.dao.impl;

import it.uniroma3.icr.dao.SymbolDao;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Symbol;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Repository
public class SymbolDaoImpl {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SessionFactory sessionFactory;

    public void insertSymbol(Symbol symbol) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(symbol);
        session.getTransaction().commit();
        session.close();
    }


    public void insertSymbols(String p, Manuscript manuscript) throws FileNotFoundException, IOException {
        File[] files = new File(p).listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                String typeSymbol = files[i].getName();
                File[] transcriptionsSymbol = files[i].listFiles();
                for (int j = 0; j < transcriptionsSymbol.length; j++) {
                    if (transcriptionsSymbol[j].isDirectory()) {
                        String transcriptionSymbol = transcriptionsSymbol[j].getName();
                        File[] images = transcriptionsSymbol[j].listFiles();
                        if (!images[0].getName().equals(".DS_Store")) {
                            File image = images[0];
                            String nameComplete = image.getName();
                            String name = FilenameUtils.getBaseName(nameComplete);
                            String parts[] = name.split("_");
                            int width = Integer.valueOf(parts[0]);
                            BufferedInputStream in = null;
                            try {
                                String transcription = transcriptionSymbol;
                                String type = typeSymbol;
                                Symbol symbol = new Symbol(transcription, type, manuscript, width);
                                manuscript.addSymbol(symbol);
                                this.insertSymbol(symbol);
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
