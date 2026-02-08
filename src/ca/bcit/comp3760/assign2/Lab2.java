package ca.bcit.comp3760.assign2;

import java.util.ArrayList;

/**
 * Lab2 Generates palindromes of length N with
 * the characters A, B, and C.
 *
 * @author Braeden Sowinski
 * @version 1.0.0
 */
public class Lab2
{
    /**
     * generate a list of all palindrome sequences of a given length N
     * for the characters A, B, and C.
     * @param n length of palindrome to generate
     * @return ArrayList of strings containing all palindromes of length n
     */
    public ArrayList<String> generatePalindromeSequences(final int n)
    {
        final ArrayList<String> sequences;
        sequences = new ArrayList<>();

        if (n < 1)
        {
            /* we can assume n in greater than 1, but we'll
               check just in case. */
            return sequences;
        }
        else if (n <= 2)
        {
            sequences.add("A".repeat(n));
            sequences.add("B".repeat(n));
            sequences.add("C".repeat(n));
            return sequences;
        }

        final ArrayList<String> subsequences;
        subsequences = generatePalindromeSequences(n - 2);

        for (final char c : "ABC".toCharArray())
        {
            subsequences.forEach(sub -> sequences.add(c + sub + c));
        }

        return sequences;
    }

    /**
     * morePalindromeSequences is a BONUS question that generates
     * palindromes of length n, with c amount of characters. i.e.
     * A, B, C, ... Cth character.
     * @param n length palindrome
     * @param nc number of characters in palindrome
     */
    public ArrayList<String> morePalindromeSequences(final int n, final int nc)
    {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        if (nc > characters.length())
        {
            return null;
        }

        final ArrayList<String> sequences;
        sequences = new ArrayList<>();

        if (n < 1)
        {
            /* we can assume n in greater than 1, but we'll
               check just in case. */
            return sequences;
        }
        else if (n <= 2)
        {
            for (int i = 0; i < nc; i++)
            {
                sequences.add((characters.charAt(i) + "").repeat(n));
            }
            return sequences;
        }

        final ArrayList<String> subsequences;
        subsequences = generatePalindromeSequences(n - 2);

        for (final char c : characters.substring(0, nc).toCharArray())
        {
            subsequences.forEach(sub -> sequences.add(c + sub + c));
        }

        return sequences;
    }

    /**
     * main program entry for testing
     * @param args unused
     */
    public static void main(final String[] args)
    {
        final Lab2 test;

        test = new Lab2();

        System.out.println("Generating ALL sequences of a given length:");
        for (int i = 1; i <= 20; i++)
        {
            final ArrayList<String> sequences;
            sequences = test.generatePalindromeSequences(i);
            System.out.printf("Length %d produces %d sequences\n", i, sequences.size());
            //System.out.println(sequences);
        }
    }
}
