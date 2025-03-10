package com.isi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartementDTO {
    private String nom;
    private String description;
    private Long chefDepartementId;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getChefDepartementId() {
        return chefDepartementId;
    }

    public void setChefDepartementId(Long chefDepartementId) {
        this.chefDepartementId = chefDepartementId;
    }
}