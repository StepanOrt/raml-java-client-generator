
package global-type-return.resource.cs.login;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import global-type-return.exceptions.FooException;

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

    public global-type-return.model.User post() {
        WebTarget target = this.client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(null);
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
        global-type-return.model.User entity = response.readEntity(global-type-return.model.User.class);
        response.close();
        return entity;
    }

}
