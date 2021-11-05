package com.example.agenda.asyncTask;

import android.os.AsyncTask;

import com.example.database.dao.RoomAlunoDAO;
import com.example.database.dao.RoomTelefoneDAO;
import com.example.model.Aluno;
import com.example.model.Telefone;

public class SalvaAlunoTask extends AsyncTask<Void, Void, Void> {

    private final RoomAlunoDAO alunoDAO;
    private final RoomTelefoneDAO telefoneDAO;
    private final Aluno aluno;
    private final Telefone telefoneFixo;
    private final Telefone telefoneCelular;
    private final QuandoAlunoSalvoListener listener;

    public SalvaAlunoTask(RoomAlunoDAO alunoDAO,
                          RoomTelefoneDAO telefoneDAO,
                          Aluno aluno,
                          Telefone telefoneFixo,
                          Telefone telefoneCelular,
                          QuandoAlunoSalvoListener listener) {
        this.alunoDAO = alunoDAO;
        this.telefoneDAO = telefoneDAO;
        this.aluno = aluno;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int idAluno = alunoDAO.salvar(aluno).intValue();
        vincularAlunoTelefone(idAluno, telefoneFixo, telefoneCelular);
        telefoneDAO.salvar(telefoneFixo, telefoneCelular);
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        listener.quandoSalvo();
    }

    private void vincularAlunoTelefone(int idAluno, Telefone...telefones) {
        for (Telefone telefone: telefones) {
            telefone.setAlunoId(idAluno);
        }
    }

    public interface QuandoAlunoSalvoListener{
        void quandoSalvo();
    }
}
