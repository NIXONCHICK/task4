package task4.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class URLReader {
    public static void readContent(String url) throws Exception {
        URL site = new URL(url);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(site.openStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a URL: ");
            String url = scanner.nextLine();
            try {
                readContent(url);
                break;
            } catch (Exception e) {
                System.out.println("Invalid URL or unable to access. Please try again.");
            }
        }
    }
}
