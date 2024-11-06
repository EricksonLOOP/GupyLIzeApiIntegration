package com.tom.gupy.Services.GlaiServices;

import com.squareup.okhttp.*;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class GlaiServicesImpl implements GlaiServices {
    @Override
    public ResponseEntity<?> criarUsuario(JSONObject jsondata) {
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject BodyParams = CriarParametros(jsondata);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), BodyParams.toString());
            Request createUserRequest = new Request.Builder()
                    .url("https://app.lizeedu.com.br/api/v2/students/")
                    .post(requestBody)
                    .addHeader("Authorization", "Token " + System.getenv("LIZE_API_KEY"))
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(createUserRequest).execute();

            if (response.isSuccessful()) {
                RequestBody emailUser = RequestBody.create(MediaType.parse("application/json"), BodyParams.get("email").toString());
                Request tokenRequest = new Request.Builder()
                        .url("https://app.lizeedu.com.br/api/v2/sso/generate_accesss_token/")
                        .post(emailUser)
                        .addHeader("Authorization", "Token " + System.getenv("LIZE_API_KEY"))
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response tokenResponse = client.newCall(tokenRequest).execute();

                if (tokenResponse.isSuccessful()) {
                    JSONObject generatedToken = new JSONObject(tokenResponse.body().string());
                    String accessToken = generatedToken.getString("access_token");
                    String redirectUrl = "https://app.lizeedu.com.br/conta/sso?access_token=" + accessToken;

                    return ResponseEntity.created(URI.create(redirectUrl)).body("Usuário criado na LizeEdu com sucesso!");
                } else {
                    return ResponseEntity.badRequest().body("Erro ao gerar token: " + tokenResponse.message());
                }
            } else {
                return ResponseEntity.status(response.code()).body("Erro ao criar usuário. Mensagem: " + response.message());
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno. Detalhes: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> tests() {
        return null;
    }

    private JSONObject CriarParametros(JSONObject jsondata) {
        JSONObject data = new JSONObject(jsondata);
        JSONObject params = new JSONObject();

        params.put("name", data.getJSONObject("candidates").optString("name", "default_name"));
        params.put("email", data.getJSONObject("candidates").optString("email", "default_email"));
        params.put("username", data.getJSONObject("candidates").optString("identificationDocument", "default_username"));
        params.put("password", data.getJSONObject("candidates").optString("birthdate", "default_password"));

        return params;
    }
}
