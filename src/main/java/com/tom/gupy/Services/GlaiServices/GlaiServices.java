package com.tom.gupy.Services.GlaiServices;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface GlaiServices {
    ResponseEntity<?> criarUsuario(JSONObject jsondata);
}
