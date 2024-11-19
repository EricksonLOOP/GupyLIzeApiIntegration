package com.tom.gupy.Services.GlaiServices;

import com.squareup.okhttp.*;
import com.tom.gupy.Models.CandidateModel.BodyCandidateRegistration;
import com.tom.gupy.Models.SchoolModel.SchoolModel;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.*;

@Service
public class GlaiServicesImpl implements GlaiServices {
    OkHttpClient client = new OkHttpClient();


    @Override
   public JSONObject CriarParametros(BodyCandidateRegistration bodyCandidateRegistration) {
        JSONObject params = new JSONObject();
        params.put("name", bodyCandidateRegistration.getName());
        params.put("responsible_email", bodyCandidateRegistration.getEmail());
        params.put("email", bodyCandidateRegistration.getEmail());
        params.put("username", bodyCandidateRegistration.getEmail());
        params.put("password", bodyCandidateRegistration.getBirthdate());
        params.put("enrollment_number", enrolamentNumberGenerator());
       return params;
    }
    public String enrolamentNumberGenerator(){
        Random random = new Random();
        long randomNumber = random.nextLong() + 100000000000L;
        return String.valueOf(randomNumber);
    }

    @Override
    public String GerarTokenSSO(String email) {
    try {
        JSONObject params = new JSONObject();
        params.put("email", email);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
        Request request = new Request.Builder()
                .url("https://app.lizeedu.com.br/api/v2/sso/generate_accesss_token/")
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Token SEU TOKEN LIZE AQUI")
                .build();
        Response response = client.newCall(request).execute();
        JSONObject responseData = new JSONObject(response.body().string());

        if (response.isSuccessful()){
            return responseData.get("access_token").toString();
        }else{
            throw new RuntimeException("Erro ao gerar Access_Token no Lize.");
        }


    } catch (Exception e) {
        return "Erro interno ao gerar Access_Token. Error: "+ e.getMessage();
    }

    }

    @Override
    public boolean AdicionarUsuarioNaClasse(String testId, String userId, JSONObject params) {
        try {
            // Relaciona testId com a classe
            String idSala = RelacionarTestIdComClasse(testId).get(0);
            List<String> salas = new ArrayList<>();
            salas.add(idSala);
            // Remove parâmetros desnecessários
            params.remove("username");
            params.remove("password");
            params.remove("classes");
            params.remove("is_active");
            params.remove("id");
            // Adiciona novos parâmetros necessários
            params.put("client", idSala);
            params.put("school_classes", salas);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
            Request request = new Request.Builder()
                    .url("https://app.lizeedu.com.br/api/v2/students/" + userId + "/set_classes/")
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Token SEU TOKEN LIZE AQUI")
                    .build();

            // Executa a requisição
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (Exception e) {
            // Exibe erro detalhado
            throw new RuntimeException("Erro ao adicionar usuário à classe: " + e.getMessage(), e);
        }
    }


    private List<String> RelacionarTestIdComClasse(String testId) {
        // Lista de Salas de aulas com seus respectivos testes
        Map<String, String> classes = Map.of(
                "c3e95ab2-e44c-4f48-9b21-3bb569916beb", "1423aacc-1871-4dc7-a15f-acf92f727806"
        );

        String classId = classes.get(testId);

        if (classId != null && !classId.isEmpty()) {
            List<String> classe = new ArrayList<>();
            classe.add(classId);
            return classe;
        } else {
            throw new RuntimeException("Classe de aula não encontrada para o testId: " + testId);
        }
    }


    private SchoolModel RecuperarEscola(String idSala) {
        try{
            Request req = new Request.Builder()
                    .url("https://app.lizeedu.com.br/api/v2/classes/"+idSala+"/")
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Token SEU TOKEN LIZE AQUI")
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
}
