package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<Task, Long>, TaskDaoCustom{
	public List<Task> findByStudentIsNull();
	public List<Task> findByStudentId(Long id);
}
