package ca.bcit.comp3760.lab06;

public class Lab6
{
    public long SW_Recursive(final int m, final int n)
    {
        if (m < 0 || n < 0)
        {
            throw new IllegalArgumentException("m and n must be non-negative");
        }

        if (m == 0 || n == 0)
        {
            return 1;
        }

        return this.SW_Recursive(m - 1, n) + this.SW_Recursive(m, n - 1);
    }

    public void RunRecursive(final int lower, final int upper)
    {
        for (int i = lower; i <= upper; ++i)
        {
            final long start;
            final long end;
            final long result;

            start = System.currentTimeMillis();
            result = this.SW_Recursive(i, i);
            end = System.currentTimeMillis();

            System.out.printf("SW_Recursive(%d,%d) = %d, time is %d ms\n",
                    i, i, result, end - start);
        }
    }

    public long SW_DynamicProg(final int m, final int n)
    {
        final long[][] table;

        table = new long[m + 1][n + 1];

        for (int row = 0; row <= m; ++row)
        {
            table[row][0] = 1;
        }

        for (int col = 0; col <= n; ++col)
        {
            table[0][col] = 1;
        }

        for (int row = 1; row <= m; ++row)
        {
            for (int col = 1; col <= n; ++col)
            {
                table[row][col] = table[row - 1][col] + table[row][col - 1];
            }
        }

        return table[m][n];
    }

    public void RunDynamicProg(final int lower, final int upper)
    {
        for (int i = lower; i <= upper; ++i)
        {
            final long start;
            final long end;
            final long result;

            start = System.currentTimeMillis();
            result = this.SW_DynamicProg(i, i);
            end = System.currentTimeMillis();

            System.out.printf("SW_DynamicProg(%d,%d) = %d, time is %d ms\n",
                    i, i, result, end - start);
        }
    }
}