package com.example.aluraviagens.util;

public class PeriodoUtil {

    public static final String PERIODO_PLURAL = " dias";
    public static final String PERIODO_SINGULAR = " dia";

    public static String devolverPeriodo(int qtdeDias){
        if(qtdeDias > 1){
            return qtdeDias + PERIODO_PLURAL;
        }
        else {
            return qtdeDias + PERIODO_SINGULAR;
        }
    }
}
