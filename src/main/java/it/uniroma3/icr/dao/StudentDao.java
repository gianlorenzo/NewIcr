package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends JpaRepository<Student,Long> {
	public Student findById(Long id);
	public Student findByUsername(String username);
	public Student findBySurname(String surname);
}
