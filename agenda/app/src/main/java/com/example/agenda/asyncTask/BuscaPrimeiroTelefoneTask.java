package com.example.agenda.asyncTask;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.database.dao.RoomTelefoneDAO;
import com.example.model.Telefone;

public class BuscaPrimeiroTelefoneTask extends AsyncTask<Void, Void, Telefone> {

    private final RoomTelefoneDAO dao;
    private final int alunoId;
    private final PrimeiroTelefoneEncontradoListener listener;

    public BuscaPrimeiroTelefoneTask(RoomTelefoneDAO dao,
                                     int alunoId,
                                     PrimeiroTelefoneEncontradoListener listener) {
        this.dao = dao;
        this.alunoId = alunoId;
        this.listener = listener;
    }

    @Override
    protected Telefone doInBackground(Void... voids) {
        Telefone primeiroTelefone = dao.buscaPrimeiroTelefone(alunoId);
        return primeiroTelefone;
    }

    @Override
    protected void onPostExecute(Telefone primeiroTelefone) {
        super.onPostExecute(primeiroTelefone);
        listener.quandoEncontrado(primeiroTelefone);
    }

    public interface PrimeiroTelefoneEncontradoListener{
        void quandoEncontrado(Telefone telefoneEncontrado);
    }
}
