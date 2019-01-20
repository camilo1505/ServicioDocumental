/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mongo.proyect.servicioDocumental.dto.DocumentoDTO;
import mongo.proyect.servicioDocumental.dto.ArchivoDTO;
import mongo.proyect.servicioDocumental.dto.UsuarioDTO;
import mongo.proyect.servicioDocumental.entity.Documento;
import mongo.proyect.servicioDocumental.repository.DocumentoRepository;
import mongo.proyect.servicioDocumental.service.DocumentoService;
import mongo.proyect.servicioDocumental.service.UsuarioService;
import mongo.proyect.servicioDocumental.storage.FileSystemStorageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author junpa
 */
@Service
public class DefaultDocumentoService implements DocumentoService{
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FileSystemStorageService storageService;
    
    @Override
    public DocumentoDTO crearDocumento(DocumentoDTO documento) {
        Documento documentoDTO = new Documento();
        Optional<Documento> revisar = null;
        UsuarioDTO propietario = null;
        MultipartFile archivo = null;
        ArchivoDTO archivoDTO = new ArchivoDTO();
        archivoDTO.setNombreArchivo("init");
        archivoDTO.setArchivo("init");
        List<ArchivoDTO> archivos = new ArrayList<>();
        archivos.add(archivoDTO);
        propietario = usuarioService.buscarUsuarioNombre(documento.getUsuario());
        if(propietario!= null){
            if(documento!=null){
                revisar = documentoRepository.nombreAutor(documento.getNombre(), documento.getUsuario());
                if(!revisar.isPresent()){
                    documentoDTO.setNombre(documento.getNombre());
                    documentoDTO.setEtiquetas(documento.getEtiquetas());
                    documentoDTO.setDescripcion(documento.getDescripcion());
                    documentoDTO.setEstado(documento.getEstado());
                    documentoDTO.setArchivo(archivos);
                    documentoDTO.setUsuario(documento.getUsuario());
                    documentoDTO = documentoRepository.save(documentoDTO);
                    return modelMapper.map(documentoDTO, DocumentoDTO.class);
                }
            }
        }
        return null;
    }

    @Override
    public DocumentoDTO editarDocumento(DocumentoDTO documento) {
        
        Optional<Documento> documentoDTO = null;
        Documento auxiliar = new Documento();
         Optional<Documento> revisar = null;
        if(documento!=null){
            revisar = documentoRepository.nombreAutor(documento.getNombre(), documento.getUsuario());
            if(!revisar.isPresent()){
                documentoDTO = documentoRepository.findById(documento.getId());
                if(documentoDTO.isPresent()){
                    auxiliar = documentoDTO.get();
                    auxiliar.setNombre(documento.getNombre());
                    auxiliar.setDescripcion(documento.getDescripcion());
                    auxiliar.setEstado(documento.getEstado());
                    auxiliar.setEtiquetas(documento.getEtiquetas());
                    auxiliar = documentoRepository.save(auxiliar);
                    return modelMapper.map(auxiliar, DocumentoDTO.class);
                }
            }
        }
        return null;
    }

    @Override
    public DocumentoDTO eliminarDocumento(DocumentoDTO documento) {
        
        Optional<Documento> documentoDTO = null;
        Documento auxiliar = new Documento();
        if(documento!=null){
            documentoDTO = documentoRepository.findById(documento.getId());
            if(documentoDTO.isPresent()){
                auxiliar = documentoDTO.get();
                documentoRepository.delete(auxiliar);
                return modelMapper.map(auxiliar, DocumentoDTO.class);
            }
        }
        return null;
    }

