import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        int expectedServers = 4;
        //var client = initializeForExpectedServers(expectedServers);

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

        Thread.sleep(1000);

        for (var t : threads) {
            t.join();
            System.out.println("joined");
        }
    }

    private static MulticastingClient initializeForExpectedServers(int expectedServers) throws Exception {
        for (int i = 0; i < expectedServers; i++) {
            new MulticastEchoServer().start();
        }

        return new MulticastingClient(expectedServers);
    }
}
