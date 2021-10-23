package com.example.ceep.ui.activity;

import static com.example.ceep.ui.activity.NotaActivitiesConstantes.CHAVE_NOTA;
import static com.example.ceep.ui.activity.NotaActivitiesConstantes.CHAVE_POSICAO;
import static com.example.ceep.ui.activity.NotaActivitiesConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static com.example.ceep.ui.activity.NotaActivitiesConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static com.example.ceep.ui.activity.NotaActivitiesConstantes.POSICAO_INVALIDA;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.ceep.R;
import com.example.ceep.dao.NotaDAO;
import com.example.ceep.model.Nota;
import com.example.ceep.ui.adapter.ListaNotasAdapter;
import com.example.ceep.ui.adapter.listener.OnItemClickListener;
import com.example.ceep.ui.recyclerview.helper.callback.NotaItemtouchHelperCallback;

import java.util.List;

public class ListaNotasActivity extends AppCompatActivity {

    public static final String TITULO_TELA = "Notas";
    private ListaNotasAdapter adapter;
    private List<Nota> todasNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        setTitle(TITULO_TELA);
        todasNotas = obterNotas();
        configurarRecyclerView(todasNotas);
        configurarBotaoInserirFormulario();
    }

    private void configurarBotaoInserirFormulario() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        ListaNotasActivity.this,
                        FormularioNotaActivity.class
                );
                startActivityForResult(intent, CODIGO_REQUISICAO_INSERE_NOTA);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        criarNota(requestCode, resultCode, data);
        alterarNota(requestCode, resultCode, data);
    }

    private void alterarNota(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CODIGO_REQUISICAO_ALTERA_NOTA){
            if(resultCode == Activity.RESULT_OK){
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
                new NotaDAO().alterar(posicaoRecebida, notaRecebida);
                adapter.alterarBanco(posicaoRecebida, notaRecebida);
            }
        }
    }

    private void criarNota(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CODIGO_REQUISICAO_INSERE_NOTA
                && data.hasExtra(CHAVE_NOTA)){
            if(resultCode == Activity.RESULT_OK){
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                new NotaDAO().inserir(notaRecebida);
                adapter.adicionarBanco(notaRecebida);
            }
        }
    }

    private List<Nota> obterNotas() {
        NotaDAO dao = new NotaDAO();
        for(int i=0; i < 10; i++){
            dao.inserir(new Nota("Nota " + (i+1),
                    "Descrição da nota " + i+1));
        }
        return dao.todos();
    }

    private void configurarRecyclerView(List<Nota> notas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recycleView);
        configurarAdapter(notas, listaNotas);
        configuraItemTouchHelper(listaNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemtouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configurarAdapter(List<Nota> notas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, notas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(Nota nota, int posicao) {
                Intent abrirFormulario = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
                abrirFormulario.putExtra(CHAVE_NOTA, nota);
                abrirFormulario.putExtra(CHAVE_POSICAO, posicao);
                startActivityForResult(abrirFormulario, CODIGO_REQUISICAO_ALTERA_NOTA);
            }
        });
    }

//    public void confirmarRemocaoAluno(@NonNull MenuItem item) {
//        new AlertDialog
//                .Builder(context)
//                .setTitle("Removendo aluno")
//                .setMessage("Tem certeza que deseja remover o aluno?")
//                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        AdapterView.AdapterContextMenuInfo menuInfo =
//                                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//                        Aluno alunoEscolhido = adapter.getItem(menuInfo.position); //obtém o aluno clicado no adapter
//                        remover(alunoEscolhido);
//                    }
//                })
//                .setNegativeButton("Não", null)
//                .show();
//    }

    //CRIA O MENU DE CONTEXTO
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_notas_menu, menu);
    }

    //ABRE O MENU DE CONTEXTO PARA O ITEM SELECIONADO
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int idItemMenuContexto = item.getItemId();
        if (idItemMenuContexto == R.id.activity_itemMenu_remover) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int posicao = (int) info.id;
            adapter.removerBanco(posicao);
        }
        return super.onContextItemSelected(item);
    }
}