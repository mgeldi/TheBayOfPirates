package de.htwberlin.de.TheBayOfPirates.rating;

import de.htwberlin.de.TheBayOfPirates.registration.RegistrationController;
import de.htwberlin.de.TheBayOfPirates.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserRatingController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRatingService userRatingService;

    @PostMapping(value = "/torrent/rate")
    public ModelAndView rateTorrent(@RequestParam("torrentid") int id, Principal principal, @RequestParam("rating") String rating) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        RegistrationController.handleSecurity(modelAndView, principal, userService);
        double ratingAsDouble = 0.0;
        try {
            ratingAsDouble = Double.parseDouble(rating);
        } catch (Exception e) {
            modelAndView.addObject("error", "The value of rating was not correct!");
            modelAndView.setViewName("index");
            return modelAndView;
        }
        try {
            userRatingService.giveRatingToTorrent(principal.getName(), id, ratingAsDouble);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        modelAndView.addObject("successMessage", "Torrent rated successfully!");
        modelAndView.setViewName("redirect:/torrent/id=" + id);
        return modelAndView;
    }
}
