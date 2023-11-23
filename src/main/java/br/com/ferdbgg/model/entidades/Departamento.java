package br.com.ferdbgg.model.entidades;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    
}
