package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.StudentSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDaoSocial extends JpaRepository<StudentSocial,Long> {
	public StudentSocial findById(Long id);
	public StudentSocial findByUsername(String username);
	public StudentSocial findBySurname(String surname);
}
