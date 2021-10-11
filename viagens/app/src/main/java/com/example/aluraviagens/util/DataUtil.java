package com.example.aluraviagens.util;

import androidx.annotation.NonNull;

import com.example.aluraviagens.model.Pacote;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataUtil {

    public static final String DIA_E_MES = "dd/MM";

    @NonNull
    public static String retornarDatasEmTexto(int dias) {
        Calendar dataIda = Calendar.getInstance();
        Calendar dataVolta = Calendar.getInstance();
        dataVolta.add(Calendar.DATE, dias);
        SimpleDateFormat formatoBrasileiro = new SimpleDateFormat(DIA_E_MES);
        String dataIdaFormatada = formatoBrasileiro.format(dataIda.getTime());
        String dataVoltaFormatada = formatoBrasileiro.format(dataVolta.getTime());
        String datasViagem = dataIdaFormatada + " - " +
                dataVoltaFormatada + " de " +
                dataVolta.get(Calendar.YEAR);
        return datasViagem;
    }
}
