package br.com.systec.fintrack;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtils {

    public static List<LocalDate> obterDatasRestantesDaSemana() {
        // Obter a data atual
        LocalDate dataAtual = LocalDate.now();
        DayOfWeek diaAtual = dataAtual.getDayOfWeek();

        // Lista para armazenar as datas restantes
        List<LocalDate> datasRestantes = new ArrayList<>();

        // Iterar do dia atual até sábado
        for (DayOfWeek dia = diaAtual; dia.getValue() <= DayOfWeek.SATURDAY.getValue(); dia = dia.plus(1)) {
            // Adicionar a data correspondente ao dia da semana
            datasRestantes.add(dataAtual);
            // Incrementar a data para o próximo dia
            dataAtual = dataAtual.plusDays(1);
        }

        return datasRestantes;
    }



}
