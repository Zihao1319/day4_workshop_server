package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws IOException {

        String dirPath = "data";
        String fileName = "cookie";

        // create a file directory object
        File newDirectory = new File(dirPath);

        if (newDirectory.exists()) {
            System.out.println("Directory already exists");

        } else {
            newDirectory.mkdir();
        }

        Cookie cookie = new Cookie();

        try {
            cookie.readCookieFile(dirPath, fileName);
            // cookie.showCookies();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

        System.out.println("Waiting for connection...");

        ServerSocket ss = new ServerSocket(3032);
        Socket s = ss.accept(); // establish connection and waiting for client to connect
        System.out.println("connected");

        try {
            InputStream is = s.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            OutputStream os = s.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            String msgReceived = "";

            while (!msgReceived.equals("close")) {
                msgReceived = dis.readUTF();

                if (msgReceived.equalsIgnoreCase("get-cookie")) {
                    String cookieValue = cookie.returnCookies();
                    System.out.println(cookieValue);

                    dos.writeUTF(cookieValue);
                    dos.flush();

                } else {
                    dos.writeUTF("No such commands");
                    dos.flush();
                }

            }

            // try (OutputStream os = s.getOutputStream()) {

            // BufferedOutputStream bos = new BufferedOutputStream(os);
            // DataOutputStream dos = new DataOutputStream(bos);

            // while (!msgReceived.equals("close")) {
            // msgReceived = dis.readUTF();

            // if (msgReceived.equalsIgnoreCase("get-cookie")) {
            // String cookieValue = cookie.returnCookies();
            // System.out.println(cookieValue);

            // dos.writeUTF(cookieValue);
            // dos.flush();

            // } else {
            // dos.writeUTF("No such commands");
            // dos.flush();
            // }

            // }
            // dos.close();
            // bos.close();
            // os.close();
            // }

            // catch (EOFException e) {

            // s.close();
            // ss.close();

            // }

            dos.close();
            bos.close();
            os.close();

            bis.close();
            dis.close();
            is.close();

        } catch (IOException e) {
            s.close();
            ss.close();
        }
    }
}
