package uk.ac.city.aczm039.Energy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class EnergyController {
   @RequestMapping(value="/index")
    public ModelAndView showHome(){return new ModelAndView("index");}

    @RequestMapping(value="/solar")
    public ModelAndView showSolar(){return new ModelAndView("solar");}

    @RequestMapping(value="/wind")
    public ModelAndView showWind(){return new ModelAndView("wind");}

    @RequestMapping(value="/hydro")
    public ModelAndView showHydro(){return new ModelAndView("hydro");}

    @RequestMapping(value="/enter")
   public ModelAndView showEnter(){return new ModelAndView("enter");}

    @RequestMapping(value="/surprise")
    public ModelAndView showSurprise(){return new ModelAndView("surprise");}


}
