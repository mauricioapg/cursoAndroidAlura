package com.example.database;

import static com.example.database.AgendaMigrations.TODAS_MIGRATIONS;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.database.converter.ConversorTipoTelefone;
import com.example.database.dao.RoomAlunoDAO;
import com.example.database.dao.RoomTelefoneDAO;
import com.example.model.Aluno;
import com.example.model.Telefone;

@Database(entities = {Aluno.class, Telefone.class}, version = 7, exportSchema = false)
@TypeConverters({ConversorTipoTelefone.class})
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String NOME_BANCO_DE_DADOS = "agenda.db";

    public abstract RoomAlunoDAO getRoomAlunoDAO();
    public abstract RoomTelefoneDAO getRoomTelefoneDAO();

    public static AgendaDatabase getInstance(Context context){
         return Room
                 .databaseBuilder(context, AgendaDatabase.class, NOME_BANCO_DE_DADOS)
                .allowMainThreadQueries() //permite acessar o banco na thread principal o ideal seria numa thread separada.fallbackToDestructiveMigration()
                 .addMigrations(TODAS_MIGRATIONS)
                 .build();
    }
}
