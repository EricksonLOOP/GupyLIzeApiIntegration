package com.tom.gupy.Services.UserServices;

import com.tom.gupy.Models.CandidateModel.BodyCandidateRegistration;
import org.springframework.http.ResponseEntity;

public interface UserServices {
ResponseEntity<?> encontrarOuCriarUsuario(BodyCandidateRegistration bodyCandidateRegistration);
}
