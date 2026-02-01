package ca.bcit.comp3760.assign1;

import java.util.List;

/**
 * JobAssignmentFinderDriver to test JobAssignmentFinder
 * @version 1.0.0
 * @author Braeden Sowinski
 */
public class JobAssignmentFinderDriver
{
    /**
     * debug solution to prove it works
     * @param f to call JobAssignmentFinder methods
     * @param filename to read benefit data
     */
    private static void debug(final JobAssignmentFinder f, final String filename)
    {
        final JobAssignmentFinder test;
        final List<Integer> maxAssign;

        f.readDataFile("./data/" + filename);

        System.out.printf("File: %s\n", filename);
        maxAssign = f.getMaxAssignment();
        System.out.printf("Max Assignment: %s\n", maxAssign.toString());
        System.out.printf("Total Value: %d\n\n", f.getMaxAssignmentTotalValue());
    }

    /**
     * main program entry for testing purposes
     * @param args unused
     */
    public static void main(final String[] args)
    {
        final JobAssignmentFinder test;

        test = new JobAssignmentFinder();

        for (int i = 0; i <= 5; i++)
        {
            debug(test, "data" + i + ".txt");
        }
    }
}
