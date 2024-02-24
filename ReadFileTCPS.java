import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ReadFileTCPS {
    private static final int PORT = 6000;

    public static void main(String[] args) {
        try (
            ServerSocket serverSocket = new ServerSocket(PORT);) {
            System.out.println("Server Listening On Port : " + PORT);
            Socket s = serverSocket.accept();
            System.out.println("Client connected : " + s.getInetAddress());
            // sendFile(s);
            // sendStringFile(s);
            recieveAndDecryptFile(s);
            
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    static void sendStringFile(Socket socket){
        try(
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
        ) {
            
            String filename = in.readLine();
            System.out.println(filename);

            if (filename.trim().length() == 0) {
                System.out.println("Hello");
                pw.println("NO INPUT GIVEN");
            } else {
                File file = new File(filename);
                if (!file.exists()) {
                    pw.println(filename+" does not exist.");
                } else {
                    BufferedReader filebr = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = filebr.readLine()) != null) {
                        pw.println(line);
                    }

                    pw.flush();
                    filebr.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    static void sendFile(Socket socket) {
        try {
            // PrintWriter pw = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String filename = in.readLine();
            OutputStream os = socket.getOutputStream();

            System.out.println(filename);

            if (filename.trim().length() == 0) {
                System.out.println("Hello");
                os.write(1);
            }else if(filename.contains("\\")){
                System.out.println("I");
                os.write(1);
            }else {
                File file = new File(filename);
                if (!file.exists()) {
                    os.write(2);
                } else {
                    FileInputStream filebr = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    String line;
                    while ((bytesRead = filebr.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                        // System.out.println(line);
                    }
                    filebr.close();
                    
                }
            }

            os.close();
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    static void recieveAndDecryptFile(Socket socket){
        try (
            InputStream is = socket.getInputStream();
            ByteArrayOutputStream byteos = new ByteArrayOutputStream();
            FileOutputStream fose = new FileOutputStream("ecrypted-file.txt");
            FileOutputStream fosd = new FileOutputStream("decrypted-file.txt");
        ) {
            // KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            // SecretKey myDesKey = new SecretKeySpec("1234".getBytes(), "DES");
            SecretKeySpec secretKeySpec = new SecretKeySpec("MySecretKey12345".getBytes(), "AES");
            IvParameterSpec iv = new IvParameterSpec("MySecretKey12345".getBytes());
            Cipher desCipher;
            desCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");


            // byte[] text = "No body can see me.".getBytes("UTF8");


            // desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            // byte[] textEncrypted = desCipher.doFinal(text);

            // String s = new String(textEncrypted);
            // System.out.println(s);


            // System.out.println(is.readAllBytes().length+"----");
            // is.reset();
            // System.out.println();
            byte[] buf = new byte[4096];
            int byteRead;

            // byte[] data = is.readAllBytes();    
            desCipher.init(Cipher.DECRYPT_MODE, secretKeySpec,iv);
            CipherInputStream cis = new CipherInputStream(is, desCipher);
            while((byteRead=cis.read(buf))!=-1){
                System.out.println(byteRead+"-----------");
                // byteos.write(buf,0,byteRead);
                // fose.write(buf,0,byteRead);
                // byte[] decryptedData = desCipher.doFinal(buf,0,byteRead);
                fosd.write(buf,0,byteRead);
                System.out.println("-------------");
            }

            // byte[] encryptedData = byteos.toByteArray();

            cis.close();

            // int paddingLength = 8 - (encryptedData.length % 8);
            // byte[] paddedData = new byte[encryptedData.length + paddingLength];
            // System.arraycopy(encryptedData, 0, paddedData, 0, encryptedData.length);
            // byte[] textEncrypted = desCipher.doFinal(paddedData);

            // byte[] textDecrypted = desCipher.doFinal(paddedData);

            // fosd.write(new String(textDecrypted,"UTF-8").getBytes());


        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            // TODO: handle exception
        }
    }
}
