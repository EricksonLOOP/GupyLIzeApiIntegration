package com.tom.gupy.Services.GlaiServices;

import com.tom.gupy.Models.CandidateModel.BodyCandidateRegistration;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;


public interface GlaiServices {
    JSONObject CriarParametros(BodyCandidateRegistration bodyCandidateRegistration);

    String GerarTokenSSO(String email);

    boolean AdicionarUsuarioNaClasse(String testId, String userId, JSONObject params);

}
