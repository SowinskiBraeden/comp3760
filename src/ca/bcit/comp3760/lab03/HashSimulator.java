package ca.bcit.comp3760.lab03;


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

    public int H3(final String key, final int size)
    {
        double sum;
        sum = 0;

        for (int i = 0; i < key.length(); ++i)
        {
            final int charValue;
            charValue = key.toUpperCase().charAt(i);
            sum += charValue * Math.pow(NUM_CHAR, i);
        }

        return (int) (sum % size);
    }

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
