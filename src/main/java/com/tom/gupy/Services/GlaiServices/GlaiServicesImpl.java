package com.tom.gupy.Services.GlaiServices;

import com.squareup.okhttp.*;
import com.tom.gupy.Models.CandidateModel.BodyCandididadteRegistration;
import com.tom.gupy.Models.SchoolModel.SchoolModel;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.*;

@Service
public class GlaiServicesImpl implements GlaiServices {
    OkHttpClient client = new OkHttpClient();


@Autowired
   public JSONObject CriarParametros(BodyCandididadteRegistration bodyCandididadteRegistration) {

        JSONObject params = new JSONObject();
        params.put("name", bodyCandididadteRegistration.getName());
        params.put("responsible_email", bodyCandididadteRegistration.getEmail());
        params.put("email", bodyCandididadteRegistration.getEmail());
        params.put("username", bodyCandididadteRegistration.getEmail());
        params.put("password", bodyCandididadteRegistration.getBirthdate());
       return params;
    }

    @Override
    public String GerarTokenSSO(String email) {
    try {
        JSONObject params = new JSONObject();
        params.put("email", email);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
        Request request = new Request.Builder()
                .url("https://app.lizeedu.com.br//api/v2/sso/generate_accesss_token/")
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authentication", "Token "+System.getenv("LIZE_API_KEY"))
                .build();
        Response response = client.newCall(request).execute();
        JSONObject responseData = new JSONObject(response.body().string());

        if (response.isSuccessful()){
            return responseData.get("access_token").toString();
        }else{
            throw new RuntimeException("Erro ao gerar Access_Token no Lize");
        }


    } catch (Exception e) {
        return "Erro interno ao gerar Access_Token. Error: "+ e.getMessage();
    }

    }

    @Override
    public boolean AdicionarUsuarioNaClasse(String testId, String userId, JSONObject params) {
        try{
            List<String> idSala = RelacionarTestIdComClasse(testId);
            //SchoolModel classe = RecuperarEscola(idSala);

            //Removendo parametros desnecessários
            params.remove("username");
            params.remove("password");
            //Add novos parametros necessários
            params.put("client", idSala);
            params.put("shchool_classes", idSala);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
            Request request = new Request.Builder()
                    .url("https://app.lizeedu.com.br/api/v2/students/"+userId+"/set_classes/")
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authentication", "Token "+System.getenv("LIZE_API_KEY"))
                    .build();

            Response response = client.newCall(request).execute();

            return response.isSuccessful();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SchoolModel RecuperarEscola(String idSala) {
    try{
        Request req = new Request.Builder()
                .url("https://app.lizeedu.com.br/api/v2/classes/"+idSala+"/")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Token "+System.getenv("LIZE_API_KEY"))
                .build();
        Response response = client.newCall(req).execute();
        JSONObject data = new JSONObject(response.body().string());
        SchoolModel classe = new SchoolModel(
                data.get("id").toString(),
                data.getString("name"),
                data.get("coodination").toString(),
                data.get("school_year").toString()
                );

        return classe;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    }

    private List<String> RelacionarTestIdComClasse(String testId) {

    //Lista de Salas de aulas com seus respectivos testes
        Map<String, String> classes = Map.of(
                "exemploIdTest1", "Sala 1",
                "exemploIdTest2", "Sala 2"
        );
        List<String> classe = new ArrayList<>();

        if (!classes.get(testId).isEmpty()){
            classe.add(classes.get(testId));
            return classe;
        }else{
            throw new RuntimeException("Classe de aula não encontrada.");
        }

    }
}
