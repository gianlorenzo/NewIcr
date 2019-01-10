package it.uniroma3.icr.insertImageInDb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetNegativeSamplePath extends GetManuscriptPath {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @Override
    public String getPath() {
        String path = this.getServletContext().getInitParameter("pathNegativeSample");
        LOGGER.info("neg path: " + path);

        return path;
    }

}
