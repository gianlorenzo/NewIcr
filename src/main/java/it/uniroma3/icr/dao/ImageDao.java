package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDao extends JpaRepository<Image,Long>, ImageDaoCustom{
	
	


}
