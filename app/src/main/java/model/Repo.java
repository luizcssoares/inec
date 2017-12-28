package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * Criado por Luiz Carlos em 27/12/2017.
 * Objetivo :
 *           Classe MODELO para os DADOS do REPOSITORIO
 *           do um USUARIO do GITHUB.
 */

public class Repo {

        @SerializedName("id")
        int id;

        @SerializedName("name")
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
}
