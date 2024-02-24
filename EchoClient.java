import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class EchoClient {
    public static void main(String[] args) {
        try (
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Socket socket = new Socket(InetAddress.getLocalHost(),7000);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
        ) {
            
            while (true) {
                System.out.print("Enter Message : ");
                String m = br.readLine();
                os.write(m.getBytes());
                if(m.equals(":q")){
                    break;
                }
                byte[] buffer = new byte[1024];
                int byteRead;
                // while((byteRead=is.read(buffer))!=-1){

                // }
                is.read(buffer);
                String echom = new String(buffer,"UTF-8");
                System.out.println("Server "+socket.getInetAddress()+" Port "+socket.getPort()+"Echoed Message :"+echom);
            }

        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
    }
}
