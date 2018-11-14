package it.uniroma3.icr.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import it.uniroma3.icr.dao.impl.ImageDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.ImageDao;
import it.uniroma3.icr.insertImageInDb.GetImagePath;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Manuscript;

@Service
public class ImageService {
	@Autowired
	private GetImagePath getImagePath;

	@Autowired
	private ImageDao imageDao;

	@Autowired
	private ImageDaoImpl imageDaoImpl;

	public void getListImageProperties(String p, Manuscript manuscript) throws FileNotFoundException, IOException {
		this.imageDaoImpl.insertImage(p,manuscript);
	}

	public void updateAllImages(String p, Manuscript manuscript) throws IOException {
		this.imageDaoImpl.updateImagesAll(p,manuscript);
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
