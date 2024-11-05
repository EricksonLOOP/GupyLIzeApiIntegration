package com.tom.gupy.Controllers.Requests;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LizeApiRequest {
    @Autowired
    @PostConstruct
    public void GetStudents(){
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url("https://app.lizeedu.com.br/api/v2/students/")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Token 9cd3fa87001df9f4ac289af7eef8d51fd1c15f60 ")
                .build();
        try{
            Response response = client.newCall(req).execute();
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
