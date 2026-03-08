package ca.bcit.comp3760.lab03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HashSimulatorDriver
{
    private static final int NUM_NAMES_IDX = 0;

    /*
     * readNamesFromFile takes an input string of a filename in order to read
     * the data into a list of strings for testing
     * @param filename to list of names, filename must start with number of names in file.
     *        e.g 10names.txt contains 10 names
     */
    private static String[] readNamesFromFile(final String filename)
        throws IOException
    {
        final BufferedReader reader;
        final String[] names;

        final int size;

        size = Integer.parseInt(filename.split("names")[NUM_NAMES_IDX]);

        String line;

        reader = new BufferedReader(new FileReader("./data/lab03/" + filename));
        names  = new String[size];

        int i = 0;
        while ((line = reader.readLine()) != null)
        {
            names[i] = line;
            i++;
        }

        reader.close();

        return names;
    }

    private static void runAndPrint(
        final HashSimulator s,
        final String[] names,
        final String label
    ) {
        int n = names.length;

        int[] sizes = {n, 2 * n, 5 * n, 10 * n, 100 * n};

        System.out.println("\nFile: " + label);
        System.out.println("HT Size | H1 Coll  H1 Probe | H2 Coll  H2 Probe | H3 Coll  H3 Probe");
        System.out.println("---------------------------------------------------------------------");

        for (int size : sizes)
        {
            int[] results = s.runHashSimulation(names, size);

            System.out.printf(
                "%7d | %5d %11d | %5d %11d | %5d %8d\n",
                size,
                results[0], results[1],
                results[2], results[3],
                results[4], results[5]
            );
        }
    }

    public static void main(final String[] args)
        throws IOException
    {
        final HashSimulator s;
        s = new HashSimulator();

        final String[] names1;
        final String[] names2;
        final String[] names3;

        names1 = readNamesFromFile("37names.txt");
        names2 = readNamesFromFile("703names.txt");
        names3 = readNamesFromFile("5777names.txt");

        runAndPrint(s, names1, "37names.txt");
        runAndPrint(s, names2, "703names.txt");
        runAndPrint(s, names3, "5777names.txt");
    }
}
