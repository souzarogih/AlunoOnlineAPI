package com.rogih.alunoonlineapi.utils;

import java.util.Random;

public class EnrollmentGenerate {

    public static String generateEnrollmentNumberNow(){
        Random random = new Random();
        int fourDigitNumber = 1000 + random.nextInt(9000);
        return WorkDate.getCurrentYear() + "" + fourDigitNumber;
    }
}
