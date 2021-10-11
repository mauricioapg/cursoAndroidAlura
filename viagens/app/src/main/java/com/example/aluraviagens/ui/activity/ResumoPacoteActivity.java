package com.example.aluraviagens.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aluraviagens.R;
import com.example.aluraviagens.model.Pacote;
import com.example.aluraviagens.util.DataUtil;
import com.example.aluraviagens.util.PeriodoUtil;
import com.example.aluraviagens.util.ResourceUtil;
import com.example.aluraviagens.util.ValorUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ResumoPacoteActivity extends AppCompatActivity {

    public static final String TITULO_TELA = "Resumo do Pacote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pacote);

        setTitle(TITULO_TELA);

        Pacote pacoteSaoPaulo = new Pacote("São Paulo", "sao_paulo_sp",
                2, new BigDecimal("243.99"));

        exibirImagem(pacoteSaoPaulo);
        exibirDestino(pacoteSaoPaulo);
        exibirPeriodo(pacoteSaoPaulo);
        exibirValor(pacoteSaoPaulo);
        exibirDatas(pacoteSaoPaulo);
    }

    private void exibirDatas(Pacote pacote) {
        TextView datas = findViewById(R.id.resumo_pacote_datas);
        String datasViagem = DataUtil.retornarDatasEmTexto(pacote.getPeriodo());
        datas.setText(datasViagem);
    }

    private void exibirValor(Pacote pacote) {
        TextView valor = findViewById(R.id.resumo_pacote_valor);
        valor.setText(ValorUtil.devolverValor(pacote.getValor()));
    }

    private void exibirPeriodo(Pacote pacote) {
        TextView periodo = findViewById(R.id.resumo_pacote_periodo);
        periodo.setText(PeriodoUtil.devolverPeriodo(pacote.getPeriodo()));
    }

    private void exibirDestino(Pacote pacote) {
        TextView destino = findViewById(R.id.resumo_pacote_destino);
        destino.setText(pacote.getLocal());
    }

    private void exibirImagem(Pacote pacote) {
        ImageView imagem = findViewById(R.id.resumo_pacote_imagem);
        Drawable imageDrawable = ResourceUtil
                .devolverDrawable(this, pacote.getImagem());
        imagem.setImageDrawable(imageDrawable);
    }
}