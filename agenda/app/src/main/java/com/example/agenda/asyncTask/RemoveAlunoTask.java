package com.example.agenda.asyncTask;

import android.os.AsyncTask;

import com.example.agenda.ListaAlunosAdapter;
import com.example.database.dao.RoomAlunoDAO;
import com.example.model.Aluno;

import java.util.List;

public class RemoveAlunoTask extends AsyncTask<Void, Void, Void> {

    private final ListaAlunosAdapter adapter;
    private final RoomAlunoDAO dao;
    private final Aluno aluno;

    public RemoveAlunoTask(ListaAlunosAdapter adapter, RoomAlunoDAO dao, Aluno aluno) {
        this.adapter = adapter;
        this.dao = dao;
        this.aluno = aluno;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.remove(aluno);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.remove(aluno);
    }
}
