package com.example.ceep.dao;

import com.example.ceep.model.Nota;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NotaDAO {

    private final static ArrayList<Nota> listaNotas = new ArrayList<>();

    public List<Nota> todos(){ return (List<Nota>) listaNotas.clone();}

    public void inserir(Nota nota){
        listaNotas.add(nota);
    }

    public void alterar(int posicao, Nota nota){listaNotas.set(posicao, nota);}

    public void remover(int posicao){listaNotas.remove(posicao);}

    public void trocar(int posicaoInicio, int posicaoFim){
        Collections.swap(listaNotas, posicaoInicio, posicaoFim);
    }

    public void removerTodos(){ listaNotas.clear(); }
}
