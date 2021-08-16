import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastEchoServer extends Thread {

    protected MulticastSocket socket = null;
    protected InetAddress group = null;

    public MulticastEchoServer() throws IOException {
        socket = new MulticastSocket(4446);
        socket.setReuseAddress(true);
        group = InetAddress.getByName("230.0.0.0");
        socket.joinGroup(group);
    }

    public void run() {
        try {
            while (true) {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                //packet = new DatagramPacket(buf, buf.length, address, port);  // ??
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Server received: " + received + " Length: " + packet.getLength());
                socket.send(packet);
                if (received.equals("end")) {
                    break;
                }
            }
            socket.leaveGroup(group);
            socket.close();
            System.out.println("Thread ending");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
