package com.example.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class Aluno implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String nome;
    private String email;
    //private Calendar dataCadastro = Calendar.getInstance();

//    public Calendar getDataCadastro(){
//        return dataCadastro;
//    }
//
//    public void setDataCadastro(Calendar dataCadastro){
//        this.dataCadastro = dataCadastro;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public boolean temIdValido() {
        return id > 0;
    }

//    public String dataFormatada(){
//        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
//        return formatador.format(getDataCadastro().getTime());
//    }
}
