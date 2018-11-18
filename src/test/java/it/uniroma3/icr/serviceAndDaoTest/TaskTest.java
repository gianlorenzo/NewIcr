package it.uniroma3.icr.serviceAndDaoTest;

import it.uniroma3.icr.dao.TaskDao;
import it.uniroma3.icr.dao.impl.TaskDaoImpl;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.service.impl.StudentService;
import it.uniroma3.icr.service.impl.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTest {

    private Task task;

    private Job job;

    private Boolean word;

    private Integer number;

    private Student student;

    private Student student2;


    private List<Result> results;

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskDaoImpl taskDaoImpl;

    @MockBean
    private TaskDao taskDao;

    @Before
    public void setUp() {
        task = new Task();
        task.setId(new Long(1));
        results = new ArrayList<>();
        task.setResults(results);
        job = new Job();
        word = new Boolean(true);
        number = new Integer(1);
        student = new Student();
        student.setUsername("username1");
        student2 = new Student();
        student2.setUsername("username2");
        Mockito.when(taskDao.findOne(new Long(1))).thenReturn(task);
    }

    @Test
    public void whenValidId_thenTaskShouldBeFound() {
        Long id = new Long(1);
        Task found = taskService.retrieveTask(id);
        assertThat(found.getId())
                .isEqualTo(id);
    }

    @Test
    public void createTask() {
        TaskService taskServiceMock = mock(TaskService.class);
        taskServiceMock.createTask(job,number,word,task);
    }

    @Test
    public void assignTask() {
        TaskService taskServiceMock = mock(TaskService.class);
        taskServiceMock.assignTask(student);
    }

    @Test
    public void updateStudent() {
        TaskService taskServiceMock = mock(TaskService.class);
        taskServiceMock.assignTask(student);
        taskServiceMock.updateStudent(student2);
    }

}
