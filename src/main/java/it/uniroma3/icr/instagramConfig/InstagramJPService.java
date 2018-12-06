package it.uniroma3.icr.instagramConfig;

import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstagramJPService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    String clientID = "e21f60a83d1e42bf9323f5033a160971";
    String clientSecret = "463179b3935f47eaa928bb49e9fc680a";
    String callback = "https://localhost:8443/instagram";
    InstagramService service;

    public InstagramService build() {
        LOGGER.info("clientid:" + clientID);
        LOGGER.info("clientSecret:" + clientSecret);
        service = new InstagramAuthService().apiKey(clientID).apiSecret(clientSecret).callback(callback).build();
        return service;
    }
    public Instagram getInstagram(String code) {

        Verifier verifier = new Verifier(code);
        Token accessToken = service.getAccessToken(verifier);
        Instagram instagram = new Instagram(accessToken);
        return instagram;
    }
}
