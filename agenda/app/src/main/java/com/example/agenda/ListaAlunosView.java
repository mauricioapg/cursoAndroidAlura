package com.example.agenda;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.DAO.AlunoDAO;
import com.example.model.Aluno;

public class ListaAlunosView {

    private final AlunoDAO dao;
    private final ListaAlunosAdapter adapter;
    private final Context context;

    public ListaAlunosView(Context context) {
        this.context = context;
        this.adapter = new ListaAlunosAdapter(this.context);
        this.dao = new AlunoDAO();
    }

    public void confirmarRemocaoAluno(@NonNull MenuItem item) {
        new AlertDialog
                .Builder(context)
                .setTitle("Removendo aluno")
                .setMessage("Tem certeza que deseja remover o aluno?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AdapterView.AdapterContextMenuInfo menuInfo =
                                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                        Aluno alunoEscolhido = adapter.getItem(menuInfo.position); //obtém o aluno clicado no adapter
                        remover(alunoEscolhido);
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void configurarAdapter(ListView listViewAlunos) {
        listViewAlunos.setAdapter(adapter);
    }

    public void atualizarLista() {
        adapter.atualizar(dao.todos());
    }

    private void remover(Aluno alunoSelecionado) {
        try {
            dao.remove(alunoSelecionado);
            adapter.remove(alunoSelecionado);
            Toast.makeText(this.context, "Aluno removido!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this.context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
