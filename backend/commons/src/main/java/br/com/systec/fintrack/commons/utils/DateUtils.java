package br.com.systec.fintrack.commons.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtils {

    public static List<LocalDate> getRemainingDatesOfWeek() {
        // Obter a data atual
        LocalDate currentDate = LocalDate.now();
        DayOfWeek currentDay = currentDate.getDayOfWeek();

        // Lista para armazenar as datas restantes
        List<LocalDate> remainingDates = new ArrayList<>();

        // Iterar do dia atual até sábado
        for (DayOfWeek day = currentDay; day.getValue() <= DayOfWeek.SATURDAY.getValue(); day = day.plus(1)) {
            // Adicionar a data correspondente ao dia da semana
            remainingDates.add(currentDate);
            // Incrementar a data para o próximo dia
            currentDate = currentDate.plusDays(1);
        }

        return remainingDates;
    }

    public static Date converterLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
