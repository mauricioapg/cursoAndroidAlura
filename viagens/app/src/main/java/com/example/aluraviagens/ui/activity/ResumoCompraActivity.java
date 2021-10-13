package com.example.aluraviagens.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aluraviagens.R;
import com.example.aluraviagens.model.Pacote;
import com.example.aluraviagens.util.DataUtil;
import com.example.aluraviagens.util.ResourceUtil;
import com.example.aluraviagens.util.ValorUtil;

import java.math.BigDecimal;

public class ResumoCompraActivity extends AppCompatActivity {

    public static final String TITULO_TELA = "Resumo da Compra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_compra);

        setTitle(TITULO_TELA);

        Intent intent = getIntent();
        if(intent.hasExtra(PacoteActivityConstantes.CHAVE_PACOTE)){
            Pacote pacote = (Pacote) intent.getParcelableExtra(
                    PacoteActivityConstantes.CHAVE_PACOTE
            );
            exibirImagem(pacote);
            exibirLocal(pacote);
            exibirPeriodo(pacote);
            exibirValor(pacote);
        }
    }

    private void exibirValor(Pacote pacote) {
        TextView valor = findViewById(R.id.resumo_compra_valor);
        valor.setText(ValorUtil.devolverValor(pacote.getValor()));
    }

    private void exibirPeriodo(Pacote pacote) {
        TextView datas = findViewById(R.id.resumo_compra_datas);
        String datasViagem = DataUtil.retornarDatasEmTexto(pacote.getPeriodo());
        datas.setText(datasViagem);
    }

    private void exibirLocal(Pacote pacote) {
        TextView local = findViewById(R.id.resumo_compra_local);
        local.setText(pacote.getLocal());
    }

    private void exibirImagem(Pacote pacote) {
        ImageView imagem = findViewById(R.id.resumo_compra_imagem);
        Drawable imageDrawable = ResourceUtil
                .devolverDrawable(this, pacote.getImagem());
        imagem.setImageDrawable(imageDrawable);
    }
}