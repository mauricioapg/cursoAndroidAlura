package com.example.ceep.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ceep.R;
import com.example.ceep.dao.NotaDAO;
import com.example.ceep.model.Nota;

public class FormularioNotaActivity extends AppCompatActivity {

    public static final String TITULO_TELA = "Insere Nota";
    private Nota nota;
    final NotaDAO dao = new NotaDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
        setTitle(TITULO_TELA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_menu_opcoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idItem = item.getItemId();
        if (idItem == R.id.activity_menu_opcoes_salvar) {
            finalizarFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void finalizarFormulario() {
        try {
            preencherNota();
            Toast.makeText(FormularioNotaActivity.this, "Nota salva!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(FormularioNotaActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        } finally {
            finish();
        }
    }

    private void preencherNota() {
        EditText titulo = findViewById(R.id.formulario_nota_titulo);
        EditText descricao = findViewById(R.id.formulario_nota_descricao);
        Nota notaCriada = new Nota(titulo.getText().toString(), descricao.getText().toString());
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra("nota", notaCriada);
        setResult(1, resultadoInsercao);
        finish();
    }
}