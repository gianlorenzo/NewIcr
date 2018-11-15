package it.uniroma3.icr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.StudentDaoSocial;
import it.uniroma3.icr.model.StudentSocial;

@Service
public class StudentServiceSocial {

	@Autowired
	private StudentDaoSocial studentDaoSocial;

	public void saveUser(StudentSocial user) {
		studentDaoSocial.save(user);
	}

	public StudentSocial findById(Long id) { return this.studentDaoSocial.findById(id);}

	public StudentSocial findUser(String username) {
		return this.studentDaoSocial.findByUsername(username);
	}

	public List<StudentSocial> retrieveAllStudents() {
		return this.studentDaoSocial.findAll();
	}

	public StudentSocial findUserBySurname(String surname) {
		return this.studentDaoSocial.findBySurname(surname);
	}

	public StudentSocial findOne(Long id) { return this.studentDaoSocial.findDistinctById(id);}

}
