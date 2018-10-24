package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleDao extends JpaRepository<Sample,Long>, SampleDaoCustom {

}
