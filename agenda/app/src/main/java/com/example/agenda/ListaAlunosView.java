package com.example.agenda;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.agenda.asyncTask.BuscaAlunoTask;
import com.example.agenda.asyncTask.RemoveAlunoTask;
import com.example.agenda.asyncTask.RemoverTodosAlunosTask;
import com.example.database.AgendaDatabase;
import com.example.database.dao.RoomAlunoDAO;
import com.example.model.Aluno;

public class ListaAlunosView {

    private final ListaAlunosAdapter adapter;
    private final Context context;
    private RoomAlunoDAO dao;

    public ListaAlunosView(Context context) {
        this.context = context;
        this.adapter = new ListaAlunosAdapter(this.context);
        AgendaDatabase database = AgendaDatabase.getInstance(this.context);
        dao = database.getRoomAlunoDAO();
    }

    public void confirmarRemocaoAluno(final MenuItem item) {
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

    public void confirmarRemocaoTodosAlunos(final MenuItem item) {
        new AlertDialog
                .Builder(context)
                .setTitle("Removendo todos")
                .setMessage("Tem certeza que deseja remover todos os alunos?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AdapterView.AdapterContextMenuInfo menuInfo =
                                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                        removerTodos();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void configurarAdapter(ListView listViewAlunos) {
        listViewAlunos.setAdapter(adapter);
    }

    public void atualizarLista() {
        new BuscaAlunoTask(dao, adapter).execute();
    }

    private void remover(Aluno alunoSelecionado) {
        try {
            new RemoveAlunoTask(adapter, dao, alunoSelecionado).execute();
            Toast.makeText(this.context, "Aluno removido!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this.context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void removerTodos(){
        try {
            new RemoverTodosAlunosTask(dao, adapter).execute();
            Toast.makeText(context, "alunos removidos!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
