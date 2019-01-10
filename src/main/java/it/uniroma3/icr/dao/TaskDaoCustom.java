package it.uniroma3.icr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import it.uniroma3.icr.model.Task;

@Repository
public interface TaskDaoCustom {

    public void updateEndDate(Task t);

    public List<Object> taskTimes();

}
