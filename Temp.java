import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Temp {
    public static void main(String[] args) throws Exception {

        // SecretKeySpec secretKeySpec = new SecretKeySpec("MySecretKey12345".getBytes(), "AES");
        // Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // IvParameterSpec iv = new IvParameterSpec("MySecretKey12345".getBytes());
        // cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,iv);

        // File file = new File("example.txt");
        // FileInputStream fis = new FileInputStream(file);
        // ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // int byteRead;
        // byte[] data = new byte[(int)file.length()];
        
        // fis.read(data);

        // byte[] eData = cipher.doFinal(data);

        // // while((byteRead = fis.read(data))!=-1){
        // //     baos.write(data, 0, byteRead);
        // // }

        // String s = new String(eData,"UTF-8");
        // System.out.println(s);

        // cipher.init(Cipher.DECRYPT_MODE, secretKeySpec,iv);
        
        // byte[] dData = cipher.doFinal(eData);
        // String d = new String(dData,"UTF-8");
        // System.out.println(d);
        // try (
        //     // FileOutputStream fos = new FileOutputStream(file);
        // ) {
            
        // } catch (Exception e) {
        //     System.out.println(e);
        //     // TODO: handle exception
        // }

        // File file = new File("some.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader("some.txt"))) {
            // Read and print the first 10 characters
            char[] buffer = new char[10];
            reader.read(buffer);
            System.out.println("First 10 characters: " + new String(buffer));

            // Mark the current position in the stream
            reader.mark(5);

            // Read and print the next 10 characters
            buffer = new char[10];
            reader.read(buffer);
            System.out.println("Next 10 characters: " + new String(buffer));

            // Reset the stream back to the marked position
            reader.reset();
            Reader
            // Read and print the next 10 characters again
            buffer = new char[10];
            reader.read(buffer);
            System.out.println("Next 10 characters after reset: " + new String(buffer));
          
            // reader.reset();
            buffer = new char[10];
            
            reader.read(buffer);
            System.out.println("Next 10 characters after reset: " + new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

// public class MarkResetExample {
//     public static void main(String[] args) {
//         try (BufferedReader reader = new BufferedReader(new FileReader("example.txt"))) {
//             // Read and print the first 10 characters
//             char[] buffer = new char[10];
//             reader.read(buffer);
//             System.out.println("First 10 characters: " + new String(buffer));

//             // Mark the current position in the stream
//             reader.mark(20);

//             // Read and print the next 10 characters
//             buffer = new char[10];
//             reader.read(buffer);
//             System.out.println("Next 10 characters: " + new String(buffer));

//             // Reset the stream back to the marked position
//             reader.reset();

//             // Read and print the next 10 characters again
//             buffer = new char[10];
//             reader.read(buffer);
//             System.out.println("Next 10 characters after reset: " + new String(buffer));
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }
