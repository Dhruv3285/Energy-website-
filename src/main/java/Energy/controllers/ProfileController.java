package uk.ac.city.aczm039.Energy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.city.aczm039.Energy.entites.CollatedUser;
import uk.ac.city.aczm039.Energy.services.ProfileService;
@Controller
public class ProfileController {
    private ProfileService profileService;
    @Autowired
    public ProfileController(ProfileService profileService){this.profileService=profileService;}

    @RequestMapping(value="/profile")
    public ModelAndView showProfile(){
        //Retrieves the email address of the currently logged in user.
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CollatedUser user = profileService.getProfile(email);
        return new ModelAndView("profile", "collatedUser", user);
    }

}
