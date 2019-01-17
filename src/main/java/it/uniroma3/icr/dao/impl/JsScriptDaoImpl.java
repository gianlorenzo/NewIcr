package it.uniroma3.icr.dao.impl;

import it.uniroma3.icr.javascriptTools.GetScriptPath;
import org.springframework.stereotype.Repository;

import java.io.File;

@Repository
public class JsScriptDaoImpl {


    public String getJsFile(String path,String typologyDirectory) {
        String jsPath = path.concat(typologyDirectory);
        File[] file = new File(jsPath).listFiles();

        return file[0].getName();

    }


}
