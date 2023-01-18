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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

        System.out.println("Waiting for connection...");

        CookieClientHandler cookieRI = new CookieClientHandler(3032, dirPath, fileName);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(cookieRI);
        executorService.shutdown();

    }
}
