package com.example.aluraviagens.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.aluraviagens.R;

public class TelaEntradaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_entrada);
        SharedPreferences preferences = getSharedPreferences(
                "user_preferences", MODE_PRIVATE
        );
        verificarStatusAbertura(preferences);
    }

    private void adicionarStatusAbertura(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("app_aberto", true);
        editor.commit();
    }

    private void verificarStatusAbertura(SharedPreferences preferences) {
        if(preferences.contains("app_aberto")){
            navegarTelaListaPacotes();
        }
        else {
            adicionarStatusAbertura(preferences);
            exibirSplashEntrada();
        }
    }

    private void exibirSplashEntrada() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navegarTelaListaPacotes();
            }
        },2000
        );
    }

    private void navegarTelaListaPacotes() {
        Intent intent = new Intent(TelaEntradaActivity.this,
                ListaPacotesActivity.class);
        startActivity(intent);
        finish();
    }
}