package com.tom.gupy.Controllers.Requests;

import com.squareup.okhttp.*;
import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;

public class GupyApiRequest {

    @PostConstruct
    public void PegarDadosDaGupy(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.gupy.io/api/v1/jobs/{jobId}/applications")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer "+System.getenv("GUPY_API_KEY"))
                .build();
        try{
            Response response = client.newCall(request).execute();// Realizar Chamada Gupy
            String jsonData = response.body().string();//Retorno JSON Gupy

            //CriarUsuarioLize(jsonData); //Criar um Usuário na Lize
        } catch (IOException e ){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void CriarUsuarioLize(String jsonData){
        JSONObject ReqFormat = CriarRequisicao(jsonData); // Criando Requisição em JSON
        RequestBody reqBody = RequestBody.create(MediaType.parse("application/json"), ReqFormat.toString());
        OkHttpClient clientLize = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://app.lizeedu.com.br/api/v2/students/")
                .post(reqBody)
                .addHeader("Authorization", "Token "+System.getenv("LIZE_API_KEY"))
                .addHeader("Content-Type", "application/json")
                .build();
        try{
            Response response = clientLize.newCall(request).execute();
            System.out.println(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public JSONObject CriarRequisicao(String jsonData){
        JSONObject data = new JSONObject(jsonData);
        JSONObject BodyReq = new JSONObject();
        //Adicionando Caracteristicas:
        String NomeUsuario = data.getString("candidateName");
        String EmailUsuario = data.getString("candidateEmail");
        String StringPassword = data.getString("candidateName");
        BodyReq.put("name",NomeUsuario);
        BodyReq.put("email", EmailUsuario);
        BodyReq.put("responsible_email", EmailUsuario);
        BodyReq.put("username", EmailUsuario);
        BodyReq.put("password", "12344567TestSenha!.");
        return BodyReq;

    }
}
