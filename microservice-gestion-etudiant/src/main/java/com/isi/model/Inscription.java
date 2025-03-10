package com.isi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isi.model.base.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "isi_inscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Inscription extends Base {

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    @JsonIgnoreProperties({"inscriptions", "notes"})
    private Etudiant etudiant;

    @Column(name = "date_inscription")
    private LocalDate dateInscription;

    private String statut; // EN_COURS, VALIDEE, ANNULEE

    @Column(name = "frais_inscription")
    private Double fraisInscription;

    @Column(name = "frais_scolarite")
    private Double fraisScolarite;

    @Column(name = "montant_verse")
    private Double montantVerse;

    private String observation;

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Double getFraisInscription() {
        return fraisInscription;
    }

    public void setFraisInscription(Double fraisInscription) {
        this.fraisInscription = fraisInscription;
    }

    public Double getFraisScolarite() {
        return fraisScolarite;
    }

    public void setFraisScolarite(Double fraisScolarite) {
        this.fraisScolarite = fraisScolarite;
    }

    public Double getMontantVerse() {
        return montantVerse;
    }

    public void setMontantVerse(Double montantVerse) {
        this.montantVerse = montantVerse;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}