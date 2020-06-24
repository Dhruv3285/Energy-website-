package uk.ac.city.aczm039.Energy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.city.aczm039.Energy.entites.EnergyUser;
import uk.ac.city.aczm039.Energy.services.RegistrationService;

@Controller
public class RegistrationController {

    /**
     * The registration service which is used to save user information to the
     * database.
     */
    private RegistrationService registrationService;

    /**
     * Constructor based dependency injection of the registration service
     *
     * @param registrationService
     */
    @Autowired
    public RegistrationController (RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    /**
     * Controller method for responding to a GET request to the /register endpoint
     *
     * @return ModelAndView a view created from register.html backed by an OperaUser object
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView showRegistation(){
        EnergyUser energyUser = new EnergyUser();
        return new ModelAndView("index", "energyUser", energyUser);
    }

    /**
     * Controller method to handle a POST request to the /register endpoint
     *
     * @param energyUser an object containing the data from the registration form
     * @return String a redirect to the profile page (which requries the user to login)
     */
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String processRegistation(EnergyUser energyUser){
        energyUser.setEnabled(Boolean.TRUE);
        energyUser.setAuthoritiy("ROLE_USER");
        registrationService.registerUser(energyUser);
        return "redirect:/index";
    }

}
