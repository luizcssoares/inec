package http;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Criado por Luiz Carlos em 26/12/2017.
 * Objetivo :
 *            Criar nossa classe de serviço.
 *            Ela tera 3 finalidades principais:
 *            - Conter nossa URL base para acessar o endpoint
 *            - Ser o interceptador para mostrar as requisições e respostas do servidor
 *            - Instanciar o objeto retrofit para abrir a chamada com webservice.
 */

public class ServiceGenerator {

    // URL base do endpoint do GITHUB
    public static final String API_BASE_URL = "https://api.github.com/";

    public static <S> S createService(Class<S> serviceClass) {

        // Instancia do interceptador das requisições
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS);

        httpClient.addInterceptor(loggingInterceptor);


        // Instância do retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(httpClient.build())
                .build();

        return retrofit.create(serviceClass);
    }
}
