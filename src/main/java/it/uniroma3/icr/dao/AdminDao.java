package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends JpaRepository<Administrator,Long>{
	public Administrator findByUsername(String username);
}
