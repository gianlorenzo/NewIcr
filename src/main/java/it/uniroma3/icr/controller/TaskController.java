package it.uniroma3.icr.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.uniroma3.icr.supportControllerMethod.TaskControllerSupport;
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

	private TaskControllerSupport taskControllerSupport = new TaskControllerSupport();


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
			@ModelAttribute("taskResults") TaskWrapper taskResults, Model model,
			@RequestParam(name = "social", required = false) String social) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String s = auth.getName();
		model.addAttribute("social", social);
		Student student;
		if (social == null || social.isEmpty())
			student = studentFacade.findUser(s);
		else
			student = studentFacadesocial.findUser(s);
		model.addAttribute("student", student);
		task = taskFacade.assignTask(student);
		return taskControllerSupport.assingStudentTask(task,student,model,taskResults,taskFacade,sampleService,negativeSampleService);

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
		LOGGER.info("5 - Auth name " + username + ", student: " + student.getId());
		String action = request.getParameter("action");
		String targetUrl = "";
		taskControllerSupport.setResult(model,action,taskResults,student,taskFacade,resultFacade);
		response.sendRedirect("newTask");
		targetUrl = "users/newTaskImage";
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
		taskControllerSupport.viewStudentTasks(s,model,taskFacade,social);
		return "users/noStudentTasks";
	}

}