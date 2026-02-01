package ca.bcit.comp3760.assign1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * JobAssignmentFinder is a simple program that reads in a file that contains
 * a 2D matrix of values indicating the benefit of a person doing a job.
 * From the read benefit matrix, the program finds the maximum benefit
 * from the matrix by checking all permutations generated for a matrix of size
 * NxN. Program provides methods to get the maximum benefit value as well as the
 * permutation for the maximum benefit and helper methods such as string representations
 * of the 2D benefit matrix.
 *
 * @version 1.0.0
 * @author Braeden Sowinski
 */
public class JobAssignmentFinder
{
    private int[][] benefitMatrix;

    /**
     * Recursive decrease-and-conquer algorithm to generate a list of all
     * permutations of the numbers 0..N-1. This follows the "decrease by 1" pattern
     * of decrease and conquer algorithms.
     * This method returns an ArrayList of ArrayLists. One permutation is an
     * ArrayList containing 0,1,2,...,N-1 in some order. The final result is an
     * ArrayList containing N! of those permutations.
     *
     * @param N size to generate permutations
     * @return permutations
     */
    @SuppressWarnings("unchecked")
    private ArrayList<ArrayList<Integer>> getPermutations(final int N)
    {
        final ArrayList<ArrayList<Integer>> results;
        results = new ArrayList<>();

        /*
         * This isn't a "base case", it's a "null case". This function does not call
         * itself with an argument of zero, but we can't prevent another caller from
         * doing so. It's a weird result, though. The list of permutations has one
         * permutation, but the one permutation is empty (0 elements).
         */
        if (N == 0)
        {
            final ArrayList<Integer> emptyList;
            emptyList = new ArrayList<Integer>();
            results.add(emptyList);
        }
        else if (N == 1)
        {
            /*
             * Now THIS is the base case. Create an ArrayList with a single integer, and add
             * it to the results list.
             */
            final ArrayList<Integer> singleton;
            singleton = new ArrayList<Integer>();
            singleton.add(0);
            results.add(singleton);
        }
        else
        {
            /*
             * And: the main part. First a recursive call (this is a decrease and conquer
             * algorithm) to get all the permutations of length N-1.
             */
            final ArrayList<ArrayList<Integer>> smallList;
            smallList = getPermutations(N - 1);

            /*
             * Iterate over the list of smaller permutations and insert the value 'N-1' into
             * every permutation in every possible position, adding each new permutation to
             * the big list of permutations.
             */
            for (final ArrayList<Integer> perm : smallList)
            {
                /*
                 * Add 'N-1' -- the biggest number in the new permutation -- at each of the
                 * positions from ..N-1.
                 */
                for (int i = 0; i < perm.size(); i++)
                {
                    final ArrayList<Integer> newPerm;
                    newPerm = (ArrayList<Integer>) perm.clone();
                    newPerm.add(i, N - 1);
                    results.add(newPerm);
                }

                /*
                 * Add 'N-1' at the end (i.e. at position "size").
                 */
                final ArrayList<Integer> newPerm;
                newPerm = (ArrayList<Integer>) perm.clone();
                newPerm.add(N - 1);
                results.add(newPerm);
            }
        }

        return results;
    }

    /**
     * readDataFile takes an input string of a filename in order to read
     * the data into the benefitMatrix for processing
     * @param filename to read matrix data
     */
    public void readDataFile(final String filename)
    {
        final BufferedReader reader;
        final int size;
        String line;

        try
        {
            reader = new BufferedReader(new FileReader(filename));
            size = Integer.parseInt(reader.readLine());

            this.benefitMatrix = new int[size][size];

            int row = 0;
            while ((line = reader.readLine()) != null)
            {
                this.benefitMatrix[row] = Arrays.stream(line.split(" "))
                                                .mapToInt(Integer::parseInt).toArray();
                row++;
            }

            reader.close();
        }
        catch (final IOException e)
        {
            System.err.println("Failed to open file \"" + filename + "\"");
        }
    }

    /**
     * getInputSize returns the dimensions of the benefit matrix,
     * i.e. if matrix is NxN then size is N
     * @return the size of the benefit matrix
     */
    public int getInputSize()
    {
        return this.benefitMatrix == null ? -1 : this.benefitMatrix.length;
    }

    /**
     * getBenefitMatrix that was read in from an input file
     * @return benefit matrix
     */
    public int[][] getBenefitMatrix()
    {
        return this.benefitMatrix;
    }

    /**
     * benefitMatrixToString constructs a string representation
     * of the 2d benefit matrix
     * @return string representation of benefit matrix
     */
    public String benefitMatrixToString()
    {
        final StringBuilder str;
        str = new StringBuilder();

        for (final int[] row : this.benefitMatrix)
        {
            for (final int i : row)
            {
                str.append(i);
                str.append(" ");
            }
            str.append("\n");
        }

        return str.toString();
    }

    /**
     * getMaxAssignment finds the solution to get the maximum value from
     * the benefit matrix by brute-force checking all permutations
     * @return a list of integers indicating which person should take which assignment for maximum benefits
     */
    public List<Integer> getMaxAssignment()
    {
        return getPermutations(this.benefitMatrix.length)
                .stream()
                .max(Comparator.comparingInt(perm -> {
                    int sum = 0;
                    for (int i = 0; i < perm.size(); i++)
                    {
                        sum += this.getBenefit(i, perm.get(i));
                    }

                    return sum;
                })).orElse(new ArrayList<>());
    }

    /**
     * getMaxAssignmentTotalValue gets the max assignment and
     * sums the value of benefits for the given max assignment solution
     * @return the sum of benefits of a max assignment solution
     */
    public int getMaxAssignmentTotalValue()
    {
        final List<Integer> perm;
        perm = this.getMaxAssignment();

        int sum = 0;
        for (int i = 0; i < perm.size(); i++)
        {
            sum += this.getBenefit(i, perm.get(i));
        }
        return sum;
    }

    /**
     * getBenefit from benefit matrix at a given row and column
     * @param row of benefit within matrix
     * @param col of benefit within matrix
     * @return benefit at row and col
     */
    public int getBenefit(int row, int col)
    {
        return this.benefitMatrix[row][col];
    }
}
