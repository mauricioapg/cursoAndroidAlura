package com.example.agenda;

import static com.example.agenda.ConstantesActivities.CHAVE_ALUNO;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.DAO.AlunoDAO;
import com.example.model.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Alunos";
    private final AlunoDAO dao = new AlunoDAO();
    private ArrayAdapter<Aluno> adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(TITULO_APPBAR);
        configurarBotaoNovoAluno();
        configurarListaAlunos();

        dao.salvar(new Aluno("Mauricio", "11985181910", "mauricio@unike.tech"));
        dao.salvar(new Aluno("Lindsay", "11963447535", "lilika_dias@yahoo.com.br"));

    }

    private void configurarListaAlunos() {
        ListView listViewAlunos = findViewById(R.id.activity_listViewAlunos);
        configurarAdapter(listViewAlunos);
        configurarListenerCliquePorItem(listViewAlunos);
        registerForContextMenu(listViewAlunos); //registra o menu do contexto na ListView
    }

    private void remover(Aluno alunoSelecionado) {
        dao.remove(alunoSelecionado);
        adapter.remove(alunoSelecionado);
        try {
            Toast.makeText(ListaAlunosActivity.this, "Aluno removido!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(ListaAlunosActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void configurarListenerCliquePorItem(ListView listViewAlunos) {
        listViewAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Aluno alunoSelecionado = (Aluno) adapterView.getItemAtPosition(posicao);
                abrirFormularioEdicaoAluno(alunoSelecionado);
            }
        });
    }

    private void configurarAdapter(ListView listViewAlunos) {
        adapter = new ArrayAdapter<>
                (this,
                        android.R.layout.simple_list_item_1);
        listViewAlunos.setAdapter(adapter);
    }

    private void abrirFormularioEdicaoAluno(Aluno alunoSelecionado) {
        Intent intent = new Intent(
                ListaAlunosActivity.this,
                FormularioAlunoActivity.class);
        intent.putExtra(CHAVE_ALUNO, alunoSelecionado); //passar atributo pela intent
        startActivity(intent);
    }

    private void configurarBotaoNovoAluno() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_lista_botaoNovoAluno);
        botaoNovoAluno.setOnClickListener((view) -> {
            abrirFormularioNovoAluno();
        });
    }

    private void abrirFormularioNovoAluno() {
        startActivity(new Intent(
                ListaAlunosActivity.this,
                FormularioAlunoActivity.class
        ));
    }

    //CRIA O MENU DE CONTEXTO
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    //ABRE O MENU DE CONTEXTO PARA O ITEM SELECIONADO
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int idItemMenuContexto = item.getItemId();
        if (idItemMenuContexto == R.id.activity_itemMenu_remover) {
            AdapterView.AdapterContextMenuInfo menuInfo =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Aluno alunoEscolhido = adapter.getItem(menuInfo.position); //obtém o aluno clicado no adapter
            remover(alunoEscolhido);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }
}