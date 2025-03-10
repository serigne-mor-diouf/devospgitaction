package com.isi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscriptionDTO {
    private Long etudiantId;
    private String anneeAcademiqueId;
    private String dateInscription;
    private String statut;
    private Double fraisInscription;
    private Double fraisScolarite;
    private Double montantVerse;
    private String observation;

    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }

    public String getAnneeAcademiqueId() {
        return anneeAcademiqueId;
    }

    public void setAnneeAcademiqueId(String anneeAcademiqueId) {
        this.anneeAcademiqueId = anneeAcademiqueId;
    }

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
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