package uk.ac.city.aczm039.Energy.services;
import uk.ac.city.aczm039.Energy.entites.CollatedUser;

public interface ProfileService {
    CollatedUser getProfile(String email);
}
