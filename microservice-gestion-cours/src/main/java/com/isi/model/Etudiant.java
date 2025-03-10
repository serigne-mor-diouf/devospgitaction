package com.isi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isi.model.base.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "isi_etudiants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Etudiant extends Base {

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(unique = true, nullable = false)
    private String matricule;

    @Column(unique = true)
    private String email;

    private String telephone;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    private String adresse;

    @Column(name = "lieu_naissance")
    private String lieuNaissance;

    private String nationalite;

    private String genre;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    @JsonIgnoreProperties({"etudiants"})
    private Classe classe;

}