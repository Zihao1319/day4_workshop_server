package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {

    // String dirPath = "./data";
    // String fileName = "cookie.txt";

    List<String> cookieItems = null;

    public void readCookieFile(String dirPath, String fileName) throws FileNotFoundException, IOException {
        cookieItems = new ArrayList<>();

        File file = new File(dirPath + File.separator + fileName);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String readString;

        try {

            while ((readString = br.readLine()) != null) {
                cookieItems.add(readString);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();

        }
    }

    public String returnCookies() {
        Random rand = new Random();

        if (cookieItems != null) {
            return cookieItems.get(rand.nextInt(cookieItems.size()));

        } else {
            return "There is no cookies file";
        }

    }

    public void showCookies() {

        if (cookieItems != null) {
            cookieItems.forEach(cookie -> {
                System.out.println(cookie);
            });

        } else {
            System.out.println("No cookies");
        }
    }

}
