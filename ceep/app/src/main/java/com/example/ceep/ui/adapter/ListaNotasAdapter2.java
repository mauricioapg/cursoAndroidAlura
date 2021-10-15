package com.example.ceep.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ceep.R;
import com.example.ceep.model.Nota;

import java.util.List;

public class ListaNotasAdapter2 extends BaseAdapter {

    private final List<Nota> notas;
    private Context context;

    public ListaNotasAdapter2(List<Nota> listaNotas, Context context) {
        this.notas = listaNotas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return notas.size();
    }

    @Override
    public Object getItem(int posicao) {
        return notas.get(posicao);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int posicao, View view, ViewGroup parent) {
        View viewCriada = LayoutInflater.from(context).inflate(
                R.layout.item_nota,
                parent,
                false);

        Nota nota = notas.get(posicao);

        TextView titulo = viewCriada.findViewById(R.id.item_nota_titulo);
        titulo.setText(nota.getTitulo());

        TextView descricao = viewCriada.findViewById(R.id.item_nota_descricao);
        descricao.setText(nota.getDescricao());

        return viewCriada;
    }
}
