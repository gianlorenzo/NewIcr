package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.icr.model.Job;

import java.util.List;

@Repository
public interface JobDao extends JpaRepository<Job,Long>{

}
