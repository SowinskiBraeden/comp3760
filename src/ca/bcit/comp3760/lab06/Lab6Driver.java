package ca.bcit.comp3760.lab06;

public class Lab6Driver
{
    public static void main(final String[] args)
    {
        final Lab6 d;

        d = new Lab6();

        System.out.println("Recursive:");
        d.RunRecursive(0, 5);

        System.out.println();
        System.out.println("Dynamic Programming:");
        d.RunDynamicProg(0, 10);

        System.out.println();
        System.out.println("My donuts");
        d.RunRecursive(37, 37);
        d.RunDynamicProg(37, 37);
        d.RunRecursive(3737, 3737);
        d.RunDynamicProg(3737, 3737);
        d.RunRecursive(10000037, 10000037);
        d.RunDynamicProg(10000037, 10000037);
    }
}