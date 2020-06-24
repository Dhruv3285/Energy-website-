package uk.ac.city.aczm039.Energy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.ac.city.aczm039.Energy.dao.EnergyRepository;
import uk.ac.city.aczm039.Energy.dao.HistoryRepository;
import uk.ac.city.aczm039.Energy.entites.CollatedUser;
import uk.ac.city.aczm039.Energy.entites.LoginHistory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {

    private EnergyRepository energyRepository;

    private HistoryRepository historyRepository;

    @Autowired
    public ProfileServiceImpl(EnergyRepository energyRepository,

                              HistoryRepository historyRepository){
        this.energyRepository = energyRepository;

        this.historyRepository = historyRepository;
    }
    @Override
    public CollatedUser getProfile(String email){
        CollatedUser user=new CollatedUser();
        user.setEnergyUser(energyRepository.findById(email).orElseThrow(()-> new UsernameNotFoundException("No matching user found.")));

        List<LoginHistory> history = historyRepository.findAll()
                .stream().filter(loginHistory -> loginHistory.getEmail().equals(email))
                .collect(Collectors.toList());
        user.setLogins(history);
        return user;



    }



}
