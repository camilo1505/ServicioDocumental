/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.controller;

import mongo.proyect.servicioDocumental.entity.Usuario;
import mongo.proyect.servicioDocumental.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<?> consultarUsuario(@RequestParam("nombre") String Nombre){
        Usuario usuario = new Usuario();
        System.out.println("mando la solicitud");
        usuario = usuarioService.buscarUsuario(Nombre);
        return ResponseEntity.ok(usuario);
    }
}
