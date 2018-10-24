package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultDao extends JpaRepository<Result, Long>, ResultDaoCustom{

	public List<Result> findByTask(Task task);
	
}
