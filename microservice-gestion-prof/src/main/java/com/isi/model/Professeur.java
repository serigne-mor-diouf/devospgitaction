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
import java.util.List;

    @Entity
    @Table(name = "isi_professeurs")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public class Professeur extends Base {

        @Column(nullable = false)
        private String nom;

        @Column(nullable = false)
        private String prenom;

        @Column(unique = true, nullable = false)
        private String email;

        private String telephone;

        private String grade;

        @JsonIgnore
        @OneToMany(mappedBy = "professeur")
        private List<Cours> cours = new ArrayList<>();

        @ManyToOne
        @JoinColumn(name = "departement_id")
        private Departement departement;

        @ManyToMany
        @JoinTable(
                name = "isi_professeur_specialites",
                joinColumns = @JoinColumn(name = "professeur_id"),
                inverseJoinColumns = @JoinColumn(name = "specialite_id")
        )
        @JsonIgnoreProperties({"professeurs"})
        private List<Specialite> specialites = new ArrayList<>();

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public List<Cours> getCours() {
            return cours;
        }

        public void setCours(List<Cours> cours) {
            this.cours = cours;
        }

        public Departement getDepartement() {
            return departement;
        }

        public void setDepartement(Departement departement) {
            this.departement = departement;
        }

        public List<Specialite> getSpecialites() {
            return specialites;
        }

        public void setSpecialites(List<Specialite> specialites) {
            this.specialites = specialites;
        }
    }
