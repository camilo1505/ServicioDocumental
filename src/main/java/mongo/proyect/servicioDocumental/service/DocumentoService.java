/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.service;

import java.util.List;
import mongo.proyect.servicioDocumental.dto.DocumentoDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author junpa
 */
public interface DocumentoService {
    
    DocumentoDTO crearDocumento(DocumentoDTO documento);
    
    DocumentoDTO editarDocumento(DocumentoDTO documento);
    
    DocumentoDTO eliminarDocumento(DocumentoDTO documento);
    
    DocumentoDTO guardarArchivo(DocumentoDTO documento, MultipartFile file) throws Exception;
    
    DocumentoDTO eliminarArchivo(DocumentoDTO documento,String archivo);
    
    DocumentoDTO cambiarNombreArchivo(DocumentoDTO documento,String archivo, String nombreArchivo);
    
    List<DocumentoDTO> consultarDocumento(String consulta);
    
    List<DocumentoDTO> mostrarDocumentos();
    
    List<DocumentoDTO> misDocumentos(String autor);
    
    String OCRFiles(DocumentoDTO documento,MultipartFile file) throws Exception;
}
