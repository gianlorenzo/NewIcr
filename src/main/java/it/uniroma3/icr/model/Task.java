package it.uniroma3.icr.model;

import java.sql.*;
import java.util.List;

import javax.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int batch;

    @Column
    private java.sql.Timestamp startDate;

    @Column
    private java.sql.Timestamp endDate;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Job job;

    @OneToOne(mappedBy = "task")
    private Result result;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Task(Long id, int batch, Timestamp startDate, Timestamp endDate, Student student, Job job,
                Result result) {
        this.id = id;
        this.batch = batch;
        this.startDate = startDate;
        this.endDate = endDate;
        this.student = student;
        this.job = job;
        this.result = result;
    }

    public java.sql.Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(java.sql.Timestamp startDate) {
        this.startDate = startDate;
    }

    public java.sql.Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(java.sql.Timestamp endDate) {
        this.endDate = endDate;
    }

    public Task() {

    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "Task [id=" + id;
    }

    public boolean equals(Object object) {
        Task task = (Task) object;
        return this.id.equals(task.getId());
    }

    public int hash() {
        return this.id.hashCode();
    }


}