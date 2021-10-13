package com.example.aluraviagens.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aluraviagens.R;
import com.example.aluraviagens.dao.PacoteDAO;
import com.example.aluraviagens.model.Pacote;
import com.example.aluraviagens.ui.adapter.ListaPacotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListaPacotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacotes);
        configurarLista();
    }

    private void configurarLista() {
        ListView listViewPacotes = findViewById(R.id.lista_pacotes_listView);
        final List<Pacote> pacotes = new PacoteDAO().lista();
        listViewPacotes.setAdapter(new ListaPacotesAdapter(pacotes, this));
        listViewPacotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Pacote pacoteCriado = pacotes.get(posicao);
                navegarTelaResumoPacote(pacoteCriado);
            }

            private void navegarTelaResumoPacote(Pacote pacoteCriado) {
                Intent telaResumoPacote = new Intent(ListaPacotesActivity.this,
                        ResumoPacoteActivity.class);
                telaResumoPacote.putExtra(PacoteActivityConstantes.CHAVE_PACOTE, pacoteCriado);
                startActivity(telaResumoPacote);
            }
        });
    }
}