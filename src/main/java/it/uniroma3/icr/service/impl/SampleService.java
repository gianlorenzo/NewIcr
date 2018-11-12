package it.uniroma3.icr.service.impl;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


import it.uniroma3.icr.dao.impl.SampleDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.SampleDao;
import it.uniroma3.icr.insertImageInDb.GetSamplePath;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Sample;

@Service
public class SampleService {
	@Autowired
	private SampleDao sampleDao;
	@Autowired
    private SampleDaoImpl sampleDaoImpl;
	@Autowired
	private GetSamplePath getSamplePath ;
	
	public void getSampleImage(String p, Manuscript manuscript) throws FileNotFoundException, IOException {
		this.sampleDaoImpl.insertSamples(p,manuscript);
	}
	public List<Sample> findAllSamplesBySymbolId(long id){
		return sampleDao.findAllSamplesBySymbolId(id);
	}
	public String getPath() {
		return this.getSamplePath.getPath();
	}
    public List<Manuscript> getManuscript() throws FileNotFoundException, IOException {
    	return this.getSamplePath.getManuscript();
    }
}
