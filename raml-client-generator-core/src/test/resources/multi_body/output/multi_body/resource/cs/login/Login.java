
package multi_body.resource.cs.login;

import java.io.InputStream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import multi_body.exceptions.MultiBodyException;
import multi_body.resource.cs.login.model.LoginPOSTBody;

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

    public multi_body.resource.cs.login.model.LoginPOSTResponse post(LoginPOSTBody body) {
        WebTarget target = this.client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(javax.ws.rs.client.Entity.json(body));
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            MultiBodyException exception = new MultiBodyException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
            if (response.hasEntity()) {
                exception.setMessageBody(response.readEntity(new GenericType<String>() {


                                                             }
                ));
            }
            response.close();
            throw exception;
        }
        multi_body.resource.cs.login.model.LoginPOSTResponse entity = response.readEntity(multi_body.resource.cs.login.model.LoginPOSTResponse.class);
        response.close();
        return entity;
    }

    public multi_body.resource.cs.login.model.LoginPOSTResponse post(InputStream body) {
        WebTarget target = this.client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(javax.ws.rs.client.Entity.entity(body, javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE));
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            MultiBodyException exception = new MultiBodyException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
            if (response.hasEntity()) {
                exception.setMessageBody(response.readEntity(new GenericType<String>() {


                                                             }
                ));
            }
            response.close();
            throw exception;
        }
        multi_body.resource.cs.login.model.LoginPOSTResponse entity = response.readEntity(multi_body.resource.cs.login.model.LoginPOSTResponse.class);
        response.close();
        return entity;
    }

    public multi_body.resource.cs.login.model.LoginGETResponse get() {
        WebTarget target = this.client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            MultiBodyException exception = new MultiBodyException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
            if (response.hasEntity()) {
                exception.setMessageBody(response.readEntity(new GenericType<String>() {


                                                             }
                ));
            }
            response.close();
            throw exception;
        }
        multi_body.resource.cs.login.model.LoginGETResponse entity = response.readEntity(multi_body.resource.cs.login.model.LoginGETResponse.class);
        response.close();
        return entity;
    }

}
