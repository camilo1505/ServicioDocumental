/*
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.controller;

import java.util.ArrayList;
import java.util.List;
import mongo.proyect.servicioDocumental.dto.DocumentoDTO;
import mongo.proyect.servicioDocumental.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author junpa
 */

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("api/v1/documento")
public class DocumentoController {
    @Autowired
    private DocumentoService documentoService;
    
    @PostMapping("/crearDocumento")
    public ResponseEntity<?> crearDocumento(
            @RequestBody DocumentoDTO documento){
        
        DocumentoDTO documentoDTO = new DocumentoDTO();
        if(documento!= null){
            documentoDTO = documentoService.crearDocumento(documento);
            if(documentoDTO!=null){
                return ResponseEntity.ok(documentoDTO);
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/editarDocumento")
    public ResponseEntity<?> editarDocumento(
            @RequestBody DocumentoDTO documento){
        
        DocumentoDTO documentoDTO = new DocumentoDTO();
        if(documento!=null){
            documentoDTO = documentoService.editarDocumento(documento);
            if(documentoDTO!=null){
                return ResponseEntity.ok(documentoDTO);
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @DeleteMapping("/eliminarDocumento")
    public ResponseEntity<?> eliminarDocumento(
            @RequestBody DocumentoDTO documento){
        
        DocumentoDTO documentoDTO = new DocumentoDTO();
        if(documento!=null){
            documentoDTO = documentoService.eliminarDocumento(documento);
            if(documentoDTO!=null){
                return ResponseEntity.ok(documentoDTO);
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/guardarArchivo")
    public ResponseEntity<?> guardarArchivo(
            @RequestBody DocumentoDTO documento,
            @RequestParam("archivo")String archivo,
            @RequestParam("nombreArchivo") String nombreArchivo){
        
        DocumentoDTO documentoDTO = new DocumentoDTO();
        if(documento!=null && !archivo.matches("") && !nombreArchivo.matches("")){
            documentoDTO = documentoService.guardarArchivo(documento, archivo, nombreArchivo);
            if(documentoDTO !=null){
                return ResponseEntity.ok(documentoDTO);
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @DeleteMapping("/eliminarArchivo")
    public ResponseEntity<?> eliminarArchivo(
            @RequestBody DocumentoDTO documento,
            @RequestParam("archivo") String archivo){
        
        DocumentoDTO documentoDTO = new DocumentoDTO();
        if(documento!=null && !archivo.matches("")){
            documentoDTO = documentoService.eliminarArchivo(documento, archivo);
            if(documentoDTO !=null){
                return ResponseEntity.ok(documentoDTO);
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/cambiarNombreArchivo")
    public ResponseEntity<?> cambiarNombreArchivo(
            @RequestBody DocumentoDTO documento,
            @RequestParam("archivo") String archivo,
            @RequestParam("nombreArchivo") String nombreArchivo){

        DocumentoDTO documentoDTO = new DocumentoDTO();
        if(documento!=null && !archivo.matches("") && !nombreArchivo.matches("")){
            documentoDTO = documentoService.cambiarNombreArchivo(documento, archivo, nombreArchivo);
            if(documentoDTO !=null){
                return ResponseEntity.ok(documentoDTO);
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/consultarDocumento")
    public ResponseEntity<?> consultarDocumeto(
            @RequestParam("nombreDocumento") String nombreDocumento,
            @RequestParam("autor") String autor, 
            @RequestParam("etiquetas")String etiquetas){
        
        List<DocumentoDTO> documentosDTO = new ArrayList<>();
        String[] etiquetaSplit = etiquetas.split("-");
        List<String> etiqueta = new ArrayList<>();
        for(String eti:etiquetaSplit){
            etiqueta.add(eti);
        }
        if(!nombreDocumento.matches("") || !autor.matches("") || !etiqueta.isEmpty()){
            documentosDTO = documentoService.consultarDocumento(nombreDocumento, autor, etiqueta);
            if(documentosDTO !=null){
                return ResponseEntity.ok(documentosDTO);
            }
        }
        else{
            documentosDTO = documentoService.mostrarDocumentos();
            if(documentosDTO!=null){
                return ResponseEntity.ok(documentosDTO);
            }
        }        
        return ResponseEntity.badRequest().build();
    }

}
