import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ReadFileUDPC {
    private static final int PORT = 7000;
    public static void main(String[] args) {
        try( DatagramSocket ds = new DatagramSocket();) {
           
            String filename = "example.txt";
            byte[] filenameBytes = filename.getBytes();
            DatagramPacket dp1 = new DatagramPacket(filenameBytes,filenameBytes.length,InetAddress.getLocalHost(),PORT);
            ds.send(dp1);

            byte[] b = new byte[1024];
            DatagramPacket rPacket = new DatagramPacket(b, b.length);
            ds.receive(rPacket);
            // rPacket.

            String file = new String(rPacket.getData());

            System.out.println(file);
            
        } catch (SocketException e) {
            System.out.println(e);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
