/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.repository;

import java.util.List;
import mongo.proyect.servicioDocumental.entity.Documento;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author junpa
 */
@Repository
public interface DocumentoRepository extends MongoRepository<Documento, ObjectId>{
    
    @Query("{nombre:'?0'}")
    List<Documento> findNombreDocumento( String nombreDocumento);
    
    @Query("{autor:'?0'}")
    List<Documento> findAutor(ObjectId Autor);
    
    @Query("{etiquetas: { $in: ['?0'] }")
    List<Documento> findEtiqueta(List<String> etiquetas);
}
