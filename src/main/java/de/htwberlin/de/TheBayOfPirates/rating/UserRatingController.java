package de.htwberlin.de.TheBayOfPirates.rating;

import de.htwberlin.de.TheBayOfPirates.registration.RegistrationController;
import de.htwberlin.de.TheBayOfPirates.torrent.TorrentRepository;
import de.htwberlin.de.TheBayOfPirates.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private TorrentRepository torrentRepository;

    @PostMapping(value = "/torrent/rate")
    public ModelAndView rateTorrent(@RequestParam("torrentid") int id, Principal principal, @RequestParam String rating) {
        double ratingAsDouble = Double.parseDouble(rating);
        ModelAndView modelAndView = new ModelAndView();
        try {
            userRatingService.giveRatingToTorrent(principal.getName(), id, ratingAsDouble);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        RegistrationController.handleSecurity(modelAndView, principal, userService);
        modelAndView.addObject("successMessage", "Torrent rated successfully!");
        modelAndView.setViewName("redirect:/torrent/id=" + id);
        return modelAndView;
    }
}
