package com.isi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isi.model.base.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "isi_classes")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Classe extends Base {
    private String nom;

    private String anneeScolaire;

    private String niveau;

    private String filiere;

    @Column(name = "capacite_max")
    private Integer capaciteMax;

    @OneToMany(mappedBy = "classe")
    @JsonIgnoreProperties({"classe"})
    private List<Etudiant> etudiants = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "isi_classe_cours",
            joinColumns = @JoinColumn(name = "classe_id"),
            inverseJoinColumns = @JoinColumn(name = "cours_id")
    )
    @JsonIgnoreProperties({"classes"})
    private List<Cours> cours = new ArrayList<>();
}
