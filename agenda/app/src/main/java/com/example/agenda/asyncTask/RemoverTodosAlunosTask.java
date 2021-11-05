package com.example.agenda.asyncTask;

import android.os.AsyncTask;

import com.example.agenda.ListaAlunosAdapter;
import com.example.database.dao.RoomAlunoDAO;

public class RemoverTodosAlunosTask extends AsyncTask<Void, Void, Void> {

    private final RoomAlunoDAO dao;
    private final ListaAlunosAdapter adapter;

    public RemoverTodosAlunosTask(RoomAlunoDAO dao, ListaAlunosAdapter adapter) {
        this.dao = dao;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.removeTodos(dao.todos());
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        adapter.removeTodos();
    }
}
