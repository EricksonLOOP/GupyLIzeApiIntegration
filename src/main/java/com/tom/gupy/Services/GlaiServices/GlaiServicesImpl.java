package com.tom.gupy.Services.GlaiServices;

import com.squareup.okhttp.*;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class GlaiServicesImpl implements  GlaiServices{
    @Override
    public ResponseEntity<?> criarUsuario(JSONObject jsondata) {
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject BodyParams = CriarParametros(jsondata);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), BodyParams.toString());
            Request request = new Request.Builder()
                    .url("https://app.lizeedu.com.br/api/v2/students/")
                    .post(requestBody)
                    .addHeader("Authorization", "Token "+System.getenv("LIZE_API_KEY"))
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return ResponseEntity.created(URI.create("https://app.lizeedu.com.br/")).body("Usuário Criado na Lize com sucesso!");
            }else{
                return ResponseEntity.badRequest().body("Erro ao criar usuário. Error Message: "+response.message());
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal server error. Error: "+ e.getMessage());
        }
    }

    private JSONObject CriarParametros(JSONObject jsondata) {
        JSONObject data = new JSONObject(jsondata);
        JSONObject params = new JSONObject();
        //Nome = Primeiro nome e Último nome
        params.put("name", data.getJSONObject("candidates").getJSONObject("name")+" "+data.getJSONObject("candidates").getJSONObject("lastName"));
        params.put("email", data.getJSONObject("candidates").getJSONObject("email"));
        //Senha e Usuário = CPF e Data de Nascimento
        params.put("username", data.getJSONObject("candidates").getJSONObject("identificationDocument"));
        params.put("password", data.getJSONObject("candidates").getJSONObject("birthdate"));
        return params;
    }
}
