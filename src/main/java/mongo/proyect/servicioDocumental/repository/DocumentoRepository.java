/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.repository;

import java.util.List;
import java.util.Optional;
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
    
    @Query("{nombre:'?0',autor:'?1'}")
    Optional<Documento> findNombreDocumentoAutor( String nombreDocumento, String autor);
    
    @Query("{nombre:'?0',estado:'publicado'}")
    List<Documento> findNombreDocumento( String nombreDocumento);
    
    @Query("{autor:'?0',estado:'publicado'}")
    List<Documento> findAutor(String Autor);
    
    @Query("{autor:'?0'}")
    List<Documento> findAutorMisDocumentos(String Autor);
    
    @Query("{etiquetas: { $in: [?0] }}")
    List<Documento> findEtiqueta(List<String> etiquetas);
}
