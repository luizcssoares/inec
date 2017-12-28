package com.example.luiz.inec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import http.GitHubService;
import http.ServiceGenerator;
import model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Criado por Luiz Carlos em 27/12/2017.
 * Objetivo :
 *            Criar ACTIVITY que exibira os USUARIOS do GITHUB
 *            em uma LISTVIEW.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Valor informddo no EditText para conultar USUARIO
        final String valor = getIntent().getStringExtra("vlrSearch");

        final List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        // Cria classe de servico para o GITHUB
        GitHubService service = ServiceGenerator.createService(GitHubService.class);

        // Chamada retorna USUARIO do GITHUB pelo metodo getUsuario(valor)
        Call<Usuario> apiCall = service.getUsuario(valor);

        apiCall.enqueue(new Callback<Usuario>() {
            @Override   // em caso de sucesso
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                Usuario u = response.body();

                // Monta dados para serem usados no LISTVIEW
                Map<String, String> map = new HashMap<>(2);
                map.put("login", u.getLogin());
                map.put("companhia", u.getCompany());
                data.add(map);

                final SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), data, android.R.layout.simple_list_item_2,
                        new String[]{"login","companhia"}, new int[]{android.R.id.text1, android.R.id.text2});

                // Localiza LISTVIEW e exibe dados
                ListView lista = (ListView) findViewById(R.id.Listview);
                lista.setAdapter(adapter);

                // Seleciona um ITEM do LISTVIEW
                AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View container, int position, long id) {

                        String linha = parent.getItemAtPosition(position).toString();

                        // Extrai valor da chave LOGIN do ITEM selcionado da LISTVIEW.
                        int pos = linha.indexOf("login");
                        String retorno = linha.substring(pos+6,linha.length()-1);

                        Intent intent = new Intent(getApplicationContext(), RepoActivity.class);
                        intent.putExtra("vlrRepo", retorno);  // ITEM selec.(USUARIO) para exibir seus RESPOSITORIOS
                        intent.putExtra("vlrSearch", valor);  // Valor EditText (nome USUARIO) da ACTVITY anterior.
                        startActivity(intent);

                    }
                };
                lista.setOnItemClickListener(itemClickListener);

            }

            @Override   // em caso de Falha
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();

            }
        });

        // Botao RETORNA Activity anterior
        Button botao = (Button)findViewById(R.id.btnVoltar);
        botao.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            Toast toast = Toast.makeText(getApplicationContext(), "Retornando", Toast.LENGTH_SHORT);
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
            }
        });
    }
}
