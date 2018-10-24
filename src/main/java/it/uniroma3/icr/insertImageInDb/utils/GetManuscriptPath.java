package it.uniroma3.icr.insertImageInDb.utils;

import it.uniroma3.icr.model.Manuscript;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class GetManuscriptPath implements ServletContextAware{

    private ServletContext servletContext;

	public GetManuscriptPath(){}
	
    public abstract String getPath();
    
	
	public List<Manuscript> getManuscript() throws FileNotFoundException, IOException {
		List<Manuscript> manuscripts = new ArrayList<>();
		
		String path = this.getPath();
		
		File[] files = new File(path).listFiles();
		for(int i=0;i<files.length;i++) {
			Manuscript manuscript = new Manuscript(files[i].getName());
			manuscripts.add(manuscript);
		}
		return manuscripts;
		
	}
	public ServletContext getServletContext(){
		return this.servletContext;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
		
	}
}
 