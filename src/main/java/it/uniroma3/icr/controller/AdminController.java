package it.uniroma3.icr.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.icr.model.Administrator;
import it.uniroma3.icr.model.ComparatoreSimboloPerNome;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.NegativeSample;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Sample;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.Symbol;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.service.editor.SymbolEditor;
import it.uniroma3.icr.service.impl.AdminFacade;
import it.uniroma3.icr.service.impl.ImageFacade;
import it.uniroma3.icr.service.impl.JobFacade;
import it.uniroma3.icr.service.impl.ManuscriptService;
import it.uniroma3.icr.service.impl.NegativeSampleService;
import it.uniroma3.icr.service.impl.SampleService;
import it.uniroma3.icr.service.impl.StudentFacade;
import it.uniroma3.icr.service.impl.SymbolFacade;
import it.uniroma3.icr.service.impl.TaskFacade;
import it.uniroma3.icr.validator.AdminValidator;
import it.uniroma3.icr.validator.jobValidator;

@Controller
public class AdminController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SymbolEditor symbolEditor;
	@Autowired
	private StudentFacade studentFacade;
	@Autowired
	private AdminFacade adminFacade;
	@Autowired
	private JobFacade facadeJob;
	@Autowired
	private SampleService sampleService;
	@Autowired
	private NegativeSampleService negativeSampleService;
	@Autowired
	private TaskFacade facadeTask;
	@Autowired
	private SymbolFacade symbolFacade;;
	@Autowired
	private ImageFacade imageFacade;
	@Autowired
	private ManuscriptService manuscriptService;

	@Qualifier("adminValidator")
	private Validator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Symbol.class, this.symbolEditor);
		binder.setValidator(validator);
	}

	@RequestMapping(value = "admin/homeAdmin")
	public String toHomeAdmin() {
		return "administration/homeAdmin";
	}

	/*--------------------------------------------REGISTRA ADMIN------------------------------------------------------------------------*/

	@RequestMapping(value = "admin/registrationAdmin", method = RequestMethod.GET)
	public String registration(@ModelAttribute Administrator administrator, Model model) {
		return "administration/registrationAdmin";
	}

	@RequestMapping(value = "admin/addAdmin", method = RequestMethod.POST)
	public String confirmAdmin(@ModelAttribute Administrator administrator, @Validated Administrator a,
			BindingResult bindingResult, Model model) {

		Administrator a1 = adminFacade.findAdmin(administrator.getUsername());
		Student s1 = studentFacade.findUser(administrator.getUsername());

		if (AdminValidator.validate(administrator, model, a1, s1)) {
			administrator.setPassword(administrator.getPassword());
			model.addAttribute("administrator", administrator);
			adminFacade.addAdmin(administrator);
			return "administration/adminRecap";
		}

		else {
			return "administration/registrationAdmin";
		}

	}
	/*--------------------------------------------INSERISCI JOB------------------------------------------------------------------------*/

	@RequestMapping(value = "admin/toSelectManuscript")
	private String toSelectManuscript(@ModelAttribute Job job, @ModelAttribute Manuscript manuscript,
			@ModelAttribute Task task, Model model) {

		List<Manuscript> listManuscripts = this.manuscriptService.findAllManuscript();
		if (listManuscripts.size() == 0)
			return "administration/noManuscriptForJob";

		List<String> listManuscriptsName = new ArrayList<>();
		for (Manuscript m : listManuscripts) {
			listManuscriptsName.add(m.getName());
		}

		model.addAttribute("manuscripts", listManuscriptsName);
		model.addAttribute("job", job);
		model.addAttribute("task", task);
		return "administration/selectImageByManuscript";
	}

	@RequestMapping(value = "admin/selectImageByManuscript")
	private String newJobByManuscript(HttpSession session, @ModelAttribute("manuscript") Manuscript manuscript,
			@ModelAttribute Job job, @ModelAttribute Task task, Model model) {
		String manuscriptName = manuscript.getName();
		List<Symbol> symbols = symbolFacade.findSymbolByManuscriptName(manuscriptName);
		Collections.sort(symbols, new ComparatoreSimboloPerNome());
		job.setManuscript(manuscript);
		session.setAttribute("manuscript", manuscript);
		model.addAttribute("symbols", symbols);
		model.addAttribute("job", job);
		return "administration/insertJobByManuscript";

	}

	@RequestMapping(value = "admin/addJobByManuscript")
	public String confirmJobByManuscript(HttpSession session, HttpServletRequest request,
			@Valid @ModelAttribute Job job, BindingResult bindingResult, @ModelAttribute Task task, 
			@ModelAttribute Image image, @ModelAttribute Result result, Model model) {
		Manuscript manuscript = manuscriptService
				.findManuscriptByName(((Manuscript) session.getAttribute("manuscript")).getName());
		model.addAttribute("job", job);
		model.addAttribute("task", task);
		model.addAttribute("manuscript", manuscript);
		Boolean bool = false;
		List<Image> imagesTask = null;
		imagesTask = this.imageFacade.getImagesFromManuscriptName(manuscript.getId());
		bool = true;

		if (jobValidator.validate(job, model)) {
			this.facadeJob.createJob(job, manuscript, imagesTask, bool, task);
			return "administration/jobRecap";
		} else {
			String manuscriptName = manuscript.getName();
			List<Symbol> symbols = symbolFacade.findSymbolByManuscriptName(manuscriptName);
			Collections.sort(symbols, new ComparatoreSimboloPerNome());
			job.setManuscript(manuscript);
			session.setAttribute("manuscript", manuscript);
			model.addAttribute("symbols", symbols);

			return "administration/insertJobByManuscript";
		}
	}

	/*--------------------------------------------INSERISCI MANOSCRITTO DB--------------------------------------------------------------------------------*/
	@RequestMapping(value = "admin/selectManuscript")
	public String selectManuscript(Model model, @ModelAttribute Manuscript manuscript)
			throws FileNotFoundException, IOException {
		List<Manuscript> listManuscripts = this.imageFacade.getManuscript();

		List<String> listManuscriptsName = new ArrayList<>();
		for (Manuscript m : listManuscripts) {
			if (manuscriptService.findManuscriptByName(m.getName()) == null)
				listManuscriptsName.add(m.getName());
		}
		if (listManuscriptsName.isEmpty()) {
			return "administration/noInsertManuscript";
		} else {
			model.addAttribute("manuscripts", listManuscriptsName);
			return "administration/insertManuscript";
		}
	}

	@RequestMapping(value = "admin/insertManuscript")
	public String insertManuscript(@ModelAttribute("manuscript") Manuscript manuscript, Model model,
			HttpServletRequest request, @ModelAttribute Symbol symbol, @ModelAttribute Image image,
			@ModelAttribute Sample sample, @ModelAttribute NegativeSample negativeSample)
			throws FileNotFoundException, IOException {

		String manuscriptName = manuscript.getName();
		this.manuscriptService.saveManuscript(manuscript);
		Manuscript m = this.manuscriptService.findManuscriptByName(manuscriptName);
		String path = symbolFacade.getPath();
		path = path.concat(manuscriptName).concat("/");
		symbolFacade.insertSymbolInDb(path, m);
		path = sampleService.getPath();
		path = path.concat(manuscriptName).concat("/");
		sampleService.getSampleImage(path, m);
		path = negativeSampleService.getNegativePath();
		path = path.concat(manuscriptName).concat("/");
		negativeSampleService.getNegativeSampleImage(path, m); //
		path = imageFacade.getPath();
		path = path.concat(manuscriptName).concat("/");
		imageFacade.updateAllImages(path, m);
		this.manuscriptService.saveManuscript(manuscript);
		return "administration/insertRecap";
	}

	@RequestMapping(value = "admin/toChangeAdminPassword")
	public String toChangeAdminPassword(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Administrator a = this.adminFacade.findAdmin(username);
		a.setPassword("");
		model.addAttribute("administrator", a);
		return "administration/changeAdminPassword";
	}

	@RequestMapping(value = "admin/changeAdminPassword", method = RequestMethod.POST)
	public String changeAdminPassword(@ModelAttribute Administrator administrator, Model model) {
		if (administrator.getPassword().equals("") || administrator.getPassword() == null) {
			model.addAttribute("errPassword", "*Campo Obbligatorio");
			model.addAttribute("administrator", administrator);
			return "administration/changeAdminPassword";
		}
		administrator.setPassword(administrator.getPassword());
		adminFacade.addAdmin(administrator);
		return "administration/homeAdmin";
	}
}