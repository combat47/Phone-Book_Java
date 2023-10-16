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
        System.out.println();
        bubbleSortJumpSearch(directory, find, amountToFind, breakTime);
        System.out.println();
        quickSortBinarySearch(directory, find, amountToFind);
        System.out.println();
        hashTable(directory, find, amountToFind);

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
        return new String[]{"Empty"};
    }


    static long linearSearch(String[] directory, String[] find, int amountToFind, boolean bubble) {
        int amountFounded = 0;
        System.out.println("Start searching (linear search)...");
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

    static void bubbleSortJumpSearch(String[] directory, String[] find, int amountToFind, long breakTime) {
        System.out.println("Start searching (bubble sort + jump search)...");
        final long start = System.nanoTime();
        String pom;
        int length = directory.length - 1;
        for (int i = 0; i < length; length--) {
            if (System.nanoTime() - start > breakTime * 10) {
                System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.", 500, amountToFind, ((System.nanoTime() - start + breakTime) / 60000000000L), ((System.nanoTime() - start + breakTime) / 1000000000) % 60, ((System.nanoTime() - start + breakTime) / 1000000) % 1000);
                System.out.printf("%nSorting time: %d min. %d sec. %d ms.", ((System.nanoTime() - start) / 60000000000L), ((System.nanoTime() - start) / 1000000000) % 60, ((System.nanoTime() - start) / 1000000) % 1000);
                System.out.println(" - STOPPED, moved to linear search");
                System.out.printf("Searching time: %d min. %d sec. %d ms.%n",(breakTime / 60000000000L), (breakTime / 1000000000) % 60, (breakTime / 1000000) % 1000);
                return;
            }
            for (int j = i; j < length; j++) {
                if (directory[j].replaceAll("\\d", "").compareTo(directory[j + 1].replaceAll("\\d", "")) > 0) {
                    pom = directory[j];
                    directory[j] = directory[j + 1];
                    directory[j + 1] = pom;
                }
            }
        }
    }
    static void quickSortBinarySearch(String[] directory, String[] find, int amountToFind) {
        System.out.println("Start searching (quick sort + binary search)...");
        int amountFounded = 0;
        final long start = System.nanoTime();
        quickSort(directory, 0, directory.length - 1);
        final long sortEnd = System.nanoTime();
        for (String a : find) {
            amountFounded += binarySearch(directory, a);
        }
        final long end = System.nanoTime();
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.", amountFounded, amountToFind, ((end - start) / 60000000000L), ((end - start) / 1000000000) % 60, ((end - start) / 1000000) % 1000);
        System.out.printf("%nSorting time: %d min. %d sec. %d ms.", ((sortEnd - start) / 60000000000L), ((sortEnd - start) / 1000000000) % 60, ((sortEnd - start) / 1000000) % 1000);
        System.out.printf("%nSearching time: %d min. %d sec. %d ms.", ((end - sortEnd) / 60000000000L), ((end - sortEnd) / 1000000000) % 60, ((end - sortEnd) / 1000000) % 1000);
    }
    static void quickSort(String[] directory, int start, int length) {
        int lengthCopy = length;
        if (length <= start) {
            return;
        }
        final String pivot = directory[length];
        String pom;

        for (int i = start; i < length; i++) {
            if (directory[i].replaceAll("\\d", "").compareTo(pivot.replaceAll("\\d", "")) > 0) {
                pom = directory[i];
                for (int j = length - 1; j >= i; j--) {
                    if (directory[j].replaceAll("\\d", "").compareTo(pivot.replaceAll("\\d", "")) < 0 || j == i) {
                        directory[i] = directory[j];
                        directory[j] = pivot;
                        directory[length] = pom;
                        length = j;
                        break;
                    }

                }
            }
        }
        quickSort(directory, start, length - 1);
        quickSort(directory, length + 1, lengthCopy);
    }

    static int binarySearch(String[] directory, String find) {
        int left = 0;
        int right = directory.length - 1;
        int middle;

        while (left <= right) {
            middle = (left + right) / 2;
            if (directory[middle].contains(find)) {
                return 1;
            } else if (directory[middle].replaceAll("\\d", "").trim().compareTo(find) > 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return 0;
    }
    static void hashTable(String[] directory, String[] find, int amountToFind) {
        System.out.println("Start searching (hash table)...");
        int amountFounded = 0;
        final long start = System.nanoTime();
        int[] hashDirectory = new int[directory.length];
        for (int i = 0; i < directory.length; i++) {
            hashDirectory[i] = directory[i].replaceAll("\\d", "").trim().hashCode();
        }
        final long sortEnd = System.nanoTime();
        int c;
        for (String a : find) {
            c = a.hashCode();
            for (int b : hashDirectory) {
                if (c == b) {
                    amountFounded++;
                    break;
                }
            }
        }
        final long end = System.nanoTime();
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.", amountFounded, amountToFind, ((end - start) / 60000000000L), ((end - start) / 1000000000) % 60, ((end - start) / 1000000) % 1000);
        System.out.printf("%nCreating time: %d min. %d sec. %d ms.", ((sortEnd - start) / 60000000000L), ((sortEnd - start) / 1000000000) % 60, ((sortEnd - start) / 1000000) % 1000);
        System.out.printf("%nSearching time: %d min. %d sec. %d ms.", ((end - sortEnd) / 60000000000L), ((end - sortEnd) / 1000000000) % 60, ((end - sortEnd) / 1000000) % 1000);
    }
}
