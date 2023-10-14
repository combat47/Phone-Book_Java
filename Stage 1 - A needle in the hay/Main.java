package phonebook;

import java.util.Scanner;
import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        final String finPath = "C:\\Users\\find.txt";
        final String dirPath = "C:\\Users\\directory.txt";
        final String[] directory = fileToArr(dirPath);
        final String[] find = fileToArr(finPath);
        final int amountToFind = find.length;

        long breakTime = linearSearch(directory, find, amountToFind, false);
    }
    static String[] fileToArr(String filePath) {
        File fileFin = new File(filePath);
        try (Scanner sc = new Scanner(fileFin)) {
            ArrayList<String> list = new ArrayList<>();
            while (sc.hasNext()) {
                list.add(sc.nextLine());
            }
            return list.toArray(new String[0]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new String[] {"Empty"};
    }

    static long linearSearch(String[] directory, String[] find, int amountToFind, boolean bubble) {
        int amountFounded = 0;
        System.out.println("Start searching ...");
        final long start = System.nanoTime();

        for (String f : find) {
            for (String d : directory) {
                if (d.contains(f)) {
                    amountFounded++;
                    break;
                }
            }
        }

        final long end = System.nanoTime();

        if (bubble) {
            System.out.printf("Searching time: %d min. %d sec. %d ms.%n", ((end - start) / 60000000000L), ((end - start) / 1000000000) % 60, ((end - start) / 1000000) % 1000);
        } else {
            System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n", amountFounded, amountToFind, ((end - start) / 60000000000L), ((end - start) / 1000000000) % 60, ((end - start) / 1000000) % 1000);
        }
        return end - start;
    }
}
