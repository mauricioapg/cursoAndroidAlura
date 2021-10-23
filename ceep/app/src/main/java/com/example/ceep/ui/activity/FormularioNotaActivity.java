package com.example.ceep.ui.activity;

import static com.example.ceep.ui.activity.NotaActivitiesConstantes.CHAVE_NOTA;
import static com.example.ceep.ui.activity.NotaActivitiesConstantes.CHAVE_POSICAO;
import static com.example.ceep.ui.activity.NotaActivitiesConstantes.POSICAO_INVALIDA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ceep.R;
import com.example.ceep.model.Nota;

public class FormularioNotaActivity extends AppCompatActivity {

    public static final String TITULO_TELA = "Insere Nota";
    private Nota nota;
    private int posicao;
    private EditText campoTitulo;
    private EditText campoDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
        setTitle(TITULO_TELA);
        InicializarCampos();
        receberDadosNota();
    }

    private void InicializarCampos(){
        campoTitulo = findViewById(R.id.formulario_nota_titulo);
        campoDescricao = findViewById(R.id.formulario_nota_descricao);
    }

    private void receberDadosNota() {
        Intent notaRecebida = getIntent();
        if(notaRecebida.hasExtra(CHAVE_NOTA) && notaRecebida.hasExtra(CHAVE_POSICAO)){
            nota = (Nota) notaRecebida.getSerializableExtra(CHAVE_NOTA);
            posicao = notaRecebida.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            campoTitulo.setText(nota.getTitulo());
            campoDescricao.setText(nota.getDescricao());
        }
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
        String titulo = campoTitulo.getText().toString();
        String descricao = campoDescricao.getText().toString();

        if(nota != null){
            nota.setTitulo(titulo);
            nota.setDescricao(descricao);
            enviarDadosAlteracao(nota, posicao);
        }
        else {
            Nota notaCriada = new Nota(titulo, descricao);
            enviarDadosInsercao(notaCriada);
        }
    }

    private void enviarDadosInsercao(Nota notaCriada) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, notaCriada);
        setResult(Activity.RESULT_OK, resultadoInsercao);
        finish();
    }

    private void enviarDadosAlteracao(Nota nota, int posicao) {
        Intent resultadoAlteracao = new Intent();
        resultadoAlteracao.putExtra(CHAVE_NOTA, nota);
        resultadoAlteracao.putExtra(CHAVE_POSICAO, posicao);
        setResult(Activity.RESULT_OK, resultadoAlteracao);
        finish();
    }
}