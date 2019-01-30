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
    
    //@Query("{nombre:{$regex:'?0',$options:'i'},usuario:{$regex:'?1',$options:'i'}, estado:true}")
    //Optional<Documento> findNombreDocumentoAutor( String nombreDocumento, String usuario);
    
    //@Query("{nombre:{$regex:'?0',$options:'i'},estado:true}")
    //List<Documento> findNombreDocumento( String nombreDocumento);
    
    //@Query("{usuario:{$regex:'?0',$options:'i'},estado:true}")
    //List<Documento> findAutor(String usuario);
    
    @Query("{usuario:'?1'}")
    List<Documento> findAutorMisDocumentos(String usuario);
    
    //@Query("{etiquetas: { $in: [?0] }, estado:true}")
    //List<Documento> findEtiqueta(List<String> etiquetas);
    /*@Query("mapReduce("
            + "function() {"
            + "     this.etiquetas.forEach(function(item){ emit(item,1); });"
            + "},"
            + "function(keys,values){"
            + "     var valor = 0"
            + "     for (var i=0; i < values.length;i++)"
            + "     valor+=1;"
            + "     }"
            + "     return valor;"
            + "},"
            + "{out:'total'}"
            + ").find();")
    List conteoTags();*/
    
    @Query("{nombre:'?0',usuario:'?1'}")
    Optional<Documento> nombreAutor( String nombreDocumento, String usuario);
    
    @Query("{$or:[{nombre:{$regex: ?0,$options:'i'}}, {usuario:{$regex: ?0,$options:'i'}},{ etiquetas:{$in:[?1]}}]}")
    List<Documento> findConsulta(String consulta, List<String> etiquetas);
    
    @Query("{$or:[{'estado':true},{'usuario':'?1'}}")
    List<Documento> consultaGeneral(String autor);
}
