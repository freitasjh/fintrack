package br.com.systec.fintrack.commons.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
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

    public static LocalDate convertToDate(String monthAndYear, int day) {
        String[] parts = monthAndYear.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);

        YearMonth yearMonth = YearMonth.of(year, month);

        // Se o dia for maior que o último dia do mês, corrige para o último dia
        int maxDay = yearMonth.lengthOfMonth();
        int safeDay = Math.min(day, maxDay);

        // Retorna o primeiro dia do mês
        return LocalDate.of(year, month, safeDay);
    }
}
