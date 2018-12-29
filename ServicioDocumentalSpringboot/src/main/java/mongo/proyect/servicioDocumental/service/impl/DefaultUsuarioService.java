/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.service.impl;

import java.util.Optional;
import mongo.proyect.servicioDocumental.dto.UsuarioDTO;
import mongo.proyect.servicioDocumental.entity.Usuario;
import mongo.proyect.servicioDocumental.repository.UsuarioRepository;
import mongo.proyect.servicioDocumental.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author junpa
 */
@Service
public class DefaultUsuarioService implements UsuarioService{
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public UsuarioDTO buscarUsuario(String Nombre, String Password) {
       
        Optional<Usuario> usuario = null;
        Usuario auxiliar = null;
        UsuarioDTO usuarioDTO = null;
        usuario = usuarioRepository.findByUsuario(Nombre);
        if(usuario.isPresent()){
            auxiliar = usuario.get();
            if(auxiliar.getPassword()==Password){
                usuarioDTO = modelMapper.map(auxiliar, UsuarioDTO.class);
            }
        }
        return usuarioDTO;
    }

    @Override
    public String tipoUsuario(String Usuario) {
        Optional<Usuario> usuario = null;
        UsuarioDTO usuarioDTO = null;
        Usuario auxiliar = null;
        usuario = usuarioRepository.findByUsuario(Usuario);
        if(usuario.isPresent()){
            auxiliar = usuario.get();
            usuarioDTO = modelMapper.map(auxiliar,UsuarioDTO.class);
            return usuarioDTO.getTipoUsuario();
        }
        return null;
    }

    @Override
    public UsuarioDTO crearUsuario(String Nombre, String Usuario, String Password, String tipoUsuario) {
        Optional<Usuario> usuario = null;
        UsuarioDTO usuarioDTO = null;
        Usuario auxiliar = null;
        usuario = usuarioRepository.findByUsuario(Nombre);
        if(!usuario.isPresent()){
            auxiliar.setNombre(Nombre);
            auxiliar.setPassword(Password);
            auxiliar.setUsuario(Usuario);
            auxiliar.setTipoUsuario(tipoUsuario);
            auxiliar = usuarioRepository.save(auxiliar);
            return modelMapper.map(auxiliar, UsuarioDTO.class);
        }
        return null;
    }

    @Override
    public String cambiarTipoUsuario(String Usuario, String Password) {
        Optional<Usuario> usuario = null;
        UsuarioDTO usuarioDTO = null;
        Usuario auxiliar = null;
        usuario = usuarioRepository.findByUsuario(Usuario);
        if(usuario.isPresent()){
           auxiliar = usuario.get();
           if(auxiliar.getPassword()==Password){
                auxiliar.setTipoUsuario("inactivo");
                auxiliar = usuarioRepository.save(auxiliar);
                return "inactivo";
           }
        }
        return "no se pudo";
    }

    @Override
    public UsuarioDTO cambiarPasswordUsuario(String Usuario, String Password) {
        Optional<Usuario> usuario = null;
        UsuarioDTO usuarioDTO = null;
        Usuario auxiliar = null;
        usuario = usuarioRepository.findByUsuario(Usuario);
        if(!usuario.isPresent()){
            auxiliar.setPassword(Password);
            auxiliar = usuarioRepository.save(auxiliar);
            return modelMapper.map(auxiliar, UsuarioDTO.class);
        }
        return null;
    }
}
