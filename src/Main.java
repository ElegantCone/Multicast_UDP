import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main (String[] args) throws IOException, InterruptedException {
        InetAddress group = InetAddress.getByName(args[0]);
        int port = Integer.parseInt(args[1]);
        PCSocket socket = new PCSocket();
        try{
            socket.run(group, port);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
