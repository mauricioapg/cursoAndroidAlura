package com.example.aluraviagens.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        Intent intent = getIntent();
        if(intent.hasExtra(PacoteActivityConstantes.CHAVE_PACOTE)){
            final Pacote pacote = (Pacote) intent.getParcelableExtra(
                    PacoteActivityConstantes.CHAVE_PACOTE
            );
            exibirValor(pacote);

            Button botaoFinalizarCompra = findViewById(R.id.pagamento_botao_finalizar_compra);
            botaoFinalizarCompra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent telaResumoCompra = new Intent(PagamentoActivity.this,
                            ResumoCompraActivity.class);
                    telaResumoCompra.putExtra(PacoteActivityConstantes.CHAVE_PACOTE, pacote);
                    startActivity(telaResumoCompra);
                }
            });
        }
    }

    private void exibirValor(Pacote pacoteSaoPaulo) {
        TextView valor = findViewById(R.id.pagamento_valor);
        String moedaBrasileira = ValorUtil.devolverValor(pacoteSaoPaulo.getValor());
        valor.setText(moedaBrasileira);
    }
}