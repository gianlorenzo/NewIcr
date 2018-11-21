package it.uniroma3.icr.service.impl;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.uniroma3.icr.dao.impl.TaskDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.icr.dao.TaskDao;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.Task;

@Service
public class TaskService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private TaskDaoImpl taskDaoImpl;

	@Autowired
	private ResultService resultService;

	@PersistenceContext
	private EntityManager entityManager;

	public void saveTask(Task t) {
		taskDao.save(t);
	}

	public Task retrieveTask(Long id) {
		return this.taskDao.findOne(id);
	}

	public List<Task> retrieveAllTask() {
		return this.taskDao.findAll();
	}

	public List<Task> findTaskByStudent(Long id) {
		return this.taskDao.findByStudentId(id);
	}

    public List<Task> findTaskByStudentSocial(Long id) {
		return this.taskDaoImpl.findTaskByStudentSocial(id);
    }

	public Task assignTask(Student student) {
		return this.taskDaoImpl.assignTask(student);
	}

	public String findHintByTask(Task t) {
		return this.taskDaoImpl.findHintByTask(t);
	}

	public void updateEndDate(Task t) {
		taskDao.updateEndDate(t);
	}

	@Transactional
	public void createTask(Job job, Integer number, Boolean word, Task task) {
		for (int i = 0; i < job.getNumberOfStudents(); i++) {
			int batchNumber = 0;
			for (int r = 0; r < number; r++) {

				if ((r % job.getTaskSize()) == 0) {
					task = new Task();
					task.setBatch(batchNumber);
					task.setJob(job);
					job.addTask(task);
					this.saveTask(task);
					batchNumber++;
				}
				Image j = job.getImages().get(r);
				Result result = new Result();
				result.setImage(j);
				result.setTask(task);
				this.resultService.addResult(result);
			}
		}

	}

	@Transactional
	public void updateStudent(Student student) {
		this.taskDaoImpl.updateStudent(student);
	}

	public List<Result> findTaskResult(Task task, Student student) {
		return this.taskDaoImpl.findTaskResult(task,student);
	}

	public Result findTaskOneResult(Task task, Student student) {
		return this.taskDaoImpl.findTaskOneResult(task,student);
	}

	public Long findStudentIdOnTask(Task task) {
		return this.taskDaoImpl.findStudentIdOnTask(task);
	}

	public long getWorkTime(Student student) {
		return this.taskDaoImpl.getWorkTime(student);
	}

}
