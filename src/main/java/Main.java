import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        int expectedServers = 4;

        var threads = new ArrayList<Thread>();
        for (int i = 0; i < expectedServers; i++) {
            var server = new MulticastEchoServer();
            threads.add(server);
            server.start();
        }

        var client = new MulticastingClient(expectedServers);
        int serversDiscovered = client.discoverServers("hello server");
        System.out.println("Servers discovered: " + serversDiscovered);

        client.discoverServers("end");
        client.close();

        for (var t : threads) {
            t.join();
            System.out.println("joined");
        }
    }
}
