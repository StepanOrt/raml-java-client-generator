import org.mule.example.api.ClientAPIClient;


public class ClientExample {
    public static void main(String[] args) {
        ClientAPIClient.create().getUsers().userId("luis").get();

    }
}
