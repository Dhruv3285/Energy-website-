package uk.ac.city.aczm039.Energy.services;


import uk.ac.city.aczm039.Energy.dao.EnergyRepository;
import uk.ac.city.aczm039.Energy.entites.EnergyUser;
public interface RegistrationService {
    void registerUser(EnergyUser energyUser);
}
