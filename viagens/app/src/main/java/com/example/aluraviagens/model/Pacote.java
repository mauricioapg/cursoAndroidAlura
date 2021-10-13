package com.example.aluraviagens.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.math.BigDecimal;

public class Pacote implements Parcelable {
    private final String local;
    private final String imagem;
    private final int periodo;
    private final BigDecimal valor;

    public Pacote(String local, String imagem, int periodo, BigDecimal valor){
        this.local = local;
        this.imagem = imagem;
        this.periodo = periodo;
        this.valor = valor;
    }

    private Pacote(Parcel in){
        local = in.readString();
        imagem = in.readString();
        periodo = in.readInt();
        valor = new BigDecimal(in.readString());
    }

    public static final Parcelable.Creator<Pacote>
            CREATOR = new Parcelable.Creator<Pacote>() {

        public Pacote createFromParcel(Parcel in) {
            return new Pacote(in);
        }

        public Pacote[] newArray(int size) {
            return new Pacote[size];
        }
    };

    public String getLocal() {
        return local;
    }

    public String getImagem() {
        return imagem;
    }

    public int getPeriodo() {
        return periodo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(local);
        dest.writeString(imagem);
        dest.writeInt(periodo);
        dest.writeString(valor.toPlainString());
    }
}
