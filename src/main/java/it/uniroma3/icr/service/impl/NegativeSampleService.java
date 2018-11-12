package it.uniroma3.icr.service.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

import it.uniroma3.icr.dao.impl.NegativeSampleDaoImpl;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.NegativeSampleDao;
import it.uniroma3.icr.dao.SymbolDao;
import it.uniroma3.icr.insertImageInDb.GetNegativeSamplePath;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.NegativeSample;
import it.uniroma3.icr.model.Sample;
import it.uniroma3.icr.model.Symbol;

@Service
public class NegativeSampleService {
	@Autowired
	private NegativeSampleDaoImpl negativeSampleDaoImpl;
	@Autowired
	public NegativeSampleDao negativeSampleDao;
	@Autowired 
	public SymbolDao symbolDao;
	@Autowired
	private GetNegativeSamplePath negativeSamplePath;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    public void getNegativeSampleImage(String p, Manuscript manuscript) throws FileNotFoundException, IOException {
		this.negativeSampleDaoImpl.insertNegativeSamples(p,manuscript);
	}

	public List<Sample> findAllNegativeSamplesBySymbolId(long id){
		return negativeSampleDao.findAllNegativeSamplesBySymbolId(id);
	}
	public String getNegativePath() {
		return this.negativeSamplePath.getPath();
	}
	public List<Manuscript> getNegativeManuscript() throws FileNotFoundException, IOException {
		return this.negativeSamplePath.getManuscript();
	}
}
