package com.example.luiz.inec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Criado por Luiz Carlos em 27/12/2017.
 * Objetivo :
 *            Criar ACTIVITY que tera um EditText onde sera infomado
 *            o nome de USUARIO a ser localizado no GitHub.
 */
public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Nome de USUARIO a ser localizado
        final EditText edtSearch = (EditText) findViewById(R.id.edtSearch);

        Button botao = (Button)findViewById(R.id.btnSearch);
        botao.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                boolean validacao = true;

                // Valor inforrmado no EditText do USUARIO
                String txtSearch = edtSearch.getText().toString();

                // Validacao do valor informado
                if ((txtSearch == null) || (txtSearch.equals(""))) {
                    validacao = false;
                    edtSearch.setError("campo_obrigatorio");
                }

                if (validacao)
                {
                    // Chama ACTIVITY (MainActivity) que exibira os USUARIOS localizados
                    Toast toast = Toast.makeText(getApplicationContext(), "validando texto", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("vlrSearch", txtSearch);
                    startActivity(intent);
                }
            }
        });

    }
}
