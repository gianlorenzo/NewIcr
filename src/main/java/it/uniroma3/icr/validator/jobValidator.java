package it.uniroma3.icr.validator;

import it.uniroma3.icr.model.Job;
import org.springframework.ui.Model;


public class jobValidator {

	public static boolean validate(Job job, Model model) {
		boolean verifica=true;
		if(job.getTitle().equals("")){
			verifica=false;
			model.addAttribute("errTitle", "*Campo obbligatorio");
		}
		 if(job.getNumberOfStudents()==null || job.getStudents()==0   ){
			verifica=false;
			model.addAttribute("errStudenti","*Questo campo non puo' essere nullo o zero");
		}
	 
		 if(job.getTaskSize()==null || job.getTaskSize()==0 ) {
			verifica=false;
			model.addAttribute("errTask","*Questo campo non puo' essere nullo o zero");
		
		}
		return verifica;
		
	}
	

	
}