package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDaoCustom {

	public void updateEndDate(Task t);

}
