package com.rogih.alunoonlineapi.utils;

import java.time.LocalDate;
import java.time.YearMonth;

public class DueDate {

    public static LocalDate calculateDueDate(Integer dueDay, int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);

        // Verificar se o dia de vencimento é válido para o mês
        int dayOfMonth = Math.min(dueDay, yearMonth.lengthOfMonth());

        return LocalDate.of(year, month, dayOfMonth);
    }
}
