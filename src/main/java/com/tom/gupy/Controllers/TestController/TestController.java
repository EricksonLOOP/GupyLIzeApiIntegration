package com.tom.gupy.Controllers.TestController;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public ResponseEntity<?> GetTests(){
        try {
            OkHttpClient client = new OkHttpClient();
            Request req = new Request.Builder()
                    .url("https://app.lizeedu.com.br/api/v2/exams/")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Token 9cd3fa87001df9f4ac289af7eef8d51fd1c15f60")
                    .build();
            Response response = client.newCall(req).execute();
            JSONObject data = new JSONObject(response.body().string());
            return ResponseEntity.ok().body("Data: "+data.get("results"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
