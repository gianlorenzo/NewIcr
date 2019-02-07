package it.uniroma3.icr.cloud.restInterface;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("taskpolicy")
public interface RestClient {

    @RequestMapping(value = "/prova", consumes = "application/json")
    Prova getProva();

    @RequestMapping(value="/assignTaskPolicy")
    String assignTaskPolicy();



    public class Prova {

        String stringaProva;


        public String getStringaProva() {
            return stringaProva;
        }

        public void setStringaProva(String stringaProva) {
            this.stringaProva = stringaProva;
        }
    }


}
