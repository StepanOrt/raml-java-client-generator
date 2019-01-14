
package simple.resource.cs.id;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import simple.exceptions.FooException;
import simple.resource.cs.id.bar.Bar;

public class Id {

    private String _baseUrl;
    private Client client;
    private final Bar bar;

    public Id(String baseUrl, Client client, String uriParam) {
        _baseUrl = (baseUrl +("/"+ uriParam));
        this.client = client;
        bar = new Bar(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public void get() {
        WebTarget target = this.client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
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
    }

    public Bar getBar() {
        return this.bar;
    }

}
