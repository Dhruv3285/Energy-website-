package uk.ac.city.aczm039.Energy.entites;


import java.util.List;
public class CollatedUser {
    private EnergyUser energyUser;

    private List<LoginHistory>logins;

    public EnergyUser getEnergyUser(){return energyUser;}
    public void setEnergyUser(EnergyUser energyUser) {this.energyUser=energyUser;}

    public List<LoginHistory> getLogins(){return logins;}
    public void setLogins(List <LoginHistory> logins){this.logins=logins;}



}
