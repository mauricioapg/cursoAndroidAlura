package com.example.database.converter;

import androidx.room.TypeConverter;

import com.example.model.TipoTelefone;

public class ConversorTipoTelefone {

    @TypeConverter
    public String toString(TipoTelefone tipo){
        return tipo.name();
    }

    @TypeConverter
    public TipoTelefone toTipoTelefone(String valor){
        if(valor != null){
            return TipoTelefone.valueOf(valor);
        }
        return TipoTelefone.FIXO;
    }
}
