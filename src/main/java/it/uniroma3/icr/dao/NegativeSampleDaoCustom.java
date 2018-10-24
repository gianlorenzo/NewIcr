package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.Sample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NegativeSampleDaoCustom {
	public List<Sample> findAllNegativeSamplesBySymbolId(long id);
}
