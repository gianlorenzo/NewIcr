package it.uniroma3.icr.supportControllerMethod;

import it.uniroma3.icr.model.*;
import it.uniroma3.icr.service.impl.NegativeSampleService;
import it.uniroma3.icr.service.impl.ResultService;
import it.uniroma3.icr.service.impl.SampleService;
import it.uniroma3.icr.service.impl.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import java.io.File;
import java.util.List;

public class TaskControllerSupport {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    public void setResult(Model model, String action, TaskWrapper taskResults,
                          Student student, TaskService taskService, ResultService resultService) {
        String conferma1 = "Conferma e vai al prossimo task";
        boolean differentId = false;

        if (conferma1.equals(action)) {
            for (Result result : taskResults.getResultList()) {
                LOGGER.debug("5.0 - task: " + result.getTask().getId() + " result " + result.getId() + " student.getId() " + student.getId() + " - result.getTask().getStudent().getId(): " + result.getTask().getStudent().getId());
                if (!student.getId().equals(result.getTask().getStudent().getId())) {
                    LOGGER.warn("5.1 - task: " + result.getTask().getId() + " result " + result.getId() + " student.getId() " + student.getId() + " - result.getTask().getStudent().getId(): " + result.getTask().getStudent().getId());
                    Long id = taskService.findStudentIdOnTask(result.getTask());
                    if (!student.getId().equals(id)) { // non e' il mio task
                        LOGGER.error("5.1.1 - task: " + result.getTask().getId() + " student.getId() " + student.getId() + " - result.getTask().getStudent().getId(): " + result.getTask().getStudent().getId());
                        differentId = true;
                    }
                }
            }
            if (!differentId) {
                model.addAttribute("student", student);
                int tempTime = 0;
                int tempTask = 0;
                for (Result result : taskResults.getResultList()) {
                    Task task = result.getTask();
                    taskService.updateEndDate(task);
                    if (result.getAnswer() == null)
                        result.setAnswer("No");
                    tempTime = (int) (task.getEndDate().getTime() - task.getStartDate().getTime()) / 1000;
                    if (tempTime > 300)
                        tempTime = 300;
                    tempTask++;
                    LOGGER.debug("6 - task " + task.getId() + " accomplished by student " + student.getId() + " - result " + result.getId());
                }
                resultService.updateListResult(taskResults);
                for (Result result : taskResults.getResultList()) {
                    LOGGER.debug("7 - AFTER update: task " + result.getTask().getId() + " accomplished by student " + student.getId() + " - result " + result.getId());
                }
                student.setTempoEffettuato(student.getTempoEffettuato() + tempTime);
                student.setTaskEffettuati(student.getTaskEffettuati() + tempTask);
                for (Result result : taskResults.getResultList()) {
                    LOGGER.debug("8 - before save: task " + result.getTask().getId() + " accomplished by student " + student.getId() + " - result " + result.getId());
                }
                taskService.updateStudent(student);
                for (Result result : taskResults.getResultList()) {
                    LOGGER.info("9 - after save: task " + result.getTask().getId() + " accomplished by student " + student.getId() + " - result " + result.getId());
                }
            }
        }
    }

    public String assingStudentTask(Task task, Student student, Model model,
                                    TaskWrapper taskResults, TaskService taskService,
                                    SampleService sampleService, NegativeSampleService negativeSampleService) {
        if ((task != null) && (task.getStudent() != null)) {
            task.setStudent(student);
            LOGGER.info("1 - assigned Task " + task.getId() + " to student " + student.getId() + " (" + task.getStudent().getId() + ")");
            List<Sample> positiveSamples = sampleService.findAllSamplesBySymbolId(task.getJob().getSymbol().getId());
            List<Sample> negativeSamples = negativeSampleService.findAllNegativeSamplesBySymbolId(task.getJob().getSymbol().getId());
            List<Result> listResults = taskService.findTaskResult(task, student);
            taskResults.setResultList(listResults);
            for (Result r : taskResults.getResultList()) {
                LOGGER.info("2 - retrieved task " + r.getTask().getId() + " student " + r.getTask().getStudent().getId() + " (for " + student.getId() + ")");
                r.getImage().setPath(r.getImage().getPath().replace(File.separatorChar, '/'));
            }
            String hint = taskService.findHintByTask(taskResults.getResultList().get(0).getTask());
            LOGGER.info("3 - hint on task " + task.getId() + " to student " + student.getId());
            for (Result r : taskResults.getResultList()) {
                LOGGER.info("3.1 - hint on task " + task.getId() + "(" + r.getTask().getId() + ") to student " + student.getId() + "(" + r.getTask().getStudent().getId() + ")" + " result " + r.getId());
            }
            task.setStudent(student);
            model.addAttribute("student", student);
            model.addAttribute("positiveSamples", positiveSamples);
            model.addAttribute("negativeSamples", negativeSamples);
            model.addAttribute("task", task);
            model.addAttribute("taskResults", taskResults);
            model.addAttribute("hint", hint);
            LOGGER.info("4 - end taskChoose task " +
                    task.getId() + "(task results " +
                    taskResults.getResultList().get(0).getTask().getId() +
                    " size " + taskResults.getResultList().size() +
                    ") by student " + student.getId() + " (" +
                    taskResults.getResultList().get(0).getTask().getStudent().getId() + " - " + task.getStudent().getId() + ")");
            return "users/newTaskImage";
        }
        return "users/goodBye";
    }

    public String viewStudentTasks(Student s, Model model, TaskService taskService,
                                   String social) {
        if (s.getTaskEffettuati() > 0) {
            long secs = taskService.getWorkTime(s);
            long hours = secs / 3600;
            long minutes = (secs % 3600) / 60;
            long seconds = secs % 60;
            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            model.addAttribute("time", timeString);
            model.addAttribute("s", s);
            model.addAttribute("social", social);
            return "users/studentTasks";
        }
        return "users/noStudentTasks";
    }

}