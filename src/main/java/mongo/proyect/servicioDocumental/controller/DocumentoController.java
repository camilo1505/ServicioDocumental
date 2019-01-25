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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author junpa
 */

@CrossOrigin(origins = "http://localhost:8081")
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
        System.out.println("documento para editar");
        System.out.println("nombre: " + documento.getNombre());
        System.out.println("descripcion: " + documento.getDescripcion());
        System.out.println("etiquetas: " + documento.getEtiquetas());
        DocumentoDTO documentoDTO = new DocumentoDTO();
        if(documento!=null){
            documentoDTO = documentoService.editarDocumento(documento);
            if(documentoDTO!=null){
                return ResponseEntity.ok().build();
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
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/guardarArchivo")
    public ResponseEntity<?> guardarArchivo(
            @RequestParam("nombreArchivo") String nombreArchivo,
            @RequestParam("autor")String autor,
            @RequestParam(name = "file") MultipartFile[] file) throws Exception{
        
        
        DocumentoDTO documento = new DocumentoDTO();
        documento.setNombre(nombreArchivo);
        documento.setUsuario(autor);
        DocumentoDTO documentoDTO = new DocumentoDTO();
        if(documento!=null && file!=null){
            for(MultipartFile archivo:file){
                documentoDTO = documentoService.guardarArchivo(documento,archivo);
            }
            if(documentoDTO !=null){
                return ResponseEntity.ok().build();
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
                return ResponseEntity.ok().build();
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
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/consultarDocumento")
    public ResponseEntity<?> consultarDocumeto(
            @RequestParam("consulta") String consulta){
        
        List<DocumentoDTO> documentosDTO = new ArrayList<>();
        if(!consulta.matches("")){
            documentosDTO = documentoService.consultarDocumento(consulta);
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

    @GetMapping("/misDocumentos")
    public ResponseEntity<?> misDocumentos(
            @RequestParam("autor") String autor ){        
        List<DocumentoDTO> documentosDTO = new ArrayList<>();
        if(!autor.matches("")){
            documentosDTO = documentoService.misDocumentos( autor);
            if(documentosDTO !=null){
                return ResponseEntity.ok(documentosDTO);
            }
        }
        return ResponseEntity.badRequest().build();
    }

}
