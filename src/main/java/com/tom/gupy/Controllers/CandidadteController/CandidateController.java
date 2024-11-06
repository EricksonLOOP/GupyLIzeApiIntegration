package com.tom.gupy.Controllers.CandidadteController;

import com.tom.gupy.Models.CandidateModel.BodyCandididadteRegistration;
import com.tom.gupy.Services.UserServices.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/candidate")
public class CandidateController {
    @Autowired
    private final UserServices userServices;

    public CandidateController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping
    public ResponseEntity<?> testCandidate(@RequestBody BodyCandididadteRegistration body){
        return userServices.encontrarOuCriarUsuario(body);

    }
}
