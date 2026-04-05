package ca.bcit.comp3760.lab05;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class JobAssignmentFinderDriver
{
    public static void main(final String[] args)
    {
        final String[] fileNames = {
                "./data/lab05/data11.txt",
                "./data/lab05/data12.txt",
                "./data/lab05/data37.txt",
                "./data/lab05/data148.txt",
                "./data/lab05/donutdata.txt",
        };

        final JobAssignmentFinder finder;
        finder = new JobAssignmentFinder();

        for (int i = 0; i < fileNames.length; i++)
        {
            testFile(finder, fileNames[i]);
            System.out.println();
        }
    }

    private static void testFile(final JobAssignmentFinder finder, final String fileName)
    {
        try
        {
            finder.readDataFile(fileName);

            final ArrayList<Integer> greedyAssignment;
            final int greedyTotal;

            greedyAssignment = finder.getGreedyAssignment();
            greedyTotal      = finder.greedyAssignmentTotalValue();

            System.out.println("File: " + fileName);
            System.out.println("Greedy assignment: " + greedyAssignment);
            System.out.println("Greedy total value: " + greedyTotal);
        }
        catch (final FileNotFoundException ex)
        {
            System.out.println("Could not open file: " + fileName);
        }
    }
}