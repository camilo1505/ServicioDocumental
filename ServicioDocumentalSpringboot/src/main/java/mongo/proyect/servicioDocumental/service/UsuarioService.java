/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.service;

import mongo.proyect.servicioDocumental.dto.UsuarioDTO;

/**
 *
 * @author junpa
 */
public interface UsuarioService {
    
    UsuarioDTO buscarUsuario(String Usuario, String Password);
    
    String tipoUsuario(String Usuario);
    
    UsuarioDTO crearUsuario(String Nombre, String Usuario, String Password, String tipoUsuario);

    String cambiarTipoUsuario(String Usuario,String Password);
    
    UsuarioDTO cambiarPasswordUsuario(String Usuario, String Password);
    
    
}
