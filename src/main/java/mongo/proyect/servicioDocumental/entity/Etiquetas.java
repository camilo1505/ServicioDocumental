/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo.proyect.servicioDocumental.entity;

/**
 *
 * @author junpa
 */
public class Etiquetas {
    private String id;
    private int value;

    private void setId(String name)
    {
        this.id = name;
    }

    private void setValue(int count)
    {
        this.value = count;
    }

    public String getId()
    {
        return id;
    }

    public int getValue()
    {
        return value;
    }
}
