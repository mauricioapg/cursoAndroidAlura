package com.example.aluraviagens.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.aluraviagens.R;
import com.example.aluraviagens.model.Pacote;
import com.example.aluraviagens.util.ValorUtil;

import java.math.BigDecimal;

public class PagamentoActivity extends AppCompatActivity {

    public static final String TITULO_TELA = "Pagamento";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        setTitle(TITULO_TELA);

        Pacote pacoteSaoPaulo = new Pacote("São Paulo", "sao_paulo_sp",
                2, new BigDecimal("243.99"));

        exibirValor(pacoteSaoPaulo);
    }

    private void exibirValor(Pacote pacoteSaoPaulo) {
        TextView valor = findViewById(R.id.pagamento_valor);
        String moedaBrasileira = ValorUtil.devolverValor(pacoteSaoPaulo.getValor());
        valor.setText(moedaBrasileira);
    }
}