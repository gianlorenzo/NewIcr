package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDao extends JpaRepository<Job,Long>{

}
