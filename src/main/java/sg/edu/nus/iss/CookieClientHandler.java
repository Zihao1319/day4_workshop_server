package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CookieClientHandler implements Runnable {

    private int portNum;
    private String dirPath;
    private String fileName;

    public CookieClientHandler(int portNum, String dirPath, String fileName) {
        this.portNum = portNum;
        this.dirPath = dirPath;
        this.fileName = fileName;
    }

    public void run() {

        System.out.println("connected");

        Cookie cookie = new Cookie();

        try (ServerSocket ss = new ServerSocket(portNum)) {
            Socket s = ss.accept();
            cookie.readCookieFile(dirPath, fileName);

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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
