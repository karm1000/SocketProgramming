import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ReadFileTCPC{
    public static void main(String[] args) {

        String filename = "";
        if(args.length>0){
            filename = args[0];
        }else{
            try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in));){
                System.out.println("Enter File Name : ");
                filename = br.readLine();
            }catch(IOException e){
                System.out.println(e);
            }
        }

        try(
            Socket socket = new Socket("localhost",6000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader si = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ){
            out.println(filename);
            sendEncryptedFile(socket, filename);
            // recieveFile(socket, filename);
            // printFile(socket, filename);
            // String line;
            // while((line = si.readLine()) != null){
            //     System.out.println(line);
            // }
        }catch(UnknownHostException e){
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }catch(Exception e){
            System.out.println(e);
        }

    }

    static void recieveFile(Socket socket,String filename){
        try(
            InputStream is = socket.getInputStream();
        ){
            String [] s = filename.split("/");
            FileOutputStream fos = new FileOutputStream("recieved_"+s[s.length-1]);
            byte[] buffer = new byte[1024];
            int bufferRead;
            // System.out.println(is.read()+"---------------");
            // if(is.r==1){
            //     throw new Exception("Exception Occured");
            // }

            while((bufferRead = is.read(buffer)) != -1){
                fos.write(buffer,0,bufferRead);
            }
            System.out.println(filename+" recieved");
            fos.close();
        }catch(FileNotFoundException e){
            System.out.println("Syntax is incorrect it should not have \\  -- "+e);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    static void printFile(Socket socket,String filename){
        try(
            BufferedReader si = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String line;
            while((line = si.readLine()) != null){
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    static void sendEncryptedFile(Socket socket,String filename){
        try(
            OutputStream os = socket.getOutputStream();
        )
        {
            // KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            // SecretKey myDesKey = keyGenerator.generateKey();
            SecretKeySpec secretKeySpec = new SecretKeySpec("MySecretKey12345".getBytes(), "AES");
            IvParameterSpec iv = new IvParameterSpec("MySecretKey12345".getBytes());

            Cipher desCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");


            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            // byte[] fileByte = new byte[(int) file.length()];
            // fis.read(fileByte);
            
            // String fileLines = new String(fileByte,"UTF-8");
            
            desCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,iv);
            // System.out.println(fileLines);
            

            // int paddingLength = 8 - (fileByte.length % 8);
            // byte[] paddedData = new byte[fileByte.length + paddingLength];
            // System.arraycopy(fileByte, 0, paddedData, 0, fileByte.length);
            // byte[] textEncrypted = desCipher.doFinal(paddedData);
            // System.out.println("HI");
            CipherOutputStream cos = new CipherOutputStream(os, desCipher);

            byte[] buffer = new byte[1024];
            int byteRead;
            while((byteRead = fis.read(buffer))!=-1){
                // byte[] encryptedData = desCipher.doFinal(buffer,0,byteRead);
                cos.write(buffer,0,byteRead);
            }
            // os.write(textEncrypted);
            System.out.println("KI");
            // os.flush();
            cos.flush();
            cos.close();


        }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}