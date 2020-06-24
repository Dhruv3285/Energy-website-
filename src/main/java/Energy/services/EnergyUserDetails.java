package uk.ac.city.aczm039.Energy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.ac.city.aczm039.Energy.dao.HistoryRepository;
import uk.ac.city.aczm039.Energy.dao.EnergyRepository;
import uk.ac.city.aczm039.Energy.entites.LoginHistory;
import uk.ac.city.aczm039.Energy.entites.EnergyUser;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class EnergyUserDetails implements UserDetailsService {

    private EnergyRepository energyRepository;

    private HistoryRepository historyRepository;

    @Autowired
    public EnergyUserDetails(EnergyRepository energyRepository, HistoryRepository historyRepository){
        this.energyRepository=energyRepository;
        this.historyRepository=historyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        EnergyUser energyUser = energyRepository.findById(s).orElseThrow(()-> new UsernameNotFoundException("No matching user."));
        //if the first line does not throw the exception the login history
        //object will be constructed and saved to the database.
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setEmail(energyUser.getEmail());
        loginHistory.setTime(LocalDateTime.now());
        historyRepository.save(loginHistory);
        //and a UserDetails object will be constructed and returned.
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(energyUser.getAuthoritiy()));
        return new User
                (energyUser.getEmail(), energyUser.getPassword(), energyUser.getEnabled(),
                        true, true, true, authorities);

    }

}
