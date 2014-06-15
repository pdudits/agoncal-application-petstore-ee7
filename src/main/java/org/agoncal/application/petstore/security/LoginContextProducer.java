package org.agoncal.application.petstore.security;

import org.agoncal.application.petstore.util.ConfigProperty;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.ServletContext;

/**
 * @author blep
 *         Date: 16/02/12
 *         Time: 07:28
 */

public class LoginContextProducer {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private SimpleCallbackHandler callbackHandler;
    
    @Inject
    ServletContext ctx;

    // ======================================
    // =          Business methods          =
    // ======================================

    @Produces
    public LoginContext produceLoginContext(@ConfigProperty("loginConfigFile") String loginConfigFileName,
                                            @ConfigProperty("loginModuleName") String loginModuleName) throws LoginException, URISyntaxException, MalformedURLException {

        final URI loginConfig = ctx.getResource("/WEB-INF/classes/"+loginConfigFileName).toURI();//LoginContextProducer.class.getResource(loginConfigFileName).toURI();
        System.setProperty("java.security.auth.login.config", new File(loginConfig).getPath());

        try {
            return new LoginContext(loginModuleName, callbackHandler);
        } catch (Exception e) {
            System.out.println("ouch!!!");
            return null;
        }
    }

}
