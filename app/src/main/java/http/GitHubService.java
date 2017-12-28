package http;

import java.util.List;

import model.Repo;
import model.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Criado por Luiz Carlos em 26/12/2017.
 * Objetivo :
 *           Criar interface de acesso aos metodos
 *           do Servico GitHub :
 *             - getUsuarios(String user)
 *                  retorna um USUARIO
 *
 *             - listRepos(String user)
 *                  retorna uma lista de REPOSITORIOS
 *                  de um USUARIO.
 *
 *             - getUsuarios()
 *                  retorna ums Lista de USUARIOS.
 */
public interface GitHubService {
    // Retorna lista de USUARIOS
    @GET("users")
    Call<List<Usuario>> getUsuarios();

    // Retorna um USUARIO
    @GET("users/{user}")
    Call<Usuario> getUsuario(@Path("user") String user);

    // Retorna uma lista de REPOSITORIO de um usuario
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
}
