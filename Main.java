package verm2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/*
 * Author: (Github uName) thisTyler
 *         (Reddit uName) Nightscythe1847
 * November 15 2018
 */
public class Main {

    private static final int numItems = 100000;
    private static final int startLevel = 0;

    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        for(int i = 0; i < numItems; i++) {
            nums.add(howManyTo300(startLevel));
        }
        System.out.println("Mean Value   : " + mean(nums));
        System.out.println("Median Value : " + median(nums));
    }

    /**
     * Determines the number of items needed
     * to reach 300 using randomized changes.
     *
     * @param start is not null, the initial power level.
     * @return the number of items needed to reach
     *          power level 300.
     */
    private static int howManyTo300(int start) {
        Random rand = new Random();
        int dP = (int)(rand.nextFloat() * 21.0) - 10;
        if(dP <= 0)
            return 1 + howManyTo300(start);
        else if (start + dP >= 300)
            return 1;
        else
            return 1 + howManyTo300(start + dP);
    }

    /**
     * Computes the mean value of a list.
     *
     * @param list is not null, the list to be averaged.
     * @return the average value of the integers in list.
     */
    private static double mean(List<Integer> list) {
        return Arrays.stream(list.toArray(new Integer[0]))
                .reduce(0, (x,y) -> x + y) / (list.size()*1.0);
    }

    /**
     * Computes the median value of a list.
     *
     * @param list is not null, the list to be averaged.
     * @return the most commonly found integer in list.
     */
    private static int median(List<Integer> list) {
        Map<Integer, Integer> countMap = new HashMap<>();
        int largest = 0;
        for(Integer i : list) {
            if(countMap.containsKey(i))
                countMap.put(i, countMap.get(i) + 1);
            else
                countMap.put(i, 1);
            if(countMap.get(i) > largest)
                largest = i;
        }
        return largest;
    }
}
