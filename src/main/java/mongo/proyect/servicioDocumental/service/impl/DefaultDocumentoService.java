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
import mongo.proyect.servicioDocumental.entity.Documento;
import mongo.proyect.servicioDocumental.repository.DocumentoRepository;
import mongo.proyect.servicioDocumental.service.DocumentoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
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
    private GridFsOperations gridOperations;
    
    @Override
    public DocumentoDTO crearDocumento(DocumentoDTO documento) {
        Documento documentoDTO = new Documento();
        if(documento!=null){
            documentoDTO.setNombre(documento.getNombre());
            documentoDTO.setEtiquetas(documento.getEtiquetas());
            documentoDTO.setDescripcion(documento.getDescripcion());
            documentoDTO.setDescripcion(documento.getDescripcion());
            documentoDTO.setEstado(documento.getEstado());
            documentoDTO.setArchivo(documento.getArchivo());
            documentoDTO.setAutor(documento.getAutor());
            documentoDTO = documentoRepository.save(documentoDTO);
            return modelMapper.map(documentoDTO, DocumentoDTO.class);
        }
        return null;
    }

    @Override
    public DocumentoDTO cambiarNombreDocumento(DocumentoDTO documento) {
        Optional<Documento> documentoDTO = null;
        Documento auxiliar = new Documento();
        if(documento!=null){
            documentoDTO = documentoRepository.findById(documento.getId());
            if(documentoDTO.isPresent()){
                auxiliar = documentoDTO.get();
                auxiliar.setNombre(documento.getNombre());
                auxiliar = documentoRepository.save(auxiliar);
                return modelMapper.map(auxiliar, DocumentoDTO.class);
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
        String[] parts = archivo.split(".");
        String type = parts[1];
        file.setType(type);
        if(documento!=null && archivo!="" && nombreArchivo!=""){
            documentoDTO = documentoRepository.findById(documento.getId());
            if(documentoDTO.isPresent()){
                auxiliar = documentoDTO.get();
                archivos = auxiliar.getArchivo();
                archivos.add(file);
                auxiliar.setArchivo(archivos);
                auxiliar = documentoRepository.save(auxiliar);
                return modelMapper.map(auxiliar, DocumentoDTO.class);
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
    
    
}
