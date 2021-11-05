package com.example.agenda.asyncTask;

import android.os.AsyncTask;
import android.widget.EditText;

import com.example.database.dao.RoomTelefoneDAO;
import com.example.model.Aluno;
import com.example.model.Telefone;
import com.example.model.TipoTelefone;

import java.util.List;

public class BuscaTodosTelefonesAlunosTask extends AsyncTask<Void, Void, List<Telefone>> {

    private final RoomTelefoneDAO telefoneDAO;
    private final Aluno aluno;
    private final TodosTelefonesEncontradosListener listener;

    public BuscaTodosTelefonesAlunosTask(RoomTelefoneDAO telefoneDAO,
                                         Aluno aluno,
                                         TodosTelefonesEncontradosListener listener) {
        this.telefoneDAO = telefoneDAO;
        this.aluno = aluno;
        this.listener = listener;
    }

    @Override
    protected List<Telefone> doInBackground(Void... voids) {
        return telefoneDAO.buscaTodosTelefones(aluno.getId());
    }

    @Override
    protected void onPostExecute(List<Telefone> telefones) {
        super.onPostExecute(telefones);
        listener.quandoEncontrado(telefones);
    }

    public interface TodosTelefonesEncontradosListener{
        void quandoEncontrado(List<Telefone> listaTelefones);
    }
}
