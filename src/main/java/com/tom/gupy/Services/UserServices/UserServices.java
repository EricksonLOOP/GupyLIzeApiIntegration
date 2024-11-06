package com.tom.gupy.Services.UserServices;

import com.tom.gupy.Models.CandidateModel.BodyCandididadteRegistration;
import org.springframework.http.ResponseEntity;

public interface UserServices {
ResponseEntity<?> encontrarOuCriarUsuario(BodyCandididadteRegistration bodyCandididadteRegistration);
}
