package com.example.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.model.Aluno;

import java.util.List;

@Dao
public interface RoomAlunoDAO {

    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert
    Long salvar(Aluno aluno); //utilizando LONG retorna o valor do Id do aluno

    @Query("SELECT *  FROM aluno")
    List<Aluno> todos();

    @Delete
    void remove(Aluno aluno);

    @Delete
    void removeTodos(List<Aluno> lista);

    @Update
    void editar(Aluno aluno);
}
