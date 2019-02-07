package it.uniroma3.icr.dao;

import java.util.List;

import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Student;
import org.springframework.stereotype.Repository;
import it.uniroma3.icr.model.Task;

@Repository
public interface TaskDaoCustom {

    public void updateEndDate(Task t);

    public List<Object> taskTimes();

    public Task assignTaskId(Student student, Image image);


}
