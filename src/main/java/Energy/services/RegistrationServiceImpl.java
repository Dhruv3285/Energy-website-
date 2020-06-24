package uk.ac.city.aczm039.Energy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.city.aczm039.Energy.dao.EnergyRepository;
import uk.ac.city.aczm039.Energy.entites.EnergyUser;
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private EnergyRepository energyRepository;

    @Autowired
    public RegistrationServiceImpl(EnergyRepository energyRepository){this.energyRepository=energyRepository;}

    @Override
    public void registerUser(EnergyUser energyUser){energyRepository.save(energyUser);}


}
