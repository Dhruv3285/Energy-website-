package uk.ac.city.aczm039.Energy.entites;

import javax.security.auth.Subject;
import java.nio.file.attribute.UserPrincipal;

public class EnergyUserPrincipal implements UserPrincipal{
    private EnergyUser energyUser;
    public EnergyUserPrincipal(EnergyUser energyUser){this.energyUser=energyUser;}
    @Override
    public String getName(){return energyUser.getEmail();}
}
