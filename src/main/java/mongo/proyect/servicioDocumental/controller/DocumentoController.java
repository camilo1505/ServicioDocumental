/*
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.MultipartConfig;
import mongo.proyect.servicioDocumental.dto.DocumentoDTO;
import mongo.proyect.servicioDocumental.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
        
        DocumentoDTO documentoDTO = new DocumentoDTO();
        if(documento!=null){
            documentoDTO = documentoService.editarDocumento(documento);
            if(documentoDTO!=null){
                return ResponseEntity.ok(documentoDTO);
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/eliminarDocumento")
    public ResponseEntity<?> eliminarDocumento(
            @RequestBody DocumentoDTO documento){
        
        System.out.println("el documento a eliminar es: " + documento.getNombre());
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
            @RequestParam("nombreArchivo") String nombreArchivo,
            @RequestParam("autor")String autor,
            @RequestParam("file") MultipartFile file){
        
        DocumentoDTO documento = new DocumentoDTO();
        documento.setNombre(nombreArchivo);
        documento.setUsuario(autor);
        System.out.println("entre a guardar los archivos");
        DocumentoDTO documentoDTO = new DocumentoDTO();
        if(documento!=null && file!=null){
            documentoDTO = documentoService.guardarArchivo(documento,file);
            if(documentoDTO !=null){
                return ResponseEntity.ok(documentoDTO);
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/eliminarArchivo")
    public ResponseEntity<?> eliminarArchivo(
            @RequestBody DocumentoDTO documento,
            @RequestParam("archivo") String archivo){
        
        DocumentoDTO documentoDTO = new DocumentoDTO();
        System.out.println("voy a eliminar el archivo");
        if(documento!=null && !archivo.matches("")){
            System.out.println("el nombre del documento es: " + documento.getNombre());
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
