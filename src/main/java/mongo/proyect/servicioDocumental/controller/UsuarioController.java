/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.controller;

import mongo.proyect.servicioDocumental.dto.UsuarioDTO;
import mongo.proyect.servicioDocumental.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author junpa
 */
@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("api/v1/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/consultarUsuario")
    public ResponseEntity<?> consultarUsuario(
            @RequestParam("nombreUsuario") String Usuario, 
            @RequestParam("password")String password){
        
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuario(Usuario);
        usuarioDTO.setPassword(password);
        usuarioDTO = usuarioService.buscarUsuario(usuarioDTO);
        if(usuarioDTO!=null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/tipoUsuario")
    public ResponseEntity<?> tipoUsuario(
            @RequestParam("nombreUsuario") String Usuario, 
            @RequestParam("password")String password){
        
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuario(Usuario);
        usuarioDTO.setPassword(password);
        String usuarioString = null;
        usuarioString = usuarioService.tipoUsuario(usuarioDTO);
        if(usuarioDTO!=null){
            return ResponseEntity.ok(usuarioString);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/crearUsuario")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDTO Usuario){
        UsuarioDTO usuarioDTO = null;
        usuarioDTO = usuarioService.crearUsuario(Usuario);
        if(usuarioDTO!=null){
            return ResponseEntity.ok(usuarioDTO);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/cambiarTipoUsuario")
    public ResponseEntity<?> cambiarTipoUsuario(@RequestBody UsuarioDTO Usuario){
        String usuarioDTO = null;
        usuarioDTO = usuarioService.cambiarTipoUsuario(Usuario);
        if(usuarioDTO!=null){
            return ResponseEntity.ok(usuarioDTO);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/cambiarPassword")
    public ResponseEntity<?> cambiarPassword(@RequestBody UsuarioDTO Usuario){
        UsuarioDTO usuarioDTO = null;
        usuarioDTO = usuarioService.cambiarPasswordUsuario(Usuario);
        if(usuarioDTO!=null){
            return ResponseEntity.ok().build()
                    ;
        }
        return ResponseEntity.badRequest().build();
    }
    
}
