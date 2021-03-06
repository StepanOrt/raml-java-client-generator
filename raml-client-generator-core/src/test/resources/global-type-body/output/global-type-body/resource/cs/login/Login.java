
package global-type-body.resource.cs.login;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import global-type-body.exceptions.FooException;
import global-type-body.model.Auth;

public class Login {

    private String _baseUrl;
    private Client client;

    public Login(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/login");
        this.client = client;
    }

    protected Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public String post(Auth body) {
        WebTarget target = this.client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.json(body));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            FooException exception = new FooException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
            if (response.hasEntity()) {
                exception.setMessageBody(response.readEntity(new GenericType<String>() {


                                                             }
                ));
            }
            response.close();
            throw exception;
        }
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

}
