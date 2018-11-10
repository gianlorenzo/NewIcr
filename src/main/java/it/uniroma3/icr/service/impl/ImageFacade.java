package it.uniroma3.icr.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.icr.dao.ImageDao;
import it.uniroma3.icr.insertImageInDb.utils.GetImagePath;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Manuscript;

@Service
public class ImageFacade {
	@Autowired
	private GetImagePath getImagePath;

	@Autowired
	private ImageDao imageDao;

	public void getListImageProperties(String p, Manuscript manuscript) throws FileNotFoundException, IOException {
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
                                            path = path.substring(path.indexOf("/static") + 8, path.length());
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

	@Transactional
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
                        path = path.substring(path.indexOf("img") , path.length());
                        Image img = new Image();
                        this.updateImage(img, image, manuscript, page, row, path);

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
		//img.setWord(word);
		img.setPath(path);
		manuscript.addImage(img);
		return img;
	}

	public void addImage(Image i) {
		imageDao.save(i);
	}

	public Image retrieveImage(long id) {
		return this.imageDao.findOne(id);
	}

	public List<Image> retrieveAllImages() {
		return this.imageDao.findAll();
	}

	public List<Image> getImagesForTypeAndManuscriptName(String type, String manuscript, int limit) {
		return this.imageDao.findImageForTypeAndManuscriptName(type, manuscript, limit);
	}
	
	public List<Image> getImagesFromManuscriptName(long manuscript) {
		return this.imageDao.findImageFromManuscriptName(manuscript);
	}

	public List<Image> findImageForTypeAndWidthAndManuscript(String type, String manuscript, int width, int limit) {
		return this.imageDao.findImageForTypeAndWidthAndManuscript(type, manuscript, width, limit);
	}

	public List<Manuscript> getManuscript() throws FileNotFoundException, IOException {
		return this.getImagePath.getManuscript();
	}

	public String getPath() throws FileNotFoundException, IOException {
		return this.getImagePath.getPath();
	}

	public List<String> findAllPages() {
		return this.imageDao.findAllPages();
	}

	public Object[] countImage() {
		return this.countImage();
	}

}
