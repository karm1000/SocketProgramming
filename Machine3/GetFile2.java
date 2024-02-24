package Machine3;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GetFile2 {
    
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6002);
        System.out.println("Server on "+6002);
        Socket s = serverSocket.accept();

        try (
            InputStream is = s.getInputStream();
            FileOutputStream fos = new FileOutputStream("./Machine3/file.txt");
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

