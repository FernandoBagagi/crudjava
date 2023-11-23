package br.com.ferdbgg.entidades;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Departamento implements Serializable {

    private Integer id;
    private String nome;
    
}
