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
    private static List<Integer> slotLevels;
    private static final Random rand = new Random();

    public static void main(String[] args) {
        resetSlotLevels();

        List<Integer> nums = new ArrayList<>();
        for(int i = 0; i < numItems; i++) {
            nums.add(howManyTo300());
            resetSlotLevels();
        }
        System.out.println("Items Mean Value   : " + mean(nums));
        System.out.println("Items Median Value : " + median(nums));

        nums = new ArrayList<>();
        for(int i = 0; i < numItems; i++) {
            nums.add(howManyBoxesTo300());
            resetSlotLevels();
        }
        System.out.println("Boxes Mean Value   : " + mean(nums));
        System.out.println("Boxes Median Value : " + median(nums));
    }

    /**
     * Resets the values of slotLevels
     * to startLevel.
     */
    private static void resetSlotLevels() {
        slotLevels = new ArrayList<>();
        for(int i = 0; i < 5; i++) slotLevels.add(startLevel);
    }

    /**
     * Determines the number of items needed
     * to reach 300 using randomized changes.
     *
     * @modifies slotLevels
     * @param start is not null, the initial power level.
     * @return the number of items needed to reach
     *          power level 300 across all 5 slots.
     */
    private static int howManyTo300() {
        int dP = (int)(rand.nextFloat() * 21.0) - 10;
        int slot = (int)(rand.nextFloat() * 5.0);
        if(dP > 0) {
            slotLevels.set(slot, slotLevels.get(slot) + dP);
            if(slotLevels.get(slot) > 300)
                slotLevels.set(slot, 300);
        }
        double average = mean(slotLevels);
        if(average >= 300.0)
            return 1;
        else
            return 1 + howManyTo300();
    }

    /**
     * Determines the number of boxes needed
     * to reach 300 using randomized changes.
     * Each box contains 3 items, which operate on the
     * same initial power level across all slots.
     * These items are assigned random item slots. (1-5)
     * The maximum powered item in each slot gotten
     * from the box overwrites the previous
     * maximum power in that slot (assuming this
     * item is more powerful than the previous
     * maximum power level in that slot).
     *
     * @modifies slotLevels
     * @param start is not null, the initial power level.
     * @return the number of boxes needed to reach
     *          power level 300.
     */
    private static int howManyBoxesTo300() {
        Map<Integer, Integer> changeMap = new HashMap<>();
        for(int i = 0; i < 3; i++) {
            int slot = (int)(rand.nextFloat() * 5.0);
            int dP = (int)(rand.nextFloat() * 21.0) - 10;
            if(changeMap.containsKey(slot)) {
                int d2 = changeMap.get(slot);
                changeMap.put(slot, dP > d2 ? dP : d2);
            } else {
                changeMap.put(slot, dP);
            }
        }
        for(Integer slot : changeMap.keySet()) {
            int dP = changeMap.get(slot);
            if(dP > 0) {
                slotLevels.set(slot, slotLevels.get(slot) + dP);
                if(slotLevels.get(slot) > 300)
                    slotLevels.set(slot, 300);
            }
        }
        double average = mean(slotLevels);
        if(average >= 300.0)
            return 1;
        else
            return 1 + howManyBoxesTo300();
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
