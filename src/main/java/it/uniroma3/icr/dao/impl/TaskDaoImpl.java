package it.uniroma3.icr.dao.impl;


import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.icr.dao.TaskDaoCustom;
import it.uniroma3.icr.model.Task;

@Repository
@Transactional(readOnly = false)
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
        if (query.executeUpdate() != 1) {
            LOGGER.info("PROBLEM IN updateEndDate - task " + task.getId() + " student " + task.getStudent().getId());
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Object> taskTimes() {
        String sql = " select task.job.id,task.batch,to_char(avg(task.endDate - task.startDate), 'HH24:MI:SS:MS') as tempo_medio, to_char(max(task.endDate - task.startDate), 'HH24:MI:SS:MS') "
                + "as tempo_massimo, to_char(min(task.endDate - task.startDate), 'HH24:MI:SS:MS') as tempo_minimo from Task task where task.endDate IS NOT NULL "
                + "group by task.job.id,task.batch order by task.job.id,task.batch";
        Query query = this.entityManager.createQuery(sql);
        List<Object> times = query.getResultList();
        System.out.println(times);
        return times;
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

        if (taskList.size() != 0) {
            task = taskList.get(0);
            task.setStudent(student);
            LOGGER.info("0 - Resumed task " + task.getId() + " for student " + student.getId());
            return task;
        } else {
            select = "SELECT t FROM Task t "
                    + "WHERE (t.batch, t.job.id) not in ( "                               // task già fatti dallo studente
                    + " SELECT distinct t2.batch, t2.job.id "
                    + " FROM Task t2 "
                    + " WHERE t2.student.id= ?1 and t2.endDate IS NOT NULL) "
                    + "AND (t.student.id IS NULL)"; // task non assegnati

            query1 = this.entityManager.createQuery(select).setMaxResults(53).setParameter(1, student.getId());
            taskList = query1.getResultList(); // trova il task da eseguire

            if (taskList.size() != 0) {  // ci sono ancora task
                int position = (int) (student.getId() % taskList.size());
                LOGGER.info("0 - New task " + taskList.get(position).getId() + " to student " + student.getId());

                Calendar calendar = Calendar.getInstance();
                java.util.Date now = calendar.getTime();
                java.sql.Timestamp date = new java.sql.Timestamp(now.getTime());

                LOGGER.debug("SQL UPDATE update task set start_date = " + date + " student_id = " + student.getId() + " where id = " + taskList.get(position));

                String update = "update task set start_date = ?1, student_id = ?2 where id = ?3 and student_id is null";
                query1 = this.entityManager.createNativeQuery(update).setParameter(1, date).setParameter(2, student.getId()).setParameter(3, taskList.get(position).getId());
                int numRow = query1.executeUpdate();
                if (numRow == 1) {
                    task = taskList.get(position);
                    task.setStudent(student);
                    task.setStartDate(date);
                    student.addTask(task);
                } else {
                    LOGGER.info("0.1 - race on task " + taskList.get(position) + ". Try another task for student " + student.getId());
                    task = assignTask(student);
                }
            } else { // i task sono finiti
                return null;
            }
        }
        return task;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public Task assignTaskId(Student student, Image image) {
        System.out.println("Sono in assignTaskId: id immagine" +  image.getId());
        Task task;
        List<Object> taskList;
        String select;
        select = "SELECT t.id,t.batch,t.end_date,t.start_date,t.job_id,t.student_id FROM result r JOIN task t ON r.task_id = t.id WHERE (t.student_id= ?1 AND r.image_id =?2  AND t.end_Date IS NULL AND t.start_Date IS NOT NULL)";
        Query query1 = this.entityManager.createNativeQuery(select,Task.class).setParameter(1, student.getId())
               .setParameter(2,image.getId());


        taskList = query1.getResultList();

        if (taskList.size() != 0) {
            System.out.println("Sono in task non ancora finito: lunghezza tasks" + taskList.size());
            task = (Task)taskList.get(0);
            System.out.println("Sono in task non ancora finito: id task" + task.getId());
            task.setStudent(student);
            System.out.println("assegnato task non ancora finito");
            LOGGER.info("0 - Resumed task " + task.getId() + " for student " + student.getId());
            return task;
        } else {
            select = "SELECT t.id,t.batch,t.end_date,t.start_date,t.job_id,t.student_id FROM Task t join result r on r.task_id = t.id "
                    + "WHERE r.image_id=?2 and (t.batch, t.job_id) not in ( " // task già fatti dallo studente
                    + " SELECT distinct t2.batch, t2.job_id "
                    + " FROM task t2 "
                    + " WHERE t2.student_id= ?1 and t2.end_Date IS NOT NULL) "
                    + "AND (t.student_id IS NULL)"; // task non assegnati

            query1 = this.entityManager.createNativeQuery(select,Task.class).setMaxResults(53).setParameter(1, student.getId()).
                    setParameter(2,image.getId());
            taskList = query1.getResultList(); // trova il task da eseguire

            if (taskList.size() != 0) {  // ci sono ancora task
                System.out.println("Sono in task non ancora assegnato: lunghezza tasks" + taskList.size());
                int position = (int) (student.getId() % taskList.size());

                Calendar calendar = Calendar.getInstance();
                java.util.Date now = calendar.getTime();
                java.sql.Timestamp date = new java.sql.Timestamp(now.getTime());

                LOGGER.debug("SQL UPDATE update task set start_date = " + date + " student_id = " + student.getId() + " where id = " + taskList.get(position));

                String update = "update task set start_date = ?1, student_id = ?2 where id = ?3 and student_id is null";
                task = (Task)taskList.get(position);
                System.out.println("Sono in task non ancora assegnato: id task" + task.getId());
                System.out.println(task.getId());
                query1 = this.entityManager.createNativeQuery(update).setParameter(1, date).setParameter(2, student.getId()).setParameter(3, task.getId());
                int numRow = query1.executeUpdate();
                if (numRow == 1) {
                    task = (Task)taskList.get(position);
                    task.setStudent(student);
                    System.out.println("assegnato task non ancora finito");
                    task.setStartDate(date);
                    student.addTask(task);
                } else {
                    LOGGER.info("0.1 - race on task " + taskList.get(position) + ". Try another task for student " + student.getId());
                    task = assignTask(student);
                    System.out.println("assegnato task non ancora finito");
                }
            } else { // i task sono finiti
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


    @Transactional
    public void updateStudent(Student student) {
        String update = "update student set task_effettuati = ?1, tempo_effettuato = ?2 where id = ?3";
        Query query1 = this.entityManager.createNativeQuery(update).setParameter(1, student.getTaskEffettuati()).setParameter(2, student.getTempoEffettuato()).setParameter(3, student.getId());
        if (query1.executeUpdate() != 1)
            LOGGER.debug("PROBLEM IN SQL UPDATE: update student set task_effettuati " + student.getTaskEffettuati() + ", tempo_effettuato = " + student.getTempoEffettuato() + " where id = " + student.getId());
    }

    public List<Result> findTaskResult(Task task, Student student) {
        LOGGER.debug("SQL SELECT r RESULT FROM RESULT WHERE TASK_id = " + task.getId());
        if (task.getStudent().getId() != student.getId())
            LOGGER.debug("PROBLEM TASK " + task.getId() + " STDUDENT PROBLEM " + task.getStudent().getId() + " " + student.getId());

        String select = "select r from Result r where r.task.id = ?1";
        Query query1 = this.entityManager.createQuery(select).setParameter(1, task.getId());
        @SuppressWarnings("unchecked")
        List<Result> resultList = query1.getResultList(); // trova i result del task
        return resultList;
    }

    public Result findTaskOneResult(Task task, Student student) {
        LOGGER.debug("SQL SELECT r RESULT FROM RESULT WHERE TASK_id = " + task.getId());
        if (task.getStudent().getId() != student.getId())
            LOGGER.debug("PROBLEM TASK " + task.getId() + " STDUDENT PROBLEM " + task.getStudent().getId() + " " + student.getId());

        String select = "select r from Result r where r.task.id = ?1";
        Query query1 = this.entityManager.createQuery(select).setParameter(1, task.getId());
        @SuppressWarnings("unchecked")
        Result result = (Result) query1.getSingleResult();
        return result;
    }

    public Long findStudentIdOnTask(Task task) {
        LOGGER.debug("SQL SELECT t TASK FROM TASK WHERE id = " + task.getId());

        String select = "select t.student.id from Task t where t.id = ?1";
        Query query1 = this.entityManager.createQuery(select).setParameter(1, task.getId());

        Long id = (Long) query1.getSingleResult(); // trova i result del task
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