package com.tom.gupy.Services.GlaiServices;

import com.tom.gupy.Models.CandidateModel.BodyCandididadteRegistration;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface GlaiServices {
    JSONObject CriarParametros(BodyCandididadteRegistration bodyCandididadteRegistration);
    String GerarTokenSSO(String email);

    boolean AdicionarUsuarioNaClasse(String testId, String userId, JSONObject params);
}
