package com.example.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.model.Telefone;

import java.util.List;

@Dao
public interface RoomTelefoneDAO {
    @Query("SELECT * FROM Telefone WHERE alunoId = :alunoId LIMIT 1")
    Telefone buscaPrimeiroTelefone(int alunoId);

    @Insert
    void salvar(Telefone... telefones);

    @Query("SELECT * FROM Telefone WHERE alunoId = :alunoId")
    List<Telefone> buscaTodosTelefones(int alunoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void editar(Telefone... telefones);
}
