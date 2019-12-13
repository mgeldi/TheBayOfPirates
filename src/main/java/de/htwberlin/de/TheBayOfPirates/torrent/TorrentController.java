package de.htwberlin.de.TheBayOfPirates.torrent;

import de.htwberlin.de.TheBayOfPirates.entity.Torrent;
import de.htwberlin.de.TheBayOfPirates.service.TorrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.security.Principal;
import java.util.Optional;

@Controller
public class TorrentController {

    @Autowired
    TorrentService torrentService;

    @GetMapping(value = "/torrent/name={name}}")
    public String getTorrent(Model model, @PathVariable String name) {
        Optional<Torrent> torrent = torrentService.findByName(name);
        if (torrent.isPresent()) {
            model.addAttribute("torrent", torrent.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Torrent not found!");
        }

        return "showtorrent";
    }

    @GetMapping(value = "/torrent/id={id}}")
    public String getTorrent(Model model, @PathVariable int id) {
        Optional<Torrent> torrent = torrentService.findByTorrentID(id);
        if (torrent.isPresent()) {
            model.addAttribute("torrent", torrent.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Torrent not found!");
        }

        return "showtorrent";
    }

    @GetMapping(value = "/torrent/upload")
    public ModelAndView postTorrent() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("description", "");
        modelAndView.setViewName("torrentUpload");
        return modelAndView;
    }

    @PostMapping(value = "/torrent/upload")
    public ModelAndView postTorrent(ModelMap modelMap, BindingResult bindingResult, File file,
                                    Principal principal, Torrent torrent) {
        ModelAndView modelAndView = new ModelAndView();
        String actualName = file.getName().substring(0, file.getName().length() - ".torrent".length());
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("sucessMessage", "Please correct the errors");
            modelMap.addAttribute("bindingResult", bindingResult);
        } else if (torrentService.findByName(actualName).isPresent()) {
            modelAndView.addObject("successMessage",
                    "Torrent with that name already exists!");
            System.out.println("Torrent already exists!");
        } else {
            try {
                Torrent savedTorrent = torrentService.saveTorrent(file, principal.getName(), torrent.getDescription());
                modelAndView.addObject("successMessage", "Upload succeeded!");
                modelAndView.setViewName("redirect:/torrent/id=" + savedTorrent.getTorrentID());
            } catch (Exception e) {
                e.printStackTrace();
                modelAndView.addObject("successMessage", "Upload failed!");
            }
        }

        return modelAndView;
    }
}
