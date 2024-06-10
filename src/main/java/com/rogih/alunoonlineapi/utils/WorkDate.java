package com.rogih.alunoonlineapi.utils;

import java.time.LocalDate;

public class WorkDate {

    public static int getCurrentYear() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear();
    }
}
