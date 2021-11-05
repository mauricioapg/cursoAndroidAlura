package com.example.agenda.asyncTask;

import android.os.AsyncTask;

import com.example.database.dao.RoomAlunoDAO;
import com.example.database.dao.RoomTelefoneDAO;
import com.example.model.Aluno;
import com.example.model.Telefone;
import com.example.model.TipoTelefone;

import java.util.List;

public class EditaAlunoTask extends AsyncTask<Void, Void, Void> {

    private final RoomAlunoDAO alunoDAO;
    private final RoomTelefoneDAO telefoneDAO;
    private final Aluno aluno;
    private final Telefone telefoneFixo;
    private final Telefone telefoneCelular;
    private final List<Telefone> listaTelefones;
    private final QuandoAlunoSalvoListener listener;

    public EditaAlunoTask(RoomAlunoDAO alunoDAO, RoomTelefoneDAO telefoneDAO, Aluno aluno, Telefone telefoneFixo, Telefone telefoneCelular, List<Telefone> listaTelefones, QuandoAlunoSalvoListener listener) {
        this.alunoDAO = alunoDAO;
        this.telefoneDAO = telefoneDAO;
        this.aluno = aluno;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
        this.listaTelefones = listaTelefones;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        alunoDAO.editar(aluno);
        vincularAlunoTelefone(aluno.getId(), telefoneFixo, telefoneCelular);
        atualizaIdsTelefones(telefoneFixo, telefoneCelular);
        telefoneDAO.editar(telefoneFixo, telefoneCelular);
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        listener.quandoSalvo();
    }

    private void atualizaIdsTelefones(Telefone telefoneFixo, Telefone telefoneCelular) {
        for (Telefone telefone:
                listaTelefones
        ) {
            if(telefone.getTipo() == TipoTelefone.FIXO){
                telefoneFixo.setId(telefone.getId());
            }
            else{
                telefoneCelular.setId(telefone.getId());
            }
        }
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
