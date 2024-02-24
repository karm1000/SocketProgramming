package ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

/**
 * ChatServer
 */
public class ChatServer {
    
    HashSet<ClientHandler> set = new HashSet<>();
    static int PORT = 5000;
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        try (ServerSocket server = new ServerSocket(PORT);)
        {
            System.out.println("Server running on "+PORT);
            while (true) {
                ClientHandler cHandler = new ClientHandler(server.accept(),chatServer);
                chatServer.set.add(cHandler);
                cHandler.start();
            }
            
        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
      
    }
    

    public void broadcast(String message,ClientHandler exclude){
        for(ClientHandler handler:set){
            if(handler!=exclude){
                handler.sendMessage(message);
            }
        }
    }
    
}

class ClientHandler extends Thread{
    Socket client;
    BufferedReader reader;
    String name;
    ChatServer server;
    PrintWriter writer;

    ClientHandler(Socket s,ChatServer chatServer) throws IOException{
        this.client = s;
        this.server = chatServer;
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        writer = new PrintWriter(client.getOutputStream(),true);
        String m = reader.readLine();
        this.name = m;
        server.broadcast(name+" joined chat room", null);
    }

    public Socket getClient() {
        return client;
    }

    boolean sendMessage(String s){
        try {
            writer.println(s);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
        return false;
    }
    
    @Override
    public void run() {
        String message;
        try {
            while((message=reader.readLine())!=null){
                if(message.equals(":q")){
                    server.set.remove(this);
                    client.close();
                    server.broadcast(name+" left",this);
                    return;
                }else{
                    message = name+" : "+message;
                    System.out.println(message);
                    server.broadcast(message, this);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

