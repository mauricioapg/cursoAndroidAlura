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
import android.widget.ListView;

import com.example.model.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Alunos";
    private final ListaAlunosView listaAlunosView = new ListaAlunosView(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(TITULO_APPBAR);
        configurarBotaoNovoAluno();
        configurarListaAlunos();
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
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    //ABRE O MENU DE CONTEXTO PARA O ITEM SELECIONADO
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int idItemMenuContexto = item.getItemId();
        if (idItemMenuContexto == R.id.activity_itemMenu_remover) {
            listaAlunosView.confirmarRemocaoAluno(item);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaAlunosView.atualizarLista();
    }


}