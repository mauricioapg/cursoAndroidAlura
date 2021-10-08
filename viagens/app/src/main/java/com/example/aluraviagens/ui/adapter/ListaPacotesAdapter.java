package com.example.aluraviagens.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.aluraviagens.R;
import com.example.aluraviagens.model.Pacote;
import com.example.aluraviagens.util.PeriodoUtil;
import com.example.aluraviagens.util.ResourceUtil;
import com.example.aluraviagens.util.ValorUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ListaPacotesAdapter extends BaseAdapter {

    private final List<Pacote> pacotes;
    private Context context;

    public ListaPacotesAdapter(List<Pacote> pacotes,Context context){
        this.pacotes = pacotes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return pacotes.size();
    }

    @Override
    public Pacote getItem(int posicao) {
        return pacotes.get(posicao);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int posicao, View view, ViewGroup parent) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_pacote, parent, false);

        Pacote pacote = pacotes.get(posicao);

        exibirLocal(viewCriada, pacote);
        exibirPeriodo(viewCriada, pacote);
        exibirValor(viewCriada, pacote);
        exibirImagem(viewCriada, pacote);

        return viewCriada;
    }

    private void exibirImagem(View viewCriada, Pacote pacote) {
        ImageView imagem = viewCriada.findViewById(R.id.item_pacote_imagem);
        Drawable drawableImagemPacote = ResourceUtil
                .devolverDrawable(context, pacote.getImagem());
        imagem.setImageDrawable(drawableImagemPacote);
    }

    private void exibirLocal(View viewCriada, Pacote pacote) {
        TextView local = viewCriada.findViewById(R.id.item_pacote_local);
        local.setText(pacote.getLocal());
    }

    private void exibirPeriodo(View viewCriada, Pacote pacote) {
        TextView periodo = viewCriada.findViewById(R.id.item_pacote_periodo);
        periodo.setText(PeriodoUtil
                .devolverPeriodo(pacote.getPeriodo())
        );
    }

    private void exibirValor(View viewCriada, Pacote pacote) {
        TextView valor = viewCriada.findViewById(R.id.item_pacote_valor);
        valor.setText(ValorUtil
                .devolverValor(pacote.getValor())
        );
    }
}
