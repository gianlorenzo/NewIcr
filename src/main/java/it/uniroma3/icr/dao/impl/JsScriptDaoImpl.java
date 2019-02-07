package it.uniroma3.icr.dao.impl;

import it.uniroma3.icr.javascriptTools.GetScriptPath;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JsScriptDaoImpl {


    public String getJsFile(String path,String typologyDirectory) {
        String jsPath = path.concat(typologyDirectory);
        System.out.print(jsPath);
        System.out.println("len"+new File(jsPath).listFiles().length);
        File[] file = new File(jsPath).listFiles();

        if(file.length>1) {
            for (int i = 0; i < file.length; i++) {
                System.out.println(file[1].getName());
                if (!file[i].getName().equals(typologyDirectory.concat(".js")))
                    file[i].delete();
            }
        }
        return typologyDirectory.concat(".js");

    }

    public List<String> getAllJsDirectory(String path) {
        File[] directory = new File(path).listFiles();
        List<String> directoryNames = new ArrayList<>();
        for (File f : directory) {
            directoryNames.add(f.getName());
        }
        return directoryNames;
    }


}
