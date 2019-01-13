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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    @Override
    public DocumentoDTO crearDocumento(DocumentoDTO documento) {
        
        Documento documentoDTO = new Documento();
        Optional<Documento> revisar = null;
        ArchivoDTO archivo = new ArchivoDTO();
        archivo.setNombreArchivo("readme.txt");
        archivo.setType("txt");
        archivo.setUrl("//");
        List<ArchivoDTO> archivos = new ArrayList<>();
        archivos.add(archivo);
        if(documento!=null){
            revisar = documentoRepository.findNombreDocumentoAutor(documento.getNombre(), documento.getAutor());
            if(!revisar.isPresent()){
                documentoDTO.setNombre(documento.getNombre());
                documentoDTO.setEtiquetas(documento.getEtiquetas());
                documentoDTO.setDescripcion(documento.getDescripcion());
                documentoDTO.setDescripcion(documento.getDescripcion());
                documentoDTO.setEstado(documento.getEstado());
                documentoDTO.setArchivo(archivos);
                documentoDTO.setAutor(documento.getAutor());
                documentoDTO = documentoRepository.save(documentoDTO);
                return modelMapper.map(documentoDTO, DocumentoDTO.class);
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
            revisar = documentoRepository.findNombreDocumentoAutor(documento.getNombre(), documento.getAutor());
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
    public DocumentoDTO guardarArchivo(DocumentoDTO documento, String archivo, String nombreArchivo) {
        
        Optional<Documento> documentoDTO = null;
        Documento auxiliar = new Documento();
        List<ArchivoDTO> archivos = new ArrayList<>();
        ArchivoDTO file = new ArchivoDTO();
        file.setNombreArchivo(nombreArchivo);
        file.setUrl(archivo);
        String[] parts = archivo.split("\\.");
        String type = parts[1];
        file.setType(type);
        boolean bandera = false;
        if(documento!=null && !archivo.matches("") && !nombreArchivo.matches("")){
            documentoDTO = documentoRepository.findById(documento.getId());
            if(documentoDTO.isPresent()){
                auxiliar = documentoDTO.get();
                archivos = auxiliar.getArchivo();
                for(ArchivoDTO fil:archivos){
                    if(fil.getNombreArchivo().matches(nombreArchivo)){
                        bandera = true;
                    }
                }
                if(!bandera){
                    archivos.add(file);
                    auxiliar.setArchivo(archivos);
                    auxiliar = documentoRepository.save(auxiliar);
                    return modelMapper.map(auxiliar, DocumentoDTO.class);
                }
            }
        }
        return null;
    }

    @Override
    public DocumentoDTO eliminarArchivo(DocumentoDTO documento, String archivo) {
        
        Optional<Documento> documentoDTO = null;
        Documento auxiliar = new Documento();
        List<ArchivoDTO> archivos = new ArrayList<>();
        if(documento!=null && archivo!=""){
            documentoDTO = documentoRepository.findById(documento.getId());
            if(documentoDTO.isPresent()){
                auxiliar = documentoDTO.get();
                archivos = auxiliar.getArchivo();
                for(ArchivoDTO arc:archivos){
                    if(arc.getUrl() == archivo){
                        archivos.remove(arc);
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
    public DocumentoDTO cambiarNombreArchivo(DocumentoDTO documento, String archivo, String nombreArchivo) {
        
        Optional<Documento> documentoDTO = null;
        Documento auxiliar = new Documento();
        List<ArchivoDTO> archivos = new ArrayList<>();
        if(documento!=null && archivo!=""){
            documentoDTO = documentoRepository.findById(documento.getId());
            if(documentoDTO.isPresent()){
                auxiliar = documentoDTO.get();
                archivos = auxiliar.getArchivo();
                for(ArchivoDTO arc:archivos){
                    if(arc.getUrl() == archivo){
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
    public List<DocumentoDTO> consultarDocumento(String nombreDocumento, String autor, List<String> etiquetas) {
        
        List<Documento> auxiliar = new ArrayList<>();
        List<Documento> documentos = new ArrayList<>();
        List<DocumentoDTO> documentosDTO = new ArrayList<>();
        UsuarioDTO autorDTO = new UsuarioDTO();
        autorDTO = usuarioService.buscarUsuarioNombre(autor);
        System.out.println("consulte el autor: " + autor);
        if(!nombreDocumento.matches("")){
            auxiliar = documentoRepository.findNombreDocumento(nombreDocumento);
            if(!auxiliar.isEmpty()){
                documentos.addAll(auxiliar);
                auxiliar.clear();
                System.out.println("encontre documentos con ese nombre");
            }
            
        }
        if(autorDTO!=null){
            System.out.println("voy a buscar sus documentos");
            System.out.println("el autor es:" + autorDTO.getNombre());
            System.out.println("el usuario del autor es: "+ autorDTO.getUsuario());
            auxiliar = documentoRepository.findAutor(autorDTO.getUsuario());
            if(!auxiliar.isEmpty()){
                System.out.println("voy a añadir");
                documentos.addAll(auxiliar);
                auxiliar.clear();
            }
        }
        if(!etiquetas.isEmpty()){
           auxiliar = documentoRepository.findEtiqueta(etiquetas);
           if(!auxiliar.isEmpty()){
               documentos.addAll(auxiliar);
               auxiliar.clear();
           }
        }
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
    
    
}
