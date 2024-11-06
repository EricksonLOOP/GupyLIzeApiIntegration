package com.tom.gupy.Services.TestServices;

import com.tom.gupy.Models.TestsModel.TestItems;
import org.springframework.http.ResponseEntity;

public interface TestServices {
    TestItems RecuperarTestes(Integer limit, Integer offset);

    ResponseEntity<?> PegarResultadosDoUsuarioID(String idResult);
}
