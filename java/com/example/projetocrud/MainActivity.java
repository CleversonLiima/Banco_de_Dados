package com.example.projetocrud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private BancoHelper bancoHelper;
    private TextInputEditText editNome, editIdade;
    private TextView textResultado;
    private Button btnGravar, btnConsultar, btnAtualizar, btnDeletar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bancoHelper = new BancoHelper(this);

        editNome = findViewById(R.id.textNome);
        editIdade = findViewById(R.id.textIdade);
        textResultado = findViewById(R.id.textResultado);

        btnGravar = findViewById(R.id.btnGravar);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnDeletar = findViewById(R.id.btnDeletar);

        // 1. CADASTRAR PESSOA
        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editNome.getText().toString().trim();
                String idadeText = editIdade.getText().toString().trim();

                if (!nome.isEmpty() && !idadeText.isEmpty()) {
                    int idade = Integer.parseInt(idadeText);
                    if (idade <= 0) {
                        textResultado.setText("Informe uma idade maior que zero.");
                        return;
                    }
                    boolean sucesso = bancoHelper.inserirUsuario(nome, idade);

                    if (sucesso) {
                        textResultado.setText("Usuário '" + nome + "' gravado com sucesso!");
                        limparCampos();
                    } else {
                        textResultado.setText("Erro: Nome já cadastrado ou falha no banco.");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Preencha o Nome e a Idade!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 2. CONSULTAR PESSOA PELO NOME
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editNome.getText().toString().trim();

                if (!nome.isEmpty()) {
                    Integer idade = bancoHelper.buscarUsuario(nome);
                    if (idade != null) {
                        editIdade.setText(String.valueOf(idade));
                        textResultado.setText("Encontrado: " + nome + ", " + idade + " anos.");
                    } else {
                        textResultado.setText("Usuário '" + nome + "' não encontrado.");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Digite o Nome para consultar!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 3. ALTERAR A IDADE DA PESSOA
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editNome.getText().toString().trim();
                String idadeText = editIdade.getText().toString().trim();

                if (!nome.isEmpty() && !idadeText.isEmpty()) {
                    int novaIdade = Integer.parseInt(idadeText);
                    if (novaIdade <= 0) {
                        textResultado.setText("Informe uma idade maior que zero.");
                        return;
                    }
                    boolean sucesso = bancoHelper.atualizarUsuario(nome, novaIdade);

                    if (sucesso) {
                        textResultado.setText("Usuário '" + nome + "' atualizado para " + novaIdade + " anos!");
                        limparCampos();
                    } else {
                        textResultado.setText("Usuário '" + nome + "' não foi encontrado para atualizar.");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Preencha o Nome e a nova Idade!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 4. EXCLUIR PESSOA
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editNome.getText().toString().trim();

                if (!nome.isEmpty()) {
                    boolean sucesso = bancoHelper.deletarUsuario(nome);

                    if (sucesso) {
                        textResultado.setText("Usuário '" + nome + "' deletado com sucesso!");
                        limparCampos();
                    } else {
                        textResultado.setText("Usuário '" + nome + "' não foi encontrado para remoção.");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Digite o Nome para deletar!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void limparCampos() {
        if (editNome.getText() != null) editNome.getText().clear();
        if (editIdade.getText() != null) editIdade.getText().clear();
    }
}
