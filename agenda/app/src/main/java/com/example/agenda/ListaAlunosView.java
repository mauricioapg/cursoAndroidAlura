package com.example.agenda;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.room.Room;

import com.example.DAO.AlunoDAO;
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

    public void removerTodos(){
        try {
            dao.removeTodos(dao.todos());
            adapter.removeTodos();
            Toast.makeText(context, "alunos removidos!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
