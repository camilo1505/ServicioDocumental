/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.service.impl;

import mongo.proyect.servicioDocumental.dto.UsuarioDTO;
import mongo.proyect.servicioDocumental.entity.Usuario;
import mongo.proyect.servicioDocumental.repository.UsuarioRepository;
import mongo.proyect.servicioDocumental.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author junpa
 */
@Service
public class DefaultUsuarioService implements UsuarioService{
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public Usuario buscarUsuario(String Nombre) {
       
        Usuario usuario = new Usuario();
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        System.out.println("me dirijo a buscar el usuario por su nombre: " + Nombre);
        usuario = usuarioRepository.findByUsuario(Nombre);
        if(usuario!=null){
            System.out.println("el usuario encontrado es:" + usuario.getNombre());
        }
        return usuario;
    }
    
    
    
}
