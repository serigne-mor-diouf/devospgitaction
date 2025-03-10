package com.isi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isi.model.base.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "isi_classes")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Classe extends Base {
    private String nom;
    private String niveau;
    private String anneeScolaire;
    @JsonIgnore
    @OneToMany(mappedBy = "classe")
    private Collection<Cours> cours;
}
