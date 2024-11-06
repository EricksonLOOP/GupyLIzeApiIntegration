package com.tom.gupy.Controllers.TestResultController;

import com.tom.gupy.Services.GlaiServices.GlaiServices;
import com.tom.gupy.Services.TestServices.TestServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/result/{resultId}")
public class TestResultController {
    @Autowired
    private final TestServices testServices;

    public TestResultController(TestServices testServices) {
        this.testServices = testServices;
    }

    @GetMapping
    public ResponseEntity<?> testResult(@PathVariable("resultId") String idResult){
        return testServices.PegarResultadosDoUsuarioID(idResult);
    }
}
