package controllers;

import dto.EtudiantDTO;
import model.Centre;
import model.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import repositories.CentreRepository;
import repositories.EtudiantRepository;

import java.util.List;

@Controller
public class EtudiantController {
    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private CentreRepository centreRepository;

    @QueryMapping
    public List<Etudiant> listEtudiants() {
        return etudiantRepository.findAll();
    }

    @QueryMapping
    public Etudiant getEtudiantById(@Argument Long id) {
        return etudiantRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("etudiant %d non trouvé ", id))
        );
    }

    @QueryMapping
    public List<Centre> centres() {
        return centreRepository.findAll();
    }

    @QueryMapping
    public Centre getCentreById(@Argument Long id) {
        return centreRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Centre %s non trouvé ", id))
        );
    }

    @MutationMapping
    public Etudiant addEtudiant(@Argument EtudiantDTO etudiant) {
        Centre centre = centreRepository.findById(etudiant.centreId()).orElse(null);
        Etudiant et = new Etudiant();
        et.setNom(etudiant.nom());
        et.setPrenom(etudiant.prenom());
        et.setGenre(etudiant.genre());
        et.setCentre(centre);
        return etudiantRepository.save(et);
    }
}