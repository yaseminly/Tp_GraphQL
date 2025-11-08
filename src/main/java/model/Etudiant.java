package model;

import enums.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "etudiants")
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom_etudiant", nullable = false)
    private String nom;

    @Column(name = "prenom_etudiant")
    private String prenom;

    @Enumerated(EnumType.STRING)
    private Genre genre; // ✅ Enum importée correctement

    @ManyToOne
    @NotNull
    @JoinColumn(name = "centre_id")
    private Centre centre;
}
