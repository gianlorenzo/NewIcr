package it.uniroma3.icr.insertImageInDb.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;

import it.uniroma3.icr.model.Manuscript;

public abstract class GetManuscriptPath implements ServletContextAware{

    private ServletContext servletContext;

	public GetManuscriptPath(){}
	
    public abstract String getPath();

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


	public List<Manuscript> getManuscript() throws FileNotFoundException, IOException {
		List<Manuscript> manuscripts = new ArrayList<>();
		
		String path = this.getPath();
		LOGGER.info("manuscript path: "+path);

		File[] files = new File(path).listFiles();
		for(int i=0;i<files.length;i++) {
			if(files[i].getName().equals(".DS_Store"))
				files[i].delete();
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
 