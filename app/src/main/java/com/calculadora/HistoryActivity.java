package com.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private LinearLayout linearLayoutHistorico;
    private Button btVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        linearLayoutHistorico = findViewById(R.id.linearLayoutHistorico);
        btVoltar = findViewById(R.id.btVoltar);

        // Pega o histórico da intent
        ArrayList<String> historico = getIntent().getStringArrayListExtra("historico");

        if (historico == null || historico.isEmpty()) {
            // Exibir mensagem caso não tenha nada no histórico
            TextView tvVazio = new TextView(this);
            tvVazio.setText("Histórico vazio");
            linearLayoutHistorico.addView(tvVazio);
        } else {
            // Adiciona cada item do histórico ao LinearLayout
            for (String item : historico) {
                TextView tv = new TextView(this);
                tv.setText(item);
                tv.setTextSize(18);
                linearLayoutHistorico.addView(tv);
            }
        }

        // Botão voltar para fechar essa Activity e voltar para MainActivity
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();  // Fecha a activity atual e volta para a anterior
            }
        });
    }
}
