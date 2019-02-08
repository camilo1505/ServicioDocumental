/*
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.controller;

import java.util.ArrayList;
import java.util.List;
import mongo.proyect.servicioDocumental.dto.ArchivoDTO;
import mongo.proyect.servicioDocumental.dto.DocumentoDTO;
import mongo.proyect.servicioDocumental.entity.Etiquetas;
import mongo.proyect.servicioDocumental.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
@RequestMapping("/documento")
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
            @RequestBody DocumentoDTO documento,
            @RequestParam("usuario") String usuario){
        
        DocumentoDTO documentoDTO = new DocumentoDTO();
        if(documento!=null){
            documentoDTO = documentoService.editarDocumento(usuario,documento);
            if(documentoDTO!=null){
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/eliminarDocumento")
    public ResponseEntity<?> eliminarDocumento(
            @RequestParam("nombreDocumento") String documento,
            @RequestParam("usuario") String usuario){
        DocumentoDTO documentoDTO = new DocumentoDTO();
        documentoDTO.setNombre(documento);
        documentoDTO.setUsuario(usuario);
        if(documento!=null){
            documentoDTO = documentoService.eliminarDocumento(documentoDTO);
            if(documentoDTO!=null){
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/guardarArchivo")
    public ResponseEntity<?> guardarArchivo(
            @RequestParam("nombreDocumento") String nombreDocumento,
            @RequestParam("usuario")String autor,
            @RequestHeader("file") MultipartFile file,
            @RequestParam("ocr") boolean ocr) throws Exception{
        System.out.println("Entro el archivo " + file.getOriginalFilename());
        DocumentoDTO documento = new DocumentoDTO();
        documento.setNombre(nombreDocumento);
        documento.setUsuario(autor);
        ArchivoDTO archivo = new ArchivoDTO();
        archivo.setOCR(ocr);
        archivo.setNombreArchivo(file.getOriginalFilename());
        DocumentoDTO documentoDTO = new DocumentoDTO();
        if(documento!=null && archivo!=null){
            documentoDTO = documentoService.guardarArchivo(documento,archivo,file);
            if(documentoDTO !=null){
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/eliminarArchivo")
    public ResponseEntity<?> eliminarArchivo(
            @RequestParam ("documento") String documento,
            @RequestParam("archivo") String archivo,
            @RequestParam("usuario") String usuario){
        
        DocumentoDTO documentoDTO = new DocumentoDTO();
        documentoDTO.setNombre(documento);
        documentoDTO.setUsuario(usuario);
        if(!documento.matches("") && !archivo.matches("")){
            documentoDTO = documentoService.eliminarArchivo(documentoDTO, archivo);
            if(documentoDTO !=null){
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/cambiarNombreArchivo")
    public ResponseEntity<?> cambiarNombreArchivo(
            @RequestParam("nombreDocumento") String documento,
            @RequestParam("nombreActual") String archivo,
            @RequestParam("nombreNuevo") String nombreArchivo,
            @RequestParam("usuario")String usuario){

        DocumentoDTO documentoDTO = new DocumentoDTO();
        documentoDTO.setNombre("nombreDocumento");
        documentoDTO.setUsuario(usuario);
        if(documento!=null && !archivo.matches("") && !nombreArchivo.matches("")){
            documentoDTO = documentoService.cambiarNombreArchivo(documentoDTO, archivo, nombreArchivo);
            if(documentoDTO !=null){
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/documentos")
    public ResponseEntity<?> documentos(
            @RequestParam("usuario") String usuario){
        List<DocumentoDTO> documentosDTO = new ArrayList<>();
        if(!usuario.matches("")){
            documentosDTO = documentoService.todosLosDocumentos(usuario);
            if(documentosDTO !=null){
                return ResponseEntity.ok(documentosDTO);
            }
        }      
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/misDocumentos")
    public ResponseEntity<?> misDocumentos(
            @RequestParam("usuario") String usuario ){        
        List<DocumentoDTO> documentosDTO = new ArrayList<>();
        if(!usuario.matches("")){
            documentosDTO = documentoService.misDocumentos(usuario);
            if(documentosDTO !=null){
                return ResponseEntity.ok(documentosDTO);
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/consultar")
    public ResponseEntity<?> consultar(
            @RequestParam("consulta") String consulta,
            @RequestParam("usuario") String usuario,
            @RequestParam("etiqueta") String etiqueta,
            @RequestParam("misDocumentos") boolean misDocumentos){
        
        List<String> etiquetas = new ArrayList<>();
        if(!etiqueta.matches("")){
            etiquetas.add(etiqueta);
        }
        List<DocumentoDTO> documentosDTO = new ArrayList<>();
        if(!usuario.matches("")){
            if(!misDocumentos){
                if(!etiquetas.isEmpty()){
                    if(!consulta.matches("")){
                        documentosDTO = documentoService.consultarEntreEtiquetas(usuario,etiquetas,consulta);
                    }
                    else{
                        documentosDTO = documentoService.consultarEtiquetas(usuario,etiquetas);
                    }
                }
                else{
                    if(!consulta.matches("")){
                        documentosDTO = documentoService.mostrarDocumentos(usuario, consulta);
                    }
                    else{
                        documentosDTO = documentoService.todosLosDocumentos(usuario);
                    }
                }
            }
            else{
                if(!etiquetas.isEmpty()){
                    if(!consulta.matches("")){
                        documentosDTO = documentoService.consultarEntreEtiquetasMisDocumentos(usuario,etiquetas,consulta);////general
                    }
                    else{
                        documentosDTO = documentoService.consultarEtiquetasMisDocumentos(usuario,etiquetas);/// mia
                    }
                }
                else{
                    if(!consulta.matches("")){
                        documentosDTO = documentoService.consultarMisDocumentos(usuario,consulta);
                    }
                    else{
                        documentosDTO = documentoService.misDocumentos(usuario);
                    }
                }
            }
        }
        if(!documentosDTO.isEmpty())
        {
            return ResponseEntity.ok(documentosDTO);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/cloudEtiquetas")
        public ResponseEntity<?> eti(){
        List<Etiquetas> lista = new ArrayList<>();
        lista = documentoService.etiquetas();
        if(!lista.isEmpty())
            {
                return ResponseEntity.ok(lista);
            }
        return ResponseEntity.noContent().build();
    }
        
    @GetMapping("/consultarEtiqueta")
    public ResponseEntity<?> busquedaEtiqueta(
    @RequestParam("usuario") String usuario,
    @RequestParam("etiqueta")String etiqueta){
        List<DocumentoDTO> documentos = new ArrayList<>();
        List<String> etiquetas = new ArrayList<>();
        if(!etiqueta.matches("")){
            etiquetas.add(etiqueta);
        }
        if(!etiquetas.isEmpty()){
            documentos = documentoService.consultarEtiquetas(usuario,etiquetas);
            if(!documentos.isEmpty())
            {
                return ResponseEntity.ok(documentos);
            }
        }
        return ResponseEntity.badRequest().build();
    }

}
