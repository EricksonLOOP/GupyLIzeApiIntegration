package com.tom.gupy.Controllers.Requests;

import com.tom.gupy.Services.GlaiServices.GlaiServices;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ContratoController {
    @Autowired
    private final GlaiServices glaiServices;

    public ContratoController(GlaiServices glaiServices) {
        this.glaiServices = glaiServices;
    }

    @GetMapping("/")
    public String HelloWorld(){
        return "Welcome To GLAI (Gupy Lize API Integration).";
    }
    @PostMapping("/lize/create")
    public ResponseEntity<?> CriarUsuarioNoLize(@RequestBody JSONObject jsondata){
        return glaiServices.criarUsuario(jsondata);
    }
}
