/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.repository;

import mongo.proyect.servicioDocumental.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author junpa
 */
@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, Long> {

    Usuario findByUsuario( String Nombre);
}
