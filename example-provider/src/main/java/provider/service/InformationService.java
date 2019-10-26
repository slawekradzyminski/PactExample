package provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import provider.dto.Information;
import provider.repository.InformationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class InformationService {

    private final InformationRepository informationRepository;

    @Autowired
    public InformationService(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }

    public List<Information> getAllInformation() {
        List<Information> informations = new ArrayList<>();
        informationRepository.findAll().forEach(informations::add);
        return informations;
    }

    public List<Information> getFirstInformationByName(String name) {
        return informationRepository.findByName(name);
    }

    public void saveOrUpdate(Information information) {
        informationRepository.save(information);
    }

    public void deleteById(int id) {
        informationRepository.deleteById(id);
    }

    public void delete(Information information) {
        informationRepository.delete(information);
    }

    public void save(Information information) {
        informationRepository.save(information);
    }

}
