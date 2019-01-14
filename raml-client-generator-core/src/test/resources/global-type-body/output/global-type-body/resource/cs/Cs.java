
package global-type-body.resource.cs;

import javax.ws.rs.client.Client;
import global-type-body.resource.cs.login.Login;

public class Cs {

    private String _baseUrl;
    private Client client;
    private final Login login;

    public Cs(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/cs");
        this.client = client;
        login = new Login(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public Login getLogin() {
        return this.login;
    }

}
