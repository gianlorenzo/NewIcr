package it.uniroma3.icr.cloud;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("taskpolicy")
public interface RestClient {

    @RequestMapping("/prova")
    String getProva();
}
