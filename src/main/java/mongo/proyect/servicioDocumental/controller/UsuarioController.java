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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author junpa
 */
@RestController
@RequestMapping("api/v1/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/consultarUsuario")
    public ResponseEntity<?> consultarUsuario(@RequestParam("Usuario") String Usuario,@RequestParam("password") String Password){
        UsuarioDTO usuarioDTO = null;
        usuarioDTO = usuarioService.buscarUsuario(Usuario,Password);
        if(usuarioDTO!=null){
            return ResponseEntity.ok(usuarioDTO);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/tipoUsuario")
    public ResponseEntity<?> tipoUsuario(@RequestParam("Usuario") String Usuario){
        String usuarioDTO = null;
        usuarioDTO = usuarioService.tipoUsuario(Usuario);
        if(usuarioDTO!=null){
            return ResponseEntity.ok(usuarioDTO);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/crearUsuario")
    public ResponseEntity<?> crearUsuario(@RequestParam("nombre") String Nombre,@RequestParam("usuario") String Usuario,@RequestParam("password") String Password,@RequestParam("tipoUsuario") String tipoUsuario){
        UsuarioDTO usuarioDTO = null;
        usuarioDTO = usuarioService.crearUsuario(Nombre, Usuario, Password, tipoUsuario);
        if(usuarioDTO!=null){
            return ResponseEntity.ok(usuarioDTO);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/cambiarTipoUsuario")
    public ResponseEntity<?> cambiarTipoUsuario(@RequestParam("Usuario") String Usuario,@RequestParam("password") String Password){
        String usuarioDTO = null;
        usuarioDTO = usuarioService.cambiarTipoUsuario(Usuario,Password);
        if(usuarioDTO!=null){
            return ResponseEntity.ok(usuarioDTO);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/cambiarPassword")
    public ResponseEntity<?> cambiarPassword(@RequestParam("usuario") String Usuario,@RequestParam("password") String Password){
        UsuarioDTO usuarioDTO = null;
        usuarioDTO = usuarioService.cambiarPasswordUsuario(Usuario, Password);
        if(usuarioDTO!=null){
            return ResponseEntity.ok(usuarioDTO);
        }
        return ResponseEntity.badRequest().build();
    }
    
}
