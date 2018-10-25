package it.uniroma3.icr.dao.impl;


import it.uniroma3.icr.dao.TaskDaoCustom;
import it.uniroma3.icr.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;

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
}