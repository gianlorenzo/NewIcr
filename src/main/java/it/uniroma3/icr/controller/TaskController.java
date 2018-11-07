package it.uniroma3.icr.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Sample;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.model.TaskWrapper;
import it.uniroma3.icr.service.editor.ImageEditor;
import it.uniroma3.icr.service.editor.TaskEditor;
import it.uniroma3.icr.service.impl.ImageFacade;
import it.uniroma3.icr.service.impl.JobFacade;
import it.uniroma3.icr.service.impl.NegativeSampleService;
import it.uniroma3.icr.service.impl.ResultFacade;
import it.uniroma3.icr.service.impl.SampleService;
import it.uniroma3.icr.service.impl.StudentFacade;
import it.uniroma3.icr.service.impl.StudentFacadeSocial;
import it.uniroma3.icr.service.impl.SymbolFacade;
import it.uniroma3.icr.service.impl.TaskFacade;

@Controller
public class TaskController {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private @Autowired ImageEditor imageEditor;
	private @Autowired TaskEditor taskEditor;
	@Autowired
	public SymbolFacade symbolFacade;
	@Autowired
	public SampleService sampleService;
	@Autowired
	public NegativeSampleService negativeSampleService;
	@Autowired
	public JobFacade facadeJob;
	@Autowired
	public ImageFacade imageFacade;
	@Autowired
	public TaskFacade taskFacade;
	@Autowired
	public StudentFacade studentFacade;

	@Autowired
	public StudentFacadeSocial studentFacadesocial;

