package com.calculadora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private StringBuilder expressao = new StringBuilder();
    private ArrayList<String> historico = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        // Configurar listeners para todos os botões
        int[] idsBotoes = {
                R.id.bt0, R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.bt6, R.id.bt7, R.id.bt8, R.id.bt9,
                R.id.btVirgula,
                R.id.btSoma, R.id.btSubtracao, R.id.btMultiplicacao, R.id.btDivisao,
                R.id.btIgualdade,
                R.id.btLimpar,
                R.id.btHistorico
        };

        for (int id : idsBotoes) {
            Button btn = findViewById(id);
            btn.setOnClickListener(this);
        }

        atualizarTexto();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.bt0 || id == R.id.bt1 || id == R.id.bt2 || id == R.id.bt3 ||
                id == R.id.bt4 || id == R.id.bt5 || id == R.id.bt6 || id == R.id.bt7 ||
                id == R.id.bt8 || id == R.id.bt9) {
            adicionarNumero(((Button) v).getText().toString());

        } else if (id == R.id.btVirgula) {
            adicionarVirgula();

        } else if (id == R.id.btSoma || id == R.id.btSubtracao || id == R.id.btMultiplicacao || id == R.id.btDivisao) {
            adicionarOperador(((Button) v).getText().toString());

        } else if (id == R.id.btIgualdade) {
            calcularResultado();

        } else if (id == R.id.btLimpar) {
            limpar();

        } else if (id == R.id.btHistorico) {
            abrirHistorico();
        }
    }

    private void adicionarNumero(String num) {
        expressao.append(num);
        atualizarTexto();
    }

    private void adicionarVirgula() {
        // Só adiciona vírgula se não tiver uma já no número atual
        // Para simplificar, permitir só uma vírgula no final
        if (expressao.length() == 0 || expressao.charAt(expressao.length() - 1) == '+' ||
                expressao.charAt(expressao.length() - 1) == '-' ||
                expressao.charAt(expressao.length() - 1) == 'x' ||
                expressao.charAt(expressao.length() - 1) == '/') {
            // Se último for operador, adiciona "0."
            expressao.append("0.");
        } else if (!expressao.toString().endsWith(".")) {
            // Evita adicionar mais de uma vírgula seguida
            expressao.append(".");
        }
        atualizarTexto();
    }

    private void adicionarOperador(String operador) {
        if (expressao.length() == 0) {
            // Não permite operador no início, exceto '-'
            if (operador.equals("-")) {
                expressao.append(operador);
            }
        } else {
            char ultimo = expressao.charAt(expressao.length() - 1);
            if (ultimo == '+' || ultimo == '-' || ultimo == 'x' || ultimo == '/') {
                // Troca o operador
                expressao.setCharAt(expressao.length() - 1, operador.charAt(0));
            } else {
                expressao.append(operador);
            }
        }
        atualizarTexto();
    }

    private void calcularResultado() {
        String expr = expressao.toString();

        // Trocar 'x' por '*', e ',' por '.'
        expr = expr.replace('x', '*');
        expr = expr.replace(',', '.');
        expr = expr.replace('.', '.'); // só para garantir

        try {
            double resultado = eval(expr);
            String resultadoStr = String.valueOf(resultado);

            // Remover .0 se for inteiro
            if (resultadoStr.endsWith(".0")) {
                resultadoStr = resultadoStr.substring(0, resultadoStr.length() - 2);
            }

            // Salvar no histórico
            historico.add(expr + " = " + resultadoStr);

            expressao.setLength(0);
            expressao.append(resultadoStr);
            atualizarTexto();

        } catch (Exception e) {
            textView.setText("Erro na expressão");
            expressao.setLength(0);
        }
    }

    // Limpar o visor e expressão
    private void limpar() {
        expressao.setLength(0);
        atualizarTexto();
    }

    // Atualiza o texto do TextView
    private void atualizarTexto() {
        textView.setText(expressao.toString());
    }

    private void abrirHistorico() {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putStringArrayListExtra("historico", historico);
        startActivity(intent);
    }

    // Método simples para avaliar expressões básicas com +, -, *, /
    // Não suporta parênteses nem prioridade, calcula da esquerda para direita (básico)
    private double eval(String expr) throws Exception {
        // Substitui espaços
        expr = expr.replace(" ", "");

        // Parse simples da esquerda para direita
        ArrayList<Double> numeros = new ArrayList<>();
        ArrayList<Character> operadores = new ArrayList<>();

        StringBuilder numeroAtual = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if ((c >= '0' && c <= '9') || c == '.') {
                numeroAtual.append(c);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                if (numeroAtual.length() == 0 && c == '-') {
                    // número negativo
                    numeroAtual.append(c);
                } else {
                    if (numeroAtual.length() == 0) throw new Exception("Expressão inválida");
                    numeros.add(Double.parseDouble(numeroAtual.toString()));
                    operadores.add(c);
                    numeroAtual.setLength(0);
                }
            } else {
                throw new Exception("Caractere inválido");
            }
        }
        if (numeroAtual.length() > 0) {
            numeros.add(Double.parseDouble(numeroAtual.toString()));
        }

        // Agora calcula da esquerda para direita sem prioridade (por simplicidade)
        double resultado = numeros.get(0);
        for (int i = 0; i < operadores.size(); i++) {
            char op = operadores.get(i);
            double prox = numeros.get(i + 1);
            switch (op) {
                case '+': resultado += prox; break;
                case '-': resultado -= prox; break;
                case '*': resultado *= prox; break;
                case '/':
                    if (prox == 0) throw new Exception("Divisão por zero");
                    resultado /= prox;
                    break;
                default:
                    throw new Exception("Operador inválido");
            }
        }

        return resultado;
    }
}
