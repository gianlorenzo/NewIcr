package it.uniroma3.icr.serviceAndDaoTest;

import it.uniroma3.icr.dao.TaskDao;
import it.uniroma3.icr.dao.impl.TaskDaoImpl;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.service.impl.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    private Result result;

    private List<Result> results;

    private java.sql.Timestamp startDate;

    private java.sql.Timestamp endDate;

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskDaoImpl taskDaoImpl;

    @MockBean
    private TaskDao taskDao;

    @Before
    public void setUp() {
        startDate = new java.sql.Timestamp(Long.valueOf("2018-08-02 15:22:38"));
        endDate = new java.sql.Timestamp(Long.valueOf("2018-12-22 16:22:38"));
        task = new Task();
        task.setId(new Long(1));
        result = new Result();
        result.setId(new Long(4));
        task.setResult(result);
        job = new Job();
        word = new Boolean(true);
        number = new Integer(1);
        student = new Student();
        student.setId(new Long(3));
        student.setUsername("username1");
        task.setStudent(student);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        results = new ArrayList<>();
        results.add(result);
        Mockito.when(taskDao.findOne(new Long(1))).thenReturn(task);
        Mockito.when(taskDaoImpl.findStudentIdOnTask(task)).thenReturn(student.getId());
        Mockito.when(taskDaoImpl.findTaskOneResult(task, student)).thenReturn(result);
        Mockito.when(taskDaoImpl.findTaskResult(task, student)).thenReturn(results);
    }

    @Test
    public void whenValidId_thenTaskShouldBeFound() {
        Long id = new Long(1);
        Task found = taskService.retrieveTask(id);
        assertThat(found.getId())
                .isEqualTo(id);
    }

    @Test
    public void findStudentIdByTask() {
        Long id = new Long(3);
        Long found = taskService.findStudentIdOnTask(task);
        assertThat(found)
                .isEqualTo(id);
    }

    @Test
    public void findTaskResult() {
        Long id = new Long(4);
        Result found = taskService.findTaskOneResult(task, student);
        assertThat(found.getId()).isEqualTo(id);
    }

    @Test
    public void findTaskResults() {
        List<Result> found = taskService.findTaskResult(task, student);
        assertThat(found.size()).isEqualTo(results.size());
    }

    @Test
    public void createTask() {
        TaskService taskServiceMock = mock(TaskService.class);
        taskServiceMock.createTask(job, number, word, task);
    }

    @Test
    public void assignTask() {
        TaskService taskServiceMock = mock(TaskService.class);
        taskServiceMock.assignTask(student);
    }


}
