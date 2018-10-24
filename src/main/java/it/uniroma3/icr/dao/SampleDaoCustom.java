package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.Sample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleDaoCustom {
	public List<Sample> findAllSamplesBySymbolId(long id);

}
