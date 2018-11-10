package it.uniroma3.icr.insertImageInDb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetSamplePath extends GetManuscriptPath{

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


	@Override
	public String getPath() {
		String path = this.getServletContext().getInitParameter("pathSample");
		LOGGER.info("samp path"+path);
		return path;
	}
    

	


}
