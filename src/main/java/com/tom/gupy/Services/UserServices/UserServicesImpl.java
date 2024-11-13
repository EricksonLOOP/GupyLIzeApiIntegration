package com.tom.gupy.Services.UserServices;

import com.squareup.okhttp.*;
import com.tom.gupy.Models.CandidateModel.BodyCandidateRegistration;
import com.tom.gupy.Models.CandidateModel.CandidateRegistrationResponse;
import com.tom.gupy.Services.GlaiServices.GlaiServices;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private final GlaiServices glaiServices;

    public UserServicesImpl(GlaiServices glaiServices) {
        this.glaiServices = glaiServices;
    }

    @Override
    public ResponseEntity<CandidateRegistrationResponse> encontrarOuCriarUsuario(BodyCandidateRegistration bodyCandidateRegistration) {
        try{
            OkHttpClient client = new OkHttpClient();
            JSONObject params = glaiServices.CriarParametros(bodyCandidateRegistration);
            RequestBody requestBody = RequestBody.create(MediaType.parse("apllication/json"), params.toString());
            Request request = new Request.Builder()
                    .url("https://app.lizeedu.com.br/api/v2/students/")
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authentication", "Token "+System.getenv("LIZEKey"))
                    .build();
            Response res = client.newCall(request).execute();
            if (res.isSuccessful()){
                JSONObject userData = new JSONObject(res.body().string());
                String userId = userData.get("id").toString();
                if (glaiServices.AdicionarUsuarioNaClasse(bodyCandidateRegistration.getTest_id(), userId, userData)){
                    String token = glaiServices.GerarTokenSSO(bodyCandidateRegistration.getEmail());
                    CandidateRegistrationResponse response = new CandidateRegistrationResponse(bodyCandidateRegistration.getTest_id()+"&"+userId, "https://app.lizeedu.com.br/conta/sso?access_token=" + token);
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                }
            }else{
                throw new RuntimeException("Erro ao criar usuário no LizeEdu");
            }

        } catch (Exception e) {
            throw new RuntimeException("Ocorreu erro interno ao tentar criar usuário e retornar parametros");
        }
        return null;
    }
}
