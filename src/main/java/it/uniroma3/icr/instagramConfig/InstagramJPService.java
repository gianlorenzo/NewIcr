package it.uniroma3.icr.instagramConfig;

import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;

public class InstagramJPService {

    String clientID = InstagramUtils.getConfigProperties().getProperty(Constants.CLIENT_ID);
    String clientSecret = InstagramUtils.getConfigProperties().getProperty(Constants.CLIENT_SECRET);
    String callback = "http://localhost:8080/connect/instagramConnected";
    InstagramService service;

    public InstagramService build() {
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
