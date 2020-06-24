package uk.ac.city.aczm039.Energy.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.city.aczm039.Energy.entites.EnergyUser;
@Repository
public interface EnergyRepository extends JpaRepository<EnergyUser,String> {
}
