package dto;

import lombok.Data;


import java.util.List;

@Data
public class CentreDTO {
    String nom;
    String adresse;
    List<EtudiantDTO> etudiants;
}