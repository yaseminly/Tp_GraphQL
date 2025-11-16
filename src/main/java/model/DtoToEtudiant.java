package model;

import dto.EtudiantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repositories.CentreRepository;

@Component
public class DtoToEtudiant {

    private final CentreRepository centreRepository;

    @Autowired
    public DtoToEtudiant(CentreRepository centreRepository) {
        this.centreRepository = centreRepository;
    }

    public void toEtudiant(Etudiant etudiant, EtudiantDTO etudiantDTO) {
        etudiant.setNom(etudiantDTO.nom());
        etudiant.setPrenom(etudiantDTO.prenom());
        etudiant.setGenre(etudiantDTO.genre());

        // 🔹 Associer le centre à partir de l’ID présent dans le DTO
        if (etudiantDTO.centreId() != null) {
            Centre centre = centreRepository.findById(etudiantDTO.centreId()).orElse(null);
            etudiant.setCentre(centre);
        } else {
            etudiant.setCentre(null);
        }
    }
}
