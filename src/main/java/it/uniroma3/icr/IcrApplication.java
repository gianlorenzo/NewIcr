package it.uniroma3.icr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
//import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class IcrApplication extends SpringBootServletInitializer {

    @Value("${server.context-parameters.pathImage}")
    private String pathImage;

    @Value("${server.context-parameters.pathSample}")
    private String pathSample;

    @Value("${server.context-parameters.pathNegativeSample}")
    private String pathNegativeSample;

    @Value("${server.context-parameters.jsPath}")
    private String jsPath;

    private static String keyStoreName = "keystore.p12";

    public static void main(String[] args) throws Exception {
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            InputStream keyStoreInputStream = classLoader.getResourceAsStream(keyStoreName);
            if (keyStoreInputStream == null) {
                throw new FileNotFoundException("Could not find file named '" + keyStoreName + "' in the CLASSPATH");
            }

            //load the keystore
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(keyStoreInputStream, null);

            //add to known keystore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keystore);

            //default SSL connections are initialized with the keystore above
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustManagers, null);
            SSLContext.setDefault(sc);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }        SpringApplication.run(IcrApplication.class, args);
    }

    @SuppressWarnings("deprecation")
    @Deprecated
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }



    @Bean
    public ServletContextInitializer contextInitializer() {
        return new ServletContextInitializer() {

            @Override
            public void onStartup(ServletContext servletContext)
                    throws ServletException {
                servletContext.setInitParameter("pathImage", pathImage);
                servletContext.setInitParameter("pathSample", pathSample);
                servletContext.setInitParameter("pathNegativeSample", pathNegativeSample);
                servletContext.setInitParameter("jsPath",jsPath);

            }
        };
    }





}
		
	
