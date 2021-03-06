package eu.revevol.calendar.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.googlecode.objectify.ObjectifyService;
import eu.revevol.calendar.model.*;
import eu.revevol.calendar.security.Require;
import eu.revevol.calendar.security.Token;
import java.util.logging.Logger;
import javax.inject.Named;

/**
 *
 * @author Clement <clement.hannicq@revevol.eu>
 */
@Api(name = "setup", version = "v1")
public class SetupEndpoint {
    
    private static Logger logger = Logger.getLogger(SetupEndpoint.class.getName());
    
    static {
        ObjectifyService.factory().register(Person.class);
        ObjectifyService.factory().register(Token.class);
    }
    
    @ApiMethod(
            name = "admin",
            path = "admin",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public void admin(@Named("token") String token, @Named("origin") String origin) throws OAuthRequestException {        
        Require.appAdmin(token);
        
        Person admin = new Person();
        admin.mail = origin;
        admin.globalAdmin = true;
        admin.name = origin;
        
        ObjectifyService.ofy().save().entity(admin);
    }
}
