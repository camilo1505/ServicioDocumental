/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.service;

import mongo.proyect.servicioDocumental.entity.Usuario;

/**
 *
 * @author junpa
 */
public interface UsuarioService {
    
    Usuario buscarUsuario(String Nombre);
    
}
