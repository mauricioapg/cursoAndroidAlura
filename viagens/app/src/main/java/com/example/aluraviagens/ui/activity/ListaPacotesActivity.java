package com.example.aluraviagens.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        Intent telaPagamentoPacote = new Intent(this, PagamentoActivity.class);
        startActivity(telaPagamentoPacote);
    }

    private void configurarLista() {
        ListView listViewPacotes = findViewById(R.id.lista_pacotes_listView);
        final List<Pacote> pacotes = new PacoteDAO().lista();
        listViewPacotes.setAdapter(new ListaPacotesAdapter(pacotes, this));
    }
}