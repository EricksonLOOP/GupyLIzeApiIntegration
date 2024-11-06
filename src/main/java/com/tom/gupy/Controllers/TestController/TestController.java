package com.tom.gupy.Controllers.TestController;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tom.gupy.Models.TestsModel.TestItems;
import com.tom.gupy.Services.TestServices.TestServices;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private final TestServices testServices;

    public TestController(TestServices testServices) {
        this.testServices = testServices;
    }

    @GetMapping
    public ResponseEntity<TestItems> GetTests(@RequestParam(required = false, defaultValue = "0") int offset,
                                              @RequestParam(required = false, defaultValue = "0") int limit){
        try {

            return ResponseEntity.ok().body(testServices.RecuperarTestes(limit, offset));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
