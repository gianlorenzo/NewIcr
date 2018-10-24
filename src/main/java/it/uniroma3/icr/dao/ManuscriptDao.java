package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.Manuscript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManuscriptDao extends JpaRepository<Manuscript, Long>{
	public Manuscript findByName(String name);
}
