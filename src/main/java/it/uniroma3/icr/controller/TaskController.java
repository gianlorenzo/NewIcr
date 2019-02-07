package it.uniroma3.icr.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.uniroma3.icr.cloud.controller.CloudController;
import it.uniroma3.icr.service.impl.*;
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

@Controller
public class TaskController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private @Autowired
    ImageEditor imageEditor;
    private @Autowired
    TaskEditor taskEditor;
    @Autowired
    public SymbolService symbolService;
    @Autowired
    public SampleService sampleService;
    @Autowired
    public NegativeSampleService negativeSampleService;
    @Autowired
    public JobService facadeJob;
    @Autowired
    public ImageService imageService;
    @Autowired
    public TaskService taskService;
    @Autowired
    public StudentService studentService;

    @Autowired
    public StudentServiceSocial studentFacadesocial;

    @Autowired
    public ResultService resultService;

    @Autowired
    public JsScriptService jsScriptService;

    @Autowired
    private CloudController cloudController;

    private TaskControllerSupport taskControllerSupport = new TaskControllerSupport();

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Image.class, this.imageEditor);
        binder.registerCustomEditor(Task.class, this.taskEditor);
    }

    public @ModelAttribute("taskResults")
    TaskWrapper setupWrapper() {
        return new TaskWrapper();
    }

    @RequestMapping(value = "user/newTask", method = RequestMethod.GET)
    public String taskChoose(@ModelAttribute Task task, @ModelAttribute Job job, @ModelAttribute Result result,
                             @ModelAttribute("taskResults") TaskWrapper taskResults, Model model,
                             @RequestParam(name = "social", required = false) String social, HttpSession session) {
        Image image = this.imageService.retrieveImage(Long.valueOf(this.cloudController.getTaskPolicy()));
        System.out.println("id immagine:" + image.getId());
        /*String id = this.cloudController.getTaskPolicy();
        System.out.println(id);
        System.out.println(Long.valueOf(id));
        task = this.taskService.retrieveTask(Long.valueOf(id));*/
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String s = auth.getName();
        model.addAttribute("social", social);
        Student student;
        if (social == null || social.isEmpty())
            student = studentService.findUser(s);
        else
            student = studentFacadesocial.findUser(s);
        model.addAttribute("student", student);
        model.addAttribute("taskResults",taskResults);
        task = taskService.assignTaskId(student,image);
        return taskControllerSupport.assingStudentTask(task, student, model, taskResults, taskService, sampleService, negativeSampleService, jsScriptService,session);

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
            student = studentService.findUser(username);
        else
            student = studentFacadesocial.findUser(username);
        LOGGER.info("5 - Auth name " + username + ", student: " + student.getId());
        String action = request.getParameter("action");
        String targetUrl = "";
        model.addAttribute("taskResults",taskResults);
        taskControllerSupport.setResult(model, action, taskResults, student, taskService, resultService);
        response.sendRedirect("newTask");
        targetUrl = "users/newTask";
        model.addAttribute("student", student);
        return targetUrl;
    }

    @RequestMapping(value = "user/studentTasks")
    public String studentTasks(Model model, @RequestParam(name = "social", required = false) String social, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student s;
        if (social == null || social.isEmpty()) {
            s = studentService.findUser(auth.getName());
            session.setAttribute("s",s);
            model.addAttribute("s",s);
        } else {
            s = studentFacadesocial.findUser(auth.getName());
            model.addAttribute("s",s);
            session.setAttribute("s",s);
        }
        return taskControllerSupport.viewStudentTasks(s, model, taskService, social,session);
    }

}