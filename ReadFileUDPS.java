import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFileUDPS {
    private static final int PORT = 7000;
    public static void main(String[] args) {
        try(
            DatagramSocket ds = new DatagramSocket(PORT);
        ) {
            byte[] filenameBytes = new byte[1024];
            DatagramPacket packet = new DatagramPacket(filenameBytes, filenameBytes.length);
            ds.receive(packet);

            String filename = new String(packet.getData());
            System.out.println(filename);
            File file = new File(filename.trim());
            
            String data = "";
            byte[] fileData = new byte[0]; 
            try{
            // try(BufferedReader fileReader = new BufferedReader(new FileReader(file))){
                // String line = "";
                // while((line = fileReader.readLine())!=null){
                //     data += line;
                // }
                fileData = Files.readAllBytes(Paths.get(filename.trim()));


            }catch(Exception e){
                System.out.println(e);
            }
            // fileData = data.getBytes();
            DatagramPacket wholeFile = new DatagramPacket(fileData,fileData.length,InetAddress.getLocalHost(),packet.getPort());
            ds.send(wholeFile);

        } catch (Exception e) {
            System.out.println(e);
        }
    }   
}
