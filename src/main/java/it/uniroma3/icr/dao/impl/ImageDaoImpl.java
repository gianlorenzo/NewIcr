package it.uniroma3.icr.dao.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.uniroma3.icr.model.Manuscript;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.icr.dao.ImageDaoCustom;
import it.uniroma3.icr.model.Image;

@Repository
@Transactional(readOnly = false)
public class ImageDaoImpl implements ImageDaoCustom {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<Image> findImageForTypeAndManuscriptName(String type, String manuscript, int limit) {
        String s = "FROM Image i WHERE i.type = :type and i.manuscript = :manuscript";
        Query query = entityManager.createNativeQuery(s, Image.class).setMaxResults(limit);
        query.setParameter("type", type);
        query.setParameter("manuscript", manuscript);
        List<Image> images = query.getResultList();
        return images;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Image> findImageFromManuscriptName(long manuscript) {
        String s = "SELECT * FROM Image i WHERE i.manuscript_id = :manuscript";
        Query query = entityManager.createNativeQuery(s, Image.class);
        query.setParameter("manuscript", manuscript);
        List<Image> images = query.getResultList();
        return images;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> findAllManuscript() {
        String s = "SELECT distinct manuscript FROM Image";
        return entityManager.createQuery(s).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] countImage() {
        String s = "select count (*), type,width from image group by type,width";
        Query query = entityManager.createQuery(s);
        List<Image> images = query.getResultList();
        Object[] objectList = images.toArray();
        return objectList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> findAllPages() {
        String s = "SELECT distinct page FROM Image";
        Query query = entityManager.createQuery(s);
        List<String> pages = query.getResultList();
        return pages;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Image> findImageForTypeAndWidthAndManuscript(String type, String manuscript, int width, int limit) {
        String s = "FROM Image i WHERE i.type = :type and i.width = :width and i.manuscript = :manuscript";

        Query query = entityManager.createQuery(s);
        query.setParameter("type", type);
        query.setParameter("width", width);
        query.setParameter("manuscript", manuscript);

        List<Image> images = query.setMaxResults(limit).getResultList();
        return images;
    }

    public void insertImage(String p, Manuscript manuscript) throws FileNotFoundException, IOException {
        File file = new File(p);
        File[] subFiles = file.listFiles();
        for (int i = 0; i < subFiles.length; i++) {
            if (subFiles[i].isDirectory()) {
                String page = subFiles[i].getName();
                File[] rows = subFiles[i].listFiles();
                for (int m = 0; m < rows.length; m++) {
                    if (rows[m].isDirectory()) {
                        String row = rows[m].getName();
                        File[] words = rows[m].listFiles();
                        for (int y = 0; y < words.length; y++) {
                            if (words[y].isDirectory()) {
                                File[] files = words[y].listFiles();
                                if (files[1].isDirectory()) {
                                    File[] images = files[1].listFiles(); // prendo solo la cartella cut_point_view
                                    for (int z = 0; z < images.length; z++) {
                                        if (!images[z].getName().equals(".DS_Store")) {
                                            String image = FilenameUtils.getBaseName(images[z].getName());
                                            String path = images[z].getPath();
                                            path = path.substring(path.indexOf("img"), path.length());
                                            LOGGER.info("pathInsertImage" + path);
                                            Image img = new Image();
                                            this.updateImage(img, image, manuscript, page, row, path);
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

    public Image updateImage(Image img, String name, Manuscript manuscript, String page, String row,
                             String path) {
        // divido il path delle cartelle e lo rigiro per ottenere sempre per prime le
        // info importanti
        img.setManuscript(manuscript);
        img.setPage(page);
        img.setRow(row);
        img.setPath(path);
        manuscript.addImage(img);
        return img;
    }

    public void updateImagesAll(String p, Manuscript manuscript) throws IOException {
        File file = new File(p);
        File[] subFiles = file.listFiles();
        for (int i = 0; i < subFiles.length; i++) {
            if (subFiles[i].isDirectory()) {
                String page = subFiles[i].getName();
                String row = subFiles[i].getName();
                File[] images = subFiles[i].listFiles();
                for (int z = 0; z < images.length; z++) {
                    if (!images[z].getName().equals(".DS_Store")) {
                        String image = FilenameUtils.getBaseName(images[z].getName());
                        String path = images[z].getPath().replace("\\", "/");
                        path = path.substring(path.indexOf("img"), path.length());
                        Image img = new Image();
                        this.updateImage(img, image, manuscript, page, row, path);

                    }
                }

            }
        }
    }
}











