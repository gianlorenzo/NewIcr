package it.uniroma3.icr.dao.impl;


import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.icr.dao.TaskDaoCustom;
import it.uniroma3.icr.model.Task;

@Repository
@Transactional(readOnly=false)
public class TaskDaoImpl implements TaskDaoCustom {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager entityManager;
		
	public void updateEndDate(Task task) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();

		java.sql.Timestamp date = new java.sql.Timestamp(now.getTime());
		task.setEndDate(date);

		String update = "update task set end_date = ?1 where id = ?2 and student_id=?3";
		Query query = this.entityManager.createNativeQuery(update).setParameter(1, date)
				.setParameter(2, task.getId()).setParameter(3, task.getStudent().getId());
		if (query.executeUpdate()!=1) {
			LOGGER.info("PROBLEM IN updateEndDate - task " + task.getId() + " student " + task.getStudent().getId());
		}			
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Object> taskTimes() {
		String sql= " select task.job.id,task.batch,to_char(avg(task.endDate - task.startDate), 'HH24:MI:SS:MS') as tempo_medio, to_char(max(task.endDate - task.startDate), 'HH24:MI:SS:MS') "
				+ "as tempo_massimo, to_char(min(task.endDate - task.startDate), 'HH24:MI:SS:MS') as tempo_minimo from Task task where task.endDate IS NOT NULL "
				+ "group by task.job.id,task.batch order by task.job.id,task.batch";
		Query query = this.entityManager.createQuery(sql);
		List<Object> times = query.getResultList();
		System.out.println(times);
		return times;
	}

}