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
@Table(name = "isi_annees_academiques")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AnneeAcademique extends Base {

    @Column(unique = true)
    private String libelle; // ex: "2023-2024"

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    private String statut; // EN_COURS, TERMINEE, EN_PREPARATION

}