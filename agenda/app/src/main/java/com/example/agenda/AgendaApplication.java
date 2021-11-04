package com.example.agenda;

import android.app.Application;

import androidx.room.Room;

import com.example.DAO.AlunoDAO;
import com.example.database.AgendaDatabase;
import com.example.database.dao.RoomAlunoDAO;
import com.example.model.Aluno;

public class AgendaApplication extends Application {

    //Tudo que for criado aqui na Application, será executado somente uma vez na Activity
    //Utilizar somente para procedimentos que não demorem tanto

    @Override
    public void onCreate() {
        super.onCreate();
        criarAlunosTestes();
    }

    private void criarAlunosTestes() {
        AgendaDatabase database = Room.databaseBuilder(this, AgendaDatabase.class, "agenda.db")
                .allowMainThreadQueries() //permite acessar o banco na thread principal o ideal seria numa thread separada
                .build();
        RoomAlunoDAO dao = database.getRoomAlunoDAO();
        //dao.salvar(new Aluno("Mauricio", "11985181910", "mauricio@unike.tech"));
        //dao.salvar(new Aluno("Lindsay", "11963447535", "lilika_dias@yahoo.com.br"));
    }
}
