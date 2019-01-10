package it.uniroma3.icr.insertImageInDb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetImagePath extends GetManuscriptPath {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getPath() {
        String path = this.getServletContext().getInitParameter("pathImage");
        LOGGER.info("image path: " + path);
        return path;
    }


}
