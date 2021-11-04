package com.example.agenda;

import static com.example.agenda.ConstantesActivities.CHAVE_ALUNO;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.model.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaAlunosActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR = "Lista de Alunos";
    private ListaAlunosView listaAlunosView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITULO_APPBAR);
        listaAlunosView = new ListaAlunosView(this);
        configurarBotaoNovoAluno();
        configurarListaAlunos();
    }

    //CRIA O MENU LATERAL DE OPÇÕES
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.activity_lista_alunos_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //ABRE OPÇÃO SELECIONADA NO MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        abrirOpcoesMenu(item);
        return super.onOptionsItemSelected(item);
    }

    private void abrirOpcoesMenu(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_itemMenu_removerTodos){
            listaAlunosView.confirmarRemocaoTodosAlunos(item);
        }
    }

    private void configurarListaAlunos() {
        ListView listViewAlunos = findViewById(R.id.activity_listViewAlunos);
        listaAlunosView.configurarAdapter(listViewAlunos);
        configurarListenerCliquePorItem(listViewAlunos);
        registerForContextMenu(listViewAlunos); //registra o menu do contexto na ListView
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
        getMenuInflater().inflate(R.menu.activity_lista_remover_aluno_selecionado, menu);
    }

    //ABRE O MENU DE CONTEXTO PARA O ITEM SELECIONADO
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        abrirOpcaoRemoverAluno(item);
        return super.onContextItemSelected(item);
    }

    private void abrirOpcaoRemoverAluno(@NonNull MenuItem item) {
        int idItemMenuContexto = item.getItemId();
        if (idItemMenuContexto == R.id.activity_remover_alunoSelecionado) {
            listaAlunosView.confirmarRemocaoAluno(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaAlunosView.atualizarLista();
    }


}