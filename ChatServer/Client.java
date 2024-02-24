package ChatServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args){
        try (
            Socket socket = new Socket(InetAddress.getLocalHost(),Integer.parseInt(args[0]));
        ) {
           System.out.println("Client connected");
           System.out.print("Enter Name : ");
           BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
           String name = br.readLine();
           PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
           BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           writer.println(name);

           Thread getMessages = new Thread(()->{
                String message;
                try {
                    while ((message = reader.readLine())!=null) {
                        System.out.println(message);   
                    }           
                } catch (Exception e) {
                    System.out.println(e);
                    // TODO: handle exception
                }
           });
           getMessages.start();

           String message;
           while (true) {
                // System.out.println("Enter Message : ");
                message = br.readLine();
                writer.println(message);
                if(message.equals(":q")){
                    System.out.println("Byebye yebybeiybfiwb");
                    break;
                }
           }
        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
    }
}
