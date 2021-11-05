package com.example.agenda;

import static com.example.agenda.ConstantesActivities.CHAVE_ALUNO;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.agenda.asyncTask.BuscaTodosTelefonesAlunosTask;
import com.example.agenda.asyncTask.EditaAlunoTask;
import com.example.agenda.asyncTask.SalvaAlunoTask;
import com.example.database.AgendaDatabase;
import com.example.database.dao.RoomAlunoDAO;
import com.example.database.dao.RoomTelefoneDAO;
import com.example.model.Aluno;
import com.example.model.Telefone;
import com.example.model.TipoTelefone;

import java.util.List;

public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoCelular;
    private EditText campoEmail;
    private RoomAlunoDAO alunoDAO;
    private RoomTelefoneDAO telefoneDAO;
    private Aluno aluno;
    List<Telefone> listaTelefones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        AgendaDatabase database = AgendaDatabase.getInstance(this);
        alunoDAO = database.getRoomAlunoDAO();
        telefoneDAO = database.getRoomTelefoneDAO();
        inicializacaoDosCampos();
        carregaAluno();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.activity_formulario_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_menu_opcoes_salvar){
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregaAluno() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_ALUNO)) {
            setTitle(TITULO_APPBAR_EDITA_ALUNO);
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoEmail.setText(aluno.getEmail());
        preencheCamposTelefone();
    }

    private void preencheCamposTelefone() {
        new BuscaTodosTelefonesAlunosTask(telefoneDAO, aluno, telefones -> {
            this.listaTelefones = telefones;
            for (Telefone telefone:
                    listaTelefones
            ) {
                if(telefone.getTipo() == TipoTelefone.FIXO){
                    campoTelefone.setText(telefone.getNumero());
                }
                else{
                    campoCelular.setText(telefone.getNumero());
                }
            }
        }).execute();
    }

    private void finalizaFormulario() {
        preencheAluno();
        Telefone telefoneFixo = criarTelefone(campoTelefone, TipoTelefone.FIXO);
        Telefone telefoneCelular = criarTelefone(campoCelular, TipoTelefone.CELULAR);

        if (aluno.temIdValido()) {
            editarAluno(telefoneFixo, telefoneCelular);
        } else {
            salvarAluno(telefoneFixo, telefoneCelular);
        }
        finish();
    }

    @NonNull
    private Telefone criarTelefone(EditText campoTelefone, TipoTelefone fixo) {
        String numeroFixo = campoTelefone.getText().toString();
        return new Telefone(numeroFixo, fixo);
    }

    private void salvarAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        new SalvaAlunoTask(alunoDAO, telefoneDAO, aluno,
                telefoneFixo, telefoneCelular, () -> finish()).execute();

    }

    private void editarAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        new EditaAlunoTask(alunoDAO, telefoneDAO, aluno, telefoneFixo,
                telefoneCelular, listaTelefones, () -> finish()).execute();

    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_campoNome);
        campoTelefone = findViewById(R.id.activity_formulario_campoTelefone);
        campoCelular = findViewById(R.id.activity_formulario_campoCelular);
        campoEmail = findViewById(R.id.activity_formulario_campoEmail);
    }

    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();

        aluno.setNome(nome);
        aluno.setEmail(email);
    }
}
