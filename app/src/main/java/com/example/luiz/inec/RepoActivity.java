package com.example.luiz.inec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import model.Repo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Criado por Luiz Carlos em 27/12/2017.
 * Objetivo :
 *            Criar ACTIVITY que exibira os REPOSITORIOS do GITHUB
 *            de um determinado USUARIO.
 */
public class RepoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        // Valor do USUARIO do GITHUB selecionao na ACTIVITY anterior
        String valor = getIntent().getStringExtra("vlrRepo");
        final String txtSearch = getIntent().getStringExtra("vlrSearch");

        final List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        // Cria classe de servico para o GITHUB
        GitHubService service = ServiceGenerator.createService(GitHubService.class);

        // Chamada retorna lista de RESPOSITORIOS do GITHUB um determinadi USUARIO
        Call<List<Repo>> apiCall = service.listRepos(valor);

        apiCall.enqueue(new Callback<List<Repo>>() {
            @Override  // Em caso de sucesso
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {

                List<Repo> listRepo = response.body();

                // Monta dados para serem usados no LISTVIEW
                for(Repo r : listRepo){
                    Map<String, String> map = new HashMap<>(2);
                    map.put("id", String.valueOf(r.getId()));
                    map.put("name", r.getName());
                    data.add(map);
                }

                SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), data, android.R.layout.simple_list_item_2,
                        new String[]{"id","name"}, new int[]{android.R.id.text1, android.R.id.text2});

                // Localiza LISTVIEW e exibe dados
                ListView lista = (ListView) findViewById(R.id.Listview);
                lista.setAdapter(adapter);
            }

            @Override  // Em caso de falha
            public void onFailure(Call<List<Repo>> call, Throwable t) {
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("vlrSearch", txtSearch);
                startActivity(intent);
            }
        });
    }
}
