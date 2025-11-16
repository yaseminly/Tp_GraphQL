package service;

import model.DtoToEtudiant;
import dto.EtudiantDTO;
import model.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;
import repositories.EtudiantRepository;

import java.util.List;

@Service
public class EtudiantService {
    @Autowired
    DtoToEtudiant dtoToEtudiant;
    @Autowired
    EtudiantRepository etudiantRepository;
    private final Sinks.Many<Etudiant> sink = Sinks.many().multicast().onBackpressureBuffer();

    public List<Etudiant> getStudents() {
        return etudiantRepository.findAll();
    }

    public Etudiant getEtudiant(Long id) {
        return etudiantRepository.findById(id).orElse(null);
    }

    public Etudiant addEtudiant(EtudiantDTO etudiantDTO) {
        Etudiant etudiant = new Etudiant();
        dtoToEtudiant.toEtudiant(etudiant, etudiantDTO);
        etudiantRepository.save(etudiant);
        sink.tryEmitNext(etudiant);
        return etudiant;
    }
}