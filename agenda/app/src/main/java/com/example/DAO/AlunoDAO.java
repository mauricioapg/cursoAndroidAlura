package com.example.DAO;

import androidx.annotation.Nullable;

import com.example.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private static List<Aluno> listaAlunos = new ArrayList<>();
    private static int contadorIds = 1;

    public void salvar(Aluno aluno){
        aluno.setId(contadorIds);
        listaAlunos.add(aluno);
        contadorIds++;
    }

    public void editar(Aluno aluno){
        Aluno alunoSeleecionado = obterAlunoPeloId(aluno);
        if(alunoSeleecionado != null){
            int posicao = listaAlunos.indexOf(alunoSeleecionado);
            listaAlunos.set(posicao, aluno);
        }
    }

    public void remove(Aluno aluno) {
        Aluno alunoSelecionado = obterAlunoPeloId(aluno);
        if(aluno != null){
            listaAlunos.remove(alunoSelecionado);
        }
    }

    @Nullable
    private Aluno obterAlunoPeloId(Aluno aluno) {
        Aluno alunoEncontrado = null;
        for (Aluno a: listaAlunos) {
            if(a.getId() == aluno.getId()){
                alunoEncontrado = a;
            }
        }
        return alunoEncontrado;
    }

    public List<Aluno> todos(){
        return new ArrayList<>(listaAlunos);
    }
}
