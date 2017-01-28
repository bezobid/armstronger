package Algorithms.armstronger;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by bezobid on 28.01.2017.
 */
public class Armstronger {
    public static void main(String[] args) {
        System.out.println("Armstrong numbers from 1 to 2147483647 (Integer.MAX_VALUE) are:");
        System.out.println("===============================================================");

        long timeStart = System.currentTimeMillis();
        int[] result = getNumbers(Integer.MAX_VALUE);
        long timeEnd = System.currentTimeMillis();
        long memoryStart = Runtime.getRuntime().totalMemory();
        long memoryEnd = Runtime.getRuntime().freeMemory();
        for (int i : result) {
            System.out.println(i);
        }

        System.out.println("===============================================================");
        System.out.println("Time comsumption:\t" + (timeEnd - timeStart) + " ms");
        System.out.println("Memory comsumption:\t" + ((memoryStart - memoryEnd) / 1024 / 1024) + " mb");
    }

    private static int[] getNumbers(int N) {

        int[][] matrix = getMatrix(10, 11);

        ArrayList<Integer> numbers = new ArrayList<>();

        ArrayList<Integer> list = buildList(N);

        for (Integer num : list)
        {
            int sum1 = getSum(num, matrix);
            if (!numbers.contains(sum1))
            {
                int sum2 = getSum(sum1, matrix);

                if (sum1 == sum2 && sum1 < N)
                {
                    numbers.add(sum1);
                }
            }
        }
        numbers.remove(0);
        return listToArray(numbers);
    }

    private static int getSum(int x, int[][] matrix)
    {
        if (x < 0) return 0;

        byte M = getLength(x);
        int sum = 0;

        for (int i = 0; i < M; i++) {
            byte digit = (byte)(x % 10);
            sum += matrix[digit][M];
            x /= 10;
        }
        return sum;
    }

    private static int[][] getMatrix(int a, int b)
    {
        int matrix[][] = new int[a][b];
        for (int x = 0; x < a; x++)
        {
            for(int y = 0; y < b; y++)
            {
                matrix[x][y] = (int)Math.pow(x, y);
            }
        }

        return matrix;
    }

    private static byte getLength(int n)
    {
        if (n < 100000){
            // 5 or less
            if (n < 100){
                // 1 or 2
                if (n < 10)
                    return 1;
                else
                    return 2;
            }else{
                // 3 or 4 or 5
                if (n < 1000)
                    return 3;
                else{
                    // 4 or 5
                    if (n < 10000)
                        return 4;
                    else
                        return 5;
                }
            }
        } else {
            // 6 or more
            if (n < 10000000) {
                // 6 or 7
                if (n < 1000000)
                    return 6;
                else
                    return 7;
            } else {
                // 8 to 10
                if (n < 100000000)
                    return 8;
                else {
                    // 9 or 10
                    if (n < 1000000000)
                        return 9;
                    else
                        return 10;
                }
            }
        }
    }

    private static int[] listToArray(ArrayList<Integer> x)
    {
        Collections.sort(x);
        int[] result = new int[x.size()];
        for (int f = 0; f < result.length; f++) {
            result[f] = x.get(f);
        }
        return result;
    }

    private static ArrayList<Integer> buildList(int n)
    {
        String N = String.valueOf(n);
        ArrayList<String>[] listOfLists = new ArrayList[String.valueOf(n).length()];

        for (int i = 0; i < listOfLists.length; i++) {
            listOfLists[i] = new ArrayList<>();
        }


        for (int a = 0; a < 10; a++)
        {
            listOfLists[0].add(String.valueOf(a));
        }

        for (int level = 1; level < listOfLists.length; level++)
        {
            ArrayList<String> prevLevel = listOfLists[level-1];
            ArrayList<String> currentLevel = listOfLists[level];

            for (String number : prevLevel) {

                for (int j = 0; j < 10; j++)
                {
                    int last = Integer.parseInt(String.valueOf(number.charAt(number.length()-1)));
                    if (j >= last)
                    {
                        String num = number+j;
                        if ((level == listOfLists.length - 1) && num.compareTo(N) >= 0)
                        {
                            ArrayList<Integer> allNumbers = new ArrayList<>();
                            for (ArrayList<String> list : listOfLists)
                            {
                                for (String string : list)
                                {
                                    int x = Integer.parseInt(string);
                                    int xLength = getLength(x);

                                    if (string.length() > xLength)
                                    {
                                        for (int i = 0; i < string.length() - xLength ; i++) {
                                            x *= 10;
                                        }
                                    }

                                    allNumbers.add(x);
                                }
                            }
                            return allNumbers;
                        }
                        else
                        {
                            currentLevel.add(number + j);
                        }
                    }
                }
            }
        }
        return null;
    }
}