	@Autowired
	public ResultFacade resultFacade;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Image.class, this.imageEditor);
		binder.registerCustomEditor(Task.class, this.taskEditor);
	}

	public @ModelAttribute("taskResults") TaskWrapper setupWrapper() {
		return new TaskWrapper();
	}

	@RequestMapping(value = "user/newTask", method = RequestMethod.GET)
	public String taskChoose(@ModelAttribute Task task, @ModelAttribute Job job, @ModelAttribute Result result,
			@ModelAttribute("taskResults") TaskWrapper taskResults, Model model, HttpServletRequest request,
			@RequestParam(name = "social", required = false) String social) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //prendo username dello studente
		String s = auth.getName();
		model.addAttribute("social", social);
		Student student;
		if (social == null || social.isEmpty())
			student = studentFacade.findUser(s);
		else
			student = studentFacadesocial.findUser(s);
		model.addAttribute("student", student);  //verifico se lo studente ha effettuato un login con i social o meno
		task = taskFacade.assignTask(student); //assegno task allo studente
		if ((task != null) && (task.getStudent() !=null)) {
			task.setStudent(student);
			LOGGER.debug("1 - assigned Task " + task.getId() + " to student "+ student.getId() + " (" + task.getStudent().getId() +")");
			List<Sample> positiveSamples = sampleService.findAllSamplesBySymbolId(task.getJob().getSymbol().getId());
			List<Sample> negativeSamples = negativeSampleService.findAllNegativeSamplesBySymbolId(task.getJob().getSymbol().getId());
			List<Result> listResults = taskFacade.findTaskResult(task, student);
			taskResults.setResultList(listResults);
			for (Result r : taskResults.getResultList()) {
				LOGGER.debug("2 - retrieved task " + r.getTask().getId() +" student " + r.getTask().getStudent().getId() + " (for " + student.getId() + ")");
				r.getImage().setPath(r.getImage().getPath().replace(File.separatorChar, '/'));
			}
			String hint = taskFacade.findHintByTask(taskResults.getResultList().get(0).getTask());
			LOGGER.debug("3 - hint on task "+ task.getId() +" to student "+ student.getId());
			for (Result r : taskResults.getResultList()) {
				LOGGER.debug("3.1 - hint on task "+ task.getId() + "(" + r.getTask().getId() +") to student " + student.getId() + "(" + r.getTask().getStudent().getId() +")" + " result "+r.getId());
			}
			task.setStudent(student);
			model.addAttribute("student", student);
			model.addAttribute("positiveSamples", positiveSamples);
			model.addAttribute("negativeSamples", negativeSamples);
			model.addAttribute("task", task);
			model.addAttribute("taskResults", taskResults);
			model.addAttribute("hint", hint);
			LOGGER.debug("4 - end taskChoose task " + 
					task.getId() + "(task results " + 
					taskResults.getResultList().get(0).getTask().getId() + 
					" size " + taskResults.getResultList().size() + 
					") by student " + student.getId() + " (" + 
					taskResults.getResultList().get(0).getTask().getStudent().getId() + " - " +task.getStudent().getId() +")");
			return "users/newTaskImage";
		}
		return "users/goodBye";
	}

	@RequestMapping(value = "user/secondConsoleWord", method = RequestMethod.POST)
	public String taskRecapWord(@ModelAttribute("taskResults") TaskWrapper taskResults, Model model,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "social", required = false) String social) throws IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		model.addAttribute("social", social);
		Student student;

		if (social == null || social.isEmpty())
			student = studentFacade.findUser(username);
		else
			student = studentFacadesocial.findUser(username);

		LOGGER.debug("5 - Auth name " + username + ", student: " + student.getId());
		String action = request.getParameter("action");
		String targetUrl = "";

		String conferma1 = "Conferma e vai al prossimo task";
		boolean differentId = false;
		
		if (conferma1.equals(action)) {
			for (Result result : taskResults.getResultList()) {
				LOGGER.debug("5.0 - task: " + result.getTask().getId() + " result " + result.getId() + " student.getId() " + student.getId() +" - result.getTask().getStudent().getId(): " + result.getTask().getStudent().getId());
				if(!student.getId().equals(result.getTask().getStudent().getId())) {
					LOGGER.warn("5.1 - task: " + result.getTask().getId() + " result " + result.getId() + " student.getId() " + student.getId() +" - result.getTask().getStudent().getId(): " + result.getTask().getStudent().getId());
					Long id = taskFacade.findStudentIdOnTask(result.getTask());
					if (!student.getId().equals(id)) { // non e' il mio task
						LOGGER.error("5.1.1 - task: " + result.getTask().getId() + " student.getId() " + student.getId() +" - result.getTask().getStudent().getId(): " + result.getTask().getStudent().getId());
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
					taskFacade.updateEndDate(task);
					if (result.getAnswer() == null)
						result.setAnswer("No");
					tempTime = (int)(task.getEndDate().getTime() - task.getStartDate().getTime())/1000;
					if (tempTime > 300) 
						tempTime = 300;
					tempTask++;
					LOGGER.debug("6 - task " + task.getId() +" accomplished by student " + student.getId() + " - result " + result.getId());
				}
				
				resultFacade.updateListResult(taskResults);
				
				for (Result result : taskResults.getResultList()) {
					LOGGER.debug("7 - AFTER update: task " + result.getTask().getId()+" accomplished by student " + student.getId() + " - result " + result.getId());
				}
				student.setTempoEffettuato(student.getTempoEffettuato() + tempTime);
				student.setTaskEffettuati(student.getTaskEffettuati() + tempTask);
				for (Result result : taskResults.getResultList()) {
					LOGGER.debug("8 - before save: task "+ result.getTask().getId() +" accomplished by student " + student.getId() + " - result " + result.getId());
				}
				taskFacade.updateStudent(student);
				for (Result result : taskResults.getResultList()) {
					LOGGER.info("9 - after save: task "+ result.getTask().getId() +" accomplished by student " + student.getId() + " - result " + result.getId());
				}
			}
			response.sendRedirect("newTask");
			targetUrl = "users/newTaskImage";
		}
		model.addAttribute("student", student);
		return targetUrl;
	}

	@RequestMapping(value = "user/studentTasks")
	public String studentTasks(Model model, @RequestParam(name = "social", required = false) String social) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Student s;
		if (social == null || social.isEmpty()) {
			s = studentFacade.findUser(auth.getName());
		} else {
			s = studentFacadesocial.findUser(auth.getName());
		}
		if (s.getTaskEffettuati() > 0) {
			long secs = this.taskFacade.getWorkTime(s);

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