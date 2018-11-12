package it.uniroma3.icr.service.impl;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import it.uniroma3.icr.dao.impl.SymbolDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.SymbolDao;
import it.uniroma3.icr.insertImageInDb.GetSamplePath;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Symbol;

@Service
public class SymbolFacade {

	@Autowired
	private SymbolDao symbolDao;

	@Autowired
    private SymbolDaoImpl symbolDaoImpl;

	@Autowired
	private GetSamplePath getSamplePath;
	
	public void insertSymbolInDb(String p, Manuscript manuscript) throws FileNotFoundException, IOException {
       this.symbolDaoImpl.insertSymbols(p,manuscript);
	}

	public Symbol retrieveSymbol(long id) {
		return this.symbolDao.findOne(id);
	}
	public List<Symbol> retrieveAllSymbols() {
		return this.symbolDao.findAll();
	}
	public List<Symbol> findSymbolByManuscriptName(String manuscript) {
		return this.symbolDao.findByManuscriptName(manuscript);
	}
	public String getPath() {
		return this.getSamplePath.getPath();
	}
    public List<Manuscript> getManuscript() throws FileNotFoundException, IOException {
    	return this.getSamplePath.getManuscript();
    }
}
