package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.NegativeSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegativeSampleDao extends JpaRepository<NegativeSample, Long>, NegativeSampleDaoCustom{
	
	
}
