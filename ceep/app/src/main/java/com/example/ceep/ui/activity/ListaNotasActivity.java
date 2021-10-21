package com.example.ceep.ui.activity;

import static com.example.ceep.ui.activity.NotaActivitiesConstantes.CHAVE_NOTA;
import static com.example.ceep.ui.activity.NotaActivitiesConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static com.example.ceep.ui.activity.NotaActivitiesConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ceep.R;
import com.example.ceep.dao.NotaDAO;
import com.example.ceep.model.Nota;
import com.example.ceep.ui.adapter.ListaNotasAdapter;
import com.example.ceep.ui.adapter.listener.OnItemClickListener;

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
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CODIGO_REQUISICAO_INSERE_NOTA
                && resultCode == CODIGO_RESULTADO_NOTA_CRIADA
                && data.hasExtra(CHAVE_NOTA)){
            receberNota(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void receberNota(@NonNull Intent data) {
        Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
        new NotaDAO().inserir(notaRecebida);
        adapter.adicionar(notaRecebida);
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
    }

    private void configurarAdapter(List<Nota> notas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, notas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(Nota nota) {
                Toast.makeText(ListaNotasActivity.this,
                        nota.getTitulo(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}