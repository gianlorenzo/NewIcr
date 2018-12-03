package it.uniroma3.icr.supportControllerMethod;

import java.util.HashMap;
import java.util.Map;

public class SetDescriptions {

    public Map<String,String> setDescriptions() {
        Map<String, String> descriptions = new HashMap<>();

        descriptions.put("trovaPartiColorate","trovaPartiColorate");
        descriptions.put("dividiRiga","dividiRiga");
        descriptions.put("completaParola","completaParola");
        descriptions.put("trovaInteroSimbolo","trovaInteroSimbolo");

        return descriptions;
    }
}
