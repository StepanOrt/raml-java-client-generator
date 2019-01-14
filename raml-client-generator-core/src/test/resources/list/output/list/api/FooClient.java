
package list.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import list.resource.users.Users;


/**
 * This api describes how to access to the users platform
 *
 */
public class FooClient {

    private String _baseUrl;
    private final Users users;

    public FooClient(String baseUrl) {
        _baseUrl = baseUrl;
        users = new Users(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return ClientBuilder.newClient();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static FooClient create(String baseUrl) {
        return new FooClient(baseUrl);
    }

    public Users getUsers() {
        return this.users;
    }

}
