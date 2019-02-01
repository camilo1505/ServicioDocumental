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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends MongoRepository<Documento, ObjectId>{
    
    
    //@Query("{nombre:{$regex:'?0',$options:'i'},usuario:{$regex:'?1',$options:'i'}, estado:true}")
    //Optional<Documento> findNombreDocumentoAutor( String nombreDocumento, String usuario);
    
    //@Query("{nombre:{$regex:'?0',$options:'i'},estado:true}")
    //List<Documento> findNombreDocumento( String nombreDocumento);
    
    //@Query("{usuario:{$regex:'?0',$options:'i'},estado:true}")
    //List<Documento> findAutor(String usuario);

    @Query("{usuario:'?0'}")
    List<Documento> findAutorMisDocumentos(String usuario);
    
    @Query("{etiquetas: { $in: [?0] }, estado:true}")
    List<Documento> findEtiqueta(String etiquetas);
    
    
    @Query("{nombre:'?0',usuario:'?1'}")
    Optional<Documento> nombreAutor( String nombreDocumento, String usuario);
    
    @Query("{$or:[{ $and:[{nombre:{$regex: ?0,$options:'i'}},{estado:true}]},{ $and:[{usuario:{$regex: ?0,$options:'i'}},{estado:true}]},{usuario:?1}]}")
    List<Documento> findConsulta(String consulta, String usuario);

    @Query("{$and:[{nombre:{$regex: ?0,$options:'i'}},{usuario:?1}]}")
    List<Documento> findConsultaMisDocumentos(String consulta, String usuario);
        
    @Query("{$or:[{'estado':true},{'usuario':'?0'}]}")
    List<Documento> consultaGeneral(String autor);
}
