package com.example.agenda;

import static com.example.agenda.ConstantesActivities.CHAVE_ALUNO;
import static com.example.agenda.R.menu.activity_formulario_menu_opcoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.DAO.AlunoDAO;
import com.example.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_NOVO_ALUNO = "Novo Aluno";
    public static final String TITULO_APPBAR_EDITAR_ALUNO = "Editar Aluno";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private Aluno aluno;
    final AlunoDAO dao = new AlunoDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        inicializarCampos();
        receberDadosAluno();
        definirTitulo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(activity_formulario_menu_opcoes, menu);
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

    private void definirTitulo() {
        if (aluno != null) {
            setTitle(TITULO_APPBAR_EDITAR_ALUNO);
        } else {
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
        }
    }

    private void receberDadosAluno() {
        Intent dados = getIntent();
        aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
        if (aluno != null) {
            campoNome.setText(aluno.getNome());
            campoTelefone.setText(aluno.getTelefone());
            campoEmail.setText(aluno.getEmail());
        }
    }

    private void finalizarFormulario() {
        try {
            preencherAluno();
            Toast.makeText(FormularioAlunoActivity.this, "Aluno salvo!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(FormularioAlunoActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        } finally {
            finish();
        }
    }

//    private void configurarBotaoSalvar() {
//        Button botaoSalvar = findViewById(R.id.activity_formulario_botaoSalvar);
//        botaoSalvar.setOnClickListener((view) -> {
//            try{
//                preencherAluno();
//                Toast.makeText(FormularioAlunoActivity.this, "Aluno salvo!", Toast.LENGTH_SHORT).show();
//            }
//            catch (Exception ex){
//                Toast.makeText(FormularioAlunoActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//            finally {
//                finish();
//            }
//        });
//    }

    private void inicializarCampos() {
        campoNome = findViewById(R.id.activity_formulario_campoNome);
        campoTelefone = findViewById(R.id.activity_formulario_campoTelefone);
        campoEmail = findViewById(R.id.activity_formulario_campoEmail);
    }

    private void preencherAluno() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        if (aluno != null) {
            aluno.setNome(nome);
            aluno.setTelefone(telefone);
            aluno.setEmail(email);
            dao.editar(aluno);
        } else {
            Aluno alunoCriado = new Aluno(nome, telefone, email);
            dao.salvar(alunoCriado);
        }
    }
}