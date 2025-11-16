package service;

import dto.CentreDTO;
import model.Centre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;
import repositories.CentreRepository;

import java.util.List;

@Service
public class CentreService {

    @Autowired
    private DtoToCentre dtoToCentre;

    @Autowired
    private CentreRepository centreRepository;

    private final Sinks.Many<Centre> sink = Sinks.many().multicast().onBackpressureBuffer();

    // 🔹 Récupérer la liste de tous les centres
    public List<Centre> getCentres() {
        return centreRepository.findAll();
    }

    // 🔹 Récupérer un centre par son ID
    public Centre getCentre(Long id) {
        return centreRepository.findById(id).orElse(null);
    }

    // 🔹 Ajouter un nouveau centre à partir du DTO
    public Centre addCentre(CentreDTO centreDTO) {
        Centre centre = new Centre();
        dtoToCentre.toCentre(centre, centreDTO);
        centreRepository.save(centre);
        sink.tryEmitNext(centre);
        return centre;
    }

    // 🔹 (Optionnel) Supprimer un centre
    public void deleteCentre(Long id) {
        centreRepository.deleteById(id);
    }

    // 🔹 (Optionnel) Mettre à jour un centre existant
    public Centre updateCentre(Long id, CentreDTO centreDTO) {
        return centreRepository.findById(id)
                .map(existingCentre -> {
                    dtoToCentre.toCentre(existingCentre, centreDTO);
                    centreRepository.save(existingCentre);
                    return existingCentre;
                })
                .orElse(null);
    }
}