    @Override
    public DocumentoDTO guardarArchivo(DocumentoDTO documento, MultipartFile archivo) {
        Optional<Documento> documentoDTO = null;
        Documento auxiliar = new Documento();
        List<ArchivoDTO> archivos = new ArrayList<>();
        ArchivoDTO file = new ArchivoDTO();
        boolean bandera = false;
        if(documento!=null && !archivo.isEmpty()){
            documentoDTO = documentoRepository.nombreAutor(documento.getNombre(), documento.getUsuario());
            if(documentoDTO.isPresent()){
                documentoDTO = documentoRepository.findById(documentoDTO.get().getId());
                if(documentoDTO.isPresent()){
                    auxiliar = documentoDTO.get();
                    archivos = auxiliar.getArchivo();
                    for(ArchivoDTO fil:archivos){
                        if(fil.getNombreArchivo().matches(archivo.getOriginalFilename())){
                            bandera = true;
                        }
                    }
                    if(!bandera){
                        file.setURL("C:\\java-exec\\upload-dir\\"+documento.getUsuario()+"\\"+documento.getNombre()+"\\"+archivo.getOriginalFilename());
                        file.setNombreArchivo(archivo.getOriginalFilename());
                        file.setArchivo(archivo.getOriginalFilename());
                        archivos.add(file);
                        auxiliar.setArchivo(archivos);
                        storageService.store(archivo,documento.getUsuario(),documento.getNombre());
                        auxiliar = documentoRepository.save(auxiliar);
                        return modelMapper.map(auxiliar, DocumentoDTO.class);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public DocumentoDTO eliminarArchivo(DocumentoDTO documento, String archivo) {
        
        Optional<Documento> documentoDTO = null;
        Documento auxiliar = new Documento();
        List<ArchivoDTO> auxiliarArchivos = new ArrayList<>();
        auxiliarArchivos = null;
        List<ArchivoDTO> archivos = new ArrayList<>();
        if(documento!=null && !archivo.matches("")){
            documentoDTO = documentoRepository.nombreAutor(documento.getNombre(),documento.getUsuario());
            if(documentoDTO.isPresent()){
                auxiliar = documentoDTO.get();
                archivos = null;
                for(ArchivoDTO arc:archivos){
                    if(!arc.getNombreArchivo().matches(archivo)){
                        auxiliarArchivos.add(arc);
                    }   
                }
                auxiliar.setArchivo(auxiliarArchivos);
                auxiliar = documentoRepository.save(auxiliar);
                return modelMapper.map(auxiliar, DocumentoDTO.class);
            }
        }
        return null;
    }

    @Override
    public DocumentoDTO cambiarNombreArchivo(DocumentoDTO documento, String archivo, String nombreArchivo) {
        
        Optional<Documento> documentoDTO = null;
        Documento auxiliar = new Documento();
        List<ArchivoDTO> archivos = new ArrayList<>();
        if(documento!=null && archivo.matches("")){
            documentoDTO = documentoRepository.nombreAutor(documento.getNombre(),documento.getUsuario());
            if(documentoDTO.isPresent()){
                auxiliar = documentoDTO.get();
                archivos = auxiliar.getArchivo();
                for(ArchivoDTO arc:archivos){
                    if(arc.getNombreArchivo().matches(archivo)){
                        arc.setNombreArchivo(nombreArchivo);
                    }
                }
                auxiliar.setArchivo(archivos);
                auxiliar = documentoRepository.save(auxiliar);
                return modelMapper.map(auxiliar, DocumentoDTO.class);
            }
        }
        return null;
    }

    @Override
    public List<DocumentoDTO> consultarDocumento(String consulta) {
        
        List<Documento> documentos = new ArrayList<>();
        List<DocumentoDTO> documentosDTO = new ArrayList<>();
        List<String> consultaList = new ArrayList<>();
        String[] consultaSplit = consulta.split(" ");
        for(String eti:consultaSplit){
            consultaList.add(eti);
        }
        
        documentos = documentoRepository.findConsulta(consulta, consultaList);
        if(!documentos.isEmpty())
        {
            for(Documento documento: documentos){
                documentosDTO.add(modelMapper.map(documento, DocumentoDTO.class));
            }
            return documentosDTO;
        }
        return null;
    }

    @Override
    public List<DocumentoDTO> mostrarDocumentos() {
        
        List<Documento> documentos = new ArrayList<>();
        List<DocumentoDTO> documentosDTO = new ArrayList<>();
        documentos = documentoRepository.findAll();
        if(!documentos.isEmpty()){
            for(Documento documento: documentos){
                documentosDTO.add(modelMapper.map(documento,DocumentoDTO.class));
            }
            return documentosDTO;
        }
        return null;
    }

    @Override
    public List<DocumentoDTO> misDocumentos(String autor) {
        List<Documento> documentos = new ArrayList<>();
        List<DocumentoDTO> documentosDTO = new ArrayList<>();
        UsuarioDTO autorDTO = new UsuarioDTO();
        autorDTO = usuarioService.buscarUsuarioNombre(autor);
        if(autorDTO!=null){
            documentos = documentoRepository.findAutorMisDocumentos(autorDTO.getUsuario());
            if(!documentos.isEmpty())
            {
                for(Documento documento: documentos){
                    documentosDTO.add(modelMapper.map(documento, DocumentoDTO.class));
                }
                return documentosDTO;
            }
        }
        
        return null;
    }
    
    
    
}
