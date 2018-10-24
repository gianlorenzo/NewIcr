package it.uniroma3.icr.dao;

import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.model.TaskWrapper;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultDaoCustom {
	public void addImageAdnTaskToResult(Task t, Result r, Job j);
	public void updateListResult(TaskWrapper taskResults);
}
