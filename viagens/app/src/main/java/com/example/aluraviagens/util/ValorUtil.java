package com.example.aluraviagens.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class ValorUtil {

    public static final String PORTUGUES = "pt";
    public static final String BRASIL = "br";

    public static String devolverValor(BigDecimal valor){
        NumberFormat formatoBrasileiro = DecimalFormat.getCurrencyInstance(
                new Locale(PORTUGUES, BRASIL));
        String moedaBrasileira = formatoBrasileiro.format(valor);
        return moedaBrasileira;
    }
}
