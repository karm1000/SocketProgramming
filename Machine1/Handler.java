package Machine1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Handler implements Runnable {
    int PORT;
    Socket socket;
    byte[] d;

    public Handler(int PORT,byte[] b){
        this.PORT = PORT;
        // this.bis = new BufferedInputStream(fis);
        this.d = b;
        try{
            socket = new Socket(InetAddress.getLocalHost(),PORT);
            System.out.println("Connection with "+PORT);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void run(){
        // byte[] buf = new byte[1024];
        // int byteRead;
        try (
            OutputStream os =  socket.getOutputStream();
            // ByteArrayInputStream bais = new ByteArrayInputStream(d);
        ) {
            
            os.write(d);
            os.flush();

        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {

        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in));){
            System.out.println("Enter Some String : ");
            String data = br.readLine();
            try (
                FileOutputStream fos = new FileOutputStream("./Machine1/file.txt");
                PrintWriter pw = new PrintWriter(fos);
            ) {
                pw.println(data);
                pw.flush();
            } catch (Exception e) {
                System.out.println(e);
            }
            File file = new File("file.txt");

            try (
                FileInputStream fis = new FileInputStream(file);
            ) {
                byte[] fileByte = new byte[(int) file.length()];
                fis.read(fileByte);
                int[] ports = new int[]{6001,6002,6003};
                ArrayList<Thread> threads = new ArrayList<>();
                System.out.println("Some");
                for(int p:ports){
                    Thread t = new Thread(new Handler(p,fileByte));
                    threads.add(t);
                    t.start();
                }

                System.out.println("Some");
                // for(Thread t:threads){
                //     t.join();
                // }

            } catch (Exception e) {
                System.out.println(e);
                // TODO: handle exception
            }

        }catch(Exception e){
            System.out.println(e);
        }

    }
}