/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author junpa
 */
@Configuration
public class Configuracion {
    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
