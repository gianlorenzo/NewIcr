package it.uniroma3.icr.service.impl;


import it.uniroma3.icr.dao.impl.JsScriptDaoImpl;
import it.uniroma3.icr.javascriptTools.GetScriptPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsScriptService {

    @Autowired
    private GetScriptPath getScriptPath;

    @Autowired
    private JsScriptDaoImpl jsScriptDaoImpl;

    public String getJsFile(String path, String typologyDirectory) {
        return this.jsScriptDaoImpl.getJsFile(path, typologyDirectory);
    }

    public String getScriptPath() {
        return this.getScriptPath.getPath();
    }
}
