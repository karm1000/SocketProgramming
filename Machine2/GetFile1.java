package Machine2;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class GetFile1 {
    
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6001);
        System.out.println("Server on "+6001);
        Socket s = serverSocket.accept();

        try (
            InputStream is = s.getInputStream();
            FileOutputStream fos = new FileOutputStream("./Machine2/file.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ) {
            byte[] buf = new byte[1024];
            int byteRead;
            while((byteRead=is.read(buf))!=-1){
                fos.write(buf,0,byteRead);
            }
            
        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }


    }
}
