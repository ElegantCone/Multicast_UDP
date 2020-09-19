import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Sender extends Thread {
    MulticastSocket socket;
    InetAddress group;
    Integer port;

    Sender(MulticastSocket socket, InetAddress group, Integer port){
        this.socket = socket;
        this.group = group;
        this.port = port;
    }

    @Override
    public void run() {
        String msg = "Hello, world!";
        DatagramPacket sendMsg = new DatagramPacket(msg.getBytes(), msg.length(), group, port);
        while(true){
            try {
                socket.send(sendMsg);
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
