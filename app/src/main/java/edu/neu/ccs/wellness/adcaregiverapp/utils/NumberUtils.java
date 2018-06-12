package edu.neu.ccs.wellness.adcaregiverapp.utils;

import java.util.Random;

/**
 * Created by amritanshtripathi on 6/11/18.
 */

public class NumberUtils {

    public static int getRandomNumber(int start, int end) {
        Random r = new Random();
        return r.nextInt(end - start) + start;
    }
}
