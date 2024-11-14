package com.tom.gupy.Services.UserServices;

import com.squareup.okhttp.*;
import com.tom.gupy.Models.CandidateModel.BodyCandidateRegistration;
import com.tom.gupy.Models.CandidateModel.CandidateRegistrationResponse;
import com.tom.gupy.Services.GlaiServices.GlaiServices;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private final GlaiServices glaiServices;

    public UserServicesImpl(GlaiServices glaiServices) {
        this.glaiServices = glaiServices;
    }

    @Override
    public ResponseEntity<CandidateRegistrationResponse> encontrarOuCriarUsuario(BodyCandidateRegistration bodyCandidateRegistration) {
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject params = glaiServices.CriarParametros(bodyCandidateRegistration);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
            Request request = new Request.Builder()
                    .url("https://app.lizeedu.com.br/api/v2/students/")
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Token 9cd3fa87001df9f4ac289af7eef8d51fd1c15f60")
                    .build();

            Response res = client.newCall(request).execute();
                res.isSuccessful();
            if (res.isSuccessful() && res.body() != null) {
                JSONObject userData = new JSONObject(res.body().string());
                String userId = userData.get("id").toString();

                if (glaiServices.AdicionarUsuarioNaClasse(bodyCandidateRegistration.getTest_id(), userId, userData)) {
                    String token = glaiServices.GerarTokenSSO(bodyCandidateRegistration.getEmail());
                    CandidateRegistrationResponse response = new CandidateRegistrationResponse(
                            bodyCandidateRegistration.getTest_id() + "&" + userId,
                            "https://app.lizeedu.com.br/conta/sso?access_token=" + token
                    );
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                }
            } else {
                throw new RuntimeException("Erro ao criar usuário no LizeEdu: " + res.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro de comunicação com a API: " + e.getMessage(), e);
        } catch (JSONException e) {
            throw new RuntimeException("Erro ao processar a resposta JSON: " + e.getMessage(), e);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

