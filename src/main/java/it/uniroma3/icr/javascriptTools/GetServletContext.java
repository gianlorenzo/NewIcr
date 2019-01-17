package it.uniroma3.icr.javascriptTools;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

public abstract class GetServletContext implements ServletContextAware {

    public abstract String getPath();


    private ServletContext servletContext;

    public GetServletContext() {

    }

    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;

    }
}
