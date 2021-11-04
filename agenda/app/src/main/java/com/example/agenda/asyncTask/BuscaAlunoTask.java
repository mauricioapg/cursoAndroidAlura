package com.example.agenda.asyncTask;

import android.os.AsyncTask;

import com.example.agenda.ListaAlunosAdapter;
import com.example.database.dao.RoomAlunoDAO;
import com.example.model.Aluno;

import java.util.List;

public class BuscaAlunoTask extends AsyncTask<Void, Void, List<Aluno>> {
    private final RoomAlunoDAO dao;
    private final ListaAlunosAdapter adapter;

    public BuscaAlunoTask(RoomAlunoDAO dao, ListaAlunosAdapter adapter) {
        this.dao = dao;
        this.adapter = adapter;
    }

    @Override
    protected List<Aluno> doInBackground(Void[] objects) {
        return dao.todos();
    }

    @Override
    protected void onPostExecute(List<Aluno> listaAlunos) {
        super.onPostExecute(listaAlunos);
        adapter.atualizar((List<Aluno>) listaAlunos);
    }
}
