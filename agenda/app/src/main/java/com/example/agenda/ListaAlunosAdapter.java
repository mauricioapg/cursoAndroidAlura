package com.example.agenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.database.AgendaDatabase;
import com.example.database.dao.RoomTelefoneDAO;
import com.example.model.Aluno;
import com.example.model.Telefone;

import java.util.ArrayList;
import java.util.List;

public class ListaAlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos = new ArrayList<>();
    private final Context context;
    RoomTelefoneDAO dao;

    public ListaAlunosAdapter(Context context) {
        this.context = context;
        dao = AgendaDatabase.getInstance(context).getRoomTelefoneDAO();
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Aluno getItem(int posicao) {
        return alunos.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return alunos.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        View viewCriada = criarView(viewGroup);
        Aluno alunoDevolvido = alunos.get(posicao);
        vincular(viewCriada, alunoDevolvido);
        return viewCriada;
    }

    private void vincular(View viewCriada, Aluno alunoDevolvido) {
        TextView nome = viewCriada.findViewById(R.id.item_lista_nome);
        nome.setText(alunoDevolvido.getNome());
        TextView telefone = viewCriada.findViewById(R.id.item_lista_telefone);
        Telefone primeiroTelefone = dao.buscaPrimeiroTelefone(alunoDevolvido.getId());
        telefone.setText(primeiroTelefone.getNumero());
//        if(primeiroTelefone != null){
//            telefone.setText(primeiroTelefone.getNumero());
//        }
    }

    private View criarView(ViewGroup viewGroup) {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_menu, viewGroup, false);
    }

    public void remove(Aluno alunoSelecionado) {
        alunos.remove(alunoSelecionado);
        notifyDataSetChanged();
    }

    public void removeTodos() {
        alunos.clear();
        notifyDataSetChanged();
    }

    public void atualizar(List<Aluno> alunos){
        this.alunos.clear();
        this.alunos.addAll(alunos);
        notifyDataSetChanged();
    }
}
