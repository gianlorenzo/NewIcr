package it.uniroma3.icr.service.impl;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	private ResultService resultService;

	@PersistenceContext
	private EntityManager entityManager;

	public void saveTask(Task t) {
		taskDao.save(t);
	}

	public Task retrieveTask(long id) {
		return this.taskDao.findOne(id);
	}

	public List<Task> retrieveAllTask() {
		return this.taskDao.findAll();
	}

	public List<Task> findTaskByStudent(Long id) {
		return this.taskDao.findByStudentId(id);
	}

	@SuppressWarnings("unchecked")
	public List<Task> findTaskByStudentSocial(Long id) {

		String s = "SELECT t FROM Task t WHERE t.studentsocial.id='" + id + "'";
		Query querys = this.entityManager.createQuery(s);
		List<Task> tasks = querys.getResultList();
		return tasks;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public Task assignTask(Student student) {
		Task task = null;
		String select = null;

		select = "SELECT t "
				+ "FROM Task t "
				+ "WHERE (t.student.id= ?1 AND t.endDate IS NULL AND t.startDate IS NOT NULL)"; // task assegnati allo studente ma lasciati in sospeso 
		Query query1 = this.entityManager.createQuery(select).setParameter(1, student.getId());
		List<Task> taskList = query1.getResultList(); // trova il task da eseguire

		if (taskList.size()!=0) {
			task = taskList.get(0);
			task.setStudent(student);
			LOGGER.info("0 - Resumed task " + task.getId() + " for student " + student.getId());
			return task;
		}
		else {
			select = "SELECT t FROM Task t "
					+ "WHERE (t.batch, t.job.id) not in ( "                               // task già fatti dallo studente
						+ " SELECT distinct t2.batch, t2.job.id "  
						+ " FROM Task t2 "
						+ " WHERE t2.student.id= ?1 and t2.endDate IS NOT NULL) "
					+ "AND (t.student.id IS NULL)"; // task non assegnati
			
			query1 = this.entityManager.createQuery(select).setMaxResults(53).setParameter(1, student.getId());
			taskList = query1.getResultList(); // trova il task da eseguire

			if (taskList.size()!=0) {  // ci sono ancora task
				int position = (int)(student.getId() % taskList.size());
				LOGGER.info("0 - New task " + taskList.get(position).getId() + " to student " + student.getId() );

				Calendar calendar = Calendar.getInstance();
				java.util.Date now = calendar.getTime();
				java.sql.Timestamp date = new java.sql.Timestamp(now.getTime());

				LOGGER.debug("SQL UPDATE update task set start_date = "+ date + " student_id = " + student.getId() + " where id = " + taskList.get(position));

				String update = "update task set start_date = ?1, student_id = ?2 where id = ?3 and student_id is null";
				query1 = this.entityManager.createNativeQuery(update).setParameter(1, date).setParameter(2, student.getId()).setParameter(3, taskList.get(position).getId());
				int numRow = query1.executeUpdate();	
				if (numRow==1) {		
					task = taskList.get(position);
					task.setStudent(student);
					task.setStartDate(date);
					student.addTask(task); 
				}
				else {
					LOGGER.info("0.1 - race on task " + taskList.get(position) + ". Try another task for student " + student.getId());
					task = assignTask(student);
				}
			}
			else { // i task sono finiti
				return null;
			}
		}
		return task;
	}

	@SuppressWarnings("unchecked")
	public String findHintByTask(Task t) {
		String sr1 = "select answer, count(*) c from result where task_id in (select id from task where batch = ?1 and job_id = ?2) and answer is not null group by answer HAVING count(*) >= ?3 order by c";

		Query query1 = this.entityManager.createNativeQuery(sr1).setMaxResults(1).setParameter(1, t.getBatch())
				.setParameter(2, t.getJob().getId());
		if (t.getJob().isTutorial())
			query1.setParameter(3, 1);
		else
			query1.setParameter(3, 5);
		List<Object[]> temp = query1.getResultList();
		String hint = "";
		if (temp.size() > 0)
			hint = (String) temp.get(0)[0];
		return hint;
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

		String update = "update student set task_effettuati = ?1, tempo_effettuato = ?2 where id = ?3";
		Query query1 = this.entityManager.createNativeQuery(update).setParameter(1, student.getTaskEffettuati()).setParameter(2, student.getTempoEffettuato()).setParameter(3, student.getId());
		if (query1.executeUpdate()!=1)
			LOGGER.debug("PROBLEM IN SQL UPDATE: update student set task_effettuati " + student.getTaskEffettuati() + ", tempo_effettuato = " + student.getTempoEffettuato() +" where id = " + student.getId());
	}

	public List<Result> findTaskResult(Task task, Student student) {
		LOGGER.debug("SQL SELECT r RESULT FROM RESULT WHERE TASK_id = " + task.getId());
		if (task.getStudent().getId()!=student.getId())
			LOGGER.debug("PROBLEM TASK " + task.getId() + " STDUDENT PROBLEM "+ task.getStudent().getId() + " " + student.getId());

		String select = "select r from Result r where r.task.id = ?1";
		Query query1 = this.entityManager.createQuery(select).setParameter(1, task.getId());
		@SuppressWarnings("unchecked")
		List<Result> resultList = query1.getResultList(); // trova i result del task
		return resultList;	
	}

	public Long findStudentIdOnTask(Task task) {
		LOGGER.debug("SQL SELECT t TASK FROM TASK WHERE id = " + task.getId());

		String select = "select t.student.id from Task t where t.id = ?1";
		Query query1 = this.entityManager.createQuery(select).setParameter(1, task.getId());

		Long id = (Long)query1.getSingleResult(); // trova i result del task
		LOGGER.debug("STUDENT FROM DB: = " + id + " for task " + task.getId());
		return id;	
	}
	
	public long getWorkTime(Student student) {
		String select = "select sum(EXTRACT(EPOCH FROM (end_date-start_date))) from task where student_id = ?1 and EXTRACT(EPOCH FROM (end_date-start_date)) <=300";
		Query query = this.entityManager.createNativeQuery(select).setParameter(1, student.getId());
		long seconds = Double.valueOf((Double)query.getSingleResult()).longValue();
		
		select = "select count(*) from task where student_id = ?1 and EXTRACT(EPOCH FROM (end_date-start_date)) >300";
		query = this.entityManager.createNativeQuery(select).setParameter(1, student.getId());
		long secondsIdleTasks = ((BigInteger)query.getSingleResult()).longValue();
		
		return seconds + secondsIdleTasks;
	}
	
}
