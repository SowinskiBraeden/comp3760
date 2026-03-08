package ca.bcit.comp3760.lab03;

/**
 * HashSimulator to test different hash methods
 * and their collisions, probing for hash tables.
 *
 * @author Braeden Sowinski - A01385066
 * @version 1.0.0
 */
public class HashSimulator
{
    private static final int CHAR_OFFSET = 64;
    private static final int NUM_CHAR    = 26;
    private static final int RESULT_SIZE = 6;
    private enum HASH
    {
        HASH1,
        HASH2,
        HASH3
    }

    /**
     * H1 hashes a key for a table of a given size
     * by summing the characters and modding by the size.
     * Where characters A = 1, B = 2, C = 3, etc.
     * @param key to hash
     * @param size of the has table to insert
     * @return hash of key
     */
    public int H1(final String key, final int size)
    {
        int sum;
        sum = 0;

        for (final char c : key.toUpperCase().toCharArray())
        {
            sum += (int) c - CHAR_OFFSET;
        }

        return sum % size;
    }

    /**
     * H2 hashes a key for a table of a given size
     * by summing the character value multiplied by the number
     * of characters in the alphabet (NUM_CHAR) to the power of
     * the index of the given character, finally modding by the size.
     * Where characters A = 1, B = 2, C = 3, etc.
     * @param key to hash
     * @param size of the has table to insert
     * @return hash of key
     */
    public int H2(final String key, final int size)
    {
        double sum;
        sum = 0;

        for (int i = 0; i < key.length(); ++i)
        {
            final int charValue;
            charValue = key.toUpperCase().charAt(i) - CHAR_OFFSET;
            sum += charValue * Math.pow(NUM_CHAR, i);
        }

        return (int) (sum % size);
    }

    /**
     * H3 is the same as H2, where the difference is character
     * values are as seen in ASCII tables for both upper and
     * lower case characters. We then account for upper and
     * lower case in the power of character index by multiplying
     * NUM_CHAR (26) by 2.
     * @param key to hash
     * @param size of the has table to insert
     * @return hash of key
     */
    public int H3(final String key, final int size)
    {
        double sum;
        sum = 0;

        for (int i = 0; i < key.length(); ++i)
        {
            final int charValue;
            charValue = key.charAt(i); // use ASCII value instead
            sum += charValue * Math.pow(NUM_CHAR * 2, i); // lower and uppercase
        }

        return (int) (sum % size);
    }

    /**
     * runHashSimulation takes a list of keys to hash and the size
     * of a hash table to insert the keys into via the hash.
     * Simulates hashing each key using each hashing method H1, H2, & H3
     * then counts collisions and probing for each type of hash into a table
     * to compare.
     * @param keys used to testing hashing methods
     * @param size of testing hash table
     * @return collision + probe results for each hashing method
     */
    public int[] runHashSimulation(final String[] keys, int size)
    {
        final int[] results;
        results = new int[RESULT_SIZE];

        for (final HASH type : HASH.values())
        {
            final String[] table;
            int collisions;
            int probes;

            table      = new String[size];
            collisions = 0;
            probes     = 0;

            for (final String key : keys)
            {
                final int hash_index;

                hash_index = type == HASH.HASH1 ? H1(key, size) :
                             type == HASH.HASH2 ? H2(key, size) :
                                                  H3(key, size);

                if (table[hash_index] == null)
                {
                    table[hash_index] = key;
                }
                else
                {
                    int probeIndex;

                    probeIndex = (hash_index + 1) % size;
                    collisions++;

                    while (table[probeIndex] != null)
                    {
                        probes++;
                        probeIndex = (probeIndex + 1) % size;
                    }

                    probes++;
                    table[probeIndex] = key;
                }
            }

            results[type.ordinal() * 2]     = collisions;
            results[type.ordinal() * 2 + 1] = probes;
        }

        return results;
    }
}
