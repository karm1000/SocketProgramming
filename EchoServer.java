import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class EchoServer {
    public static void main(String[] args) {
        try (
            ServerSocket serverSocket = new ServerSocket(7000);
            // InputStream is = socket.getInputStream();
            // OutputStream os = socket.getOutputStream();
            ) {
                
            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("Client Connected : "+socket.getInetAddress()+"/"+socket.getPort());

                try(

                    OutputStream os = socket.getOutputStream();
                    InputStream is = socket.getInputStream();
                    // BufferedReader br = new BufferedReader(new InputStreamReader(is));
                )
                {
                    // System.out.println("XX");
                    byte[] buf = new byte[1024];
                    int byteRead;
                    String line;
                    // System.out.println("YY");
                    // is.read();
                    // ArrayList<Byte> a = new ArrayList<>(1024);
                    // ByteArrayOutputStream b = new ByteArrayOutputStream(1024);
                    while((byteRead=is.read(buf))!=-1){         
                        String m = new String(buf,"UTF-8");
                        if(m.equals(":q")) break;
                        System.out.println("Client "+socket.getInetAddress()+" PORT "+socket.getPort()+" Message "+m);
                        os.write(m.getBytes());
                        // Arrays.fill(buf,Byte.parseByte("0"));
                        buf = new byte[1024];
                    }
                    
                } catch (Exception e) {
                    System.out.println(e);
                    // TODO: handle exception
                }
                socket.close();
            }

            // BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // loop: while (true) {
                
                
                // System.out.println("Hi");
                // ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // while((byteRead=is.read(buffer))!=-1){
                //     String s = new String(buffer,"UTF-8");
                //     if(s.equals(":q")) break;
                //     System.out.println("Client "+socket.getInetAddress()+" Port :" + socket.getPort()+" Message -> "+s);
                //     os.write(buffer);
                // }
            // }
            

        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
    }
}
