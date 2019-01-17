package it.uniroma3.icr.javascriptTools;

import it.uniroma3.icr.insertImageInDb.GetManuscriptPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetScriptPath extends GetManuscriptPath {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getPath() {
//        String path = this.getServletContext().getInitParameter("pathJs");
       // LOGGER.info("js path: " + path);
        String path = this.getServletContext().getInitParameter("jsPath");
        return path;
    }

}
