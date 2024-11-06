package com.tom.gupy.Controllers.CandidateController;

import com.tom.gupy.Models.CandidateModel.BodyCandidateRegistration;
import com.tom.gupy.Services.UserServices.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/candidate")
public class CandidateController {
    @Autowired
    private final UserServices userServices;

    public CandidateController(UserServices userServices) {
        this.userServices = userServices;
    }
    @PostMapping
    public ResponseEntity<?> testCandidate(@Valid  @RequestBody BodyCandidateRegistration body){
        return userServices.encontrarOuCriarUsuario(body);

    }
}
