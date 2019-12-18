package de.htwberlin.de.TheBayOfPirates.torrent;

import de.htwberlin.de.TheBayOfPirates.entity.Torrent;
import de.htwberlin.de.TheBayOfPirates.service.TorrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.Iterator;
import java.util.Optional;

@Controller
public class TorrentController {

    @Autowired
    TorrentService torrentService;

    public TorrentController(TorrentService torrentService) {
        this.torrentService = torrentService;
    }

    @GetMapping(value = "/torrent/name={name}")
    public String getTorrent(Model model, @PathVariable String name) {
        Optional<Torrent> torrent = torrentService.findByName(name);
        if (torrent.isPresent()) {
            model.addAttribute("torrent", torrent.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Torrent not found!");
        }

        return "showtorrent";
    }

    @GetMapping(value = "/torrent/id={id}")
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
    public ModelAndView postTorrent(HttpServletRequest request, @RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes, ModelMap modelMap,
                                    Principal principal, @RequestParam("description") String description) {
        ModelAndView modelAndView = new ModelAndView();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = null;

        Iterator<String> iterator = multipartRequest.getFileNames();

        while (iterator.hasNext()) {
            String key = iterator.next();
            // create multipartFile array if you upload multiple files
            multipartFile = (MultipartFile) multipartRequest.getFile(key);
        }
        String fileName = multipartFile.getOriginalFilename();
        System.out.println("File to be uploaded was called: " + fileName);
        String actualName = fileName.substring(0, fileName.length() - ".torrent".length());
        if (torrentService.findByName(actualName).isPresent()) {
            modelAndView.addObject("error",
                    "Torrent with that name already exists!");
            System.out.println("Torrent already exists!");
            modelAndView.setViewName("redirect:/torrent/upload");
        } else {
            try {
                Torrent savedTorrent = torrentService.saveTorrentBytes(file.getBytes(), fileName,
                        principal.getName(), description);
                modelAndView.addObject("successMessage", "Upload succeeded!");
                modelAndView.setViewName("redirect:/torrent/id=" + savedTorrent.getTorrentID());
            } catch (Exception e) {
                modelAndView.addObject("error", "Upload failed!");
                modelAndView.setViewName("redirect:/torrent/upload");
            }
        }

        return modelAndView;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        System.out.println(name + " parameter is missing");

        return "/torrent/upload";
    }

    @GetMapping(value = "/torrent/download")
    public ResponseEntity<InputStreamResource> downloadFile1(@RequestParam(name = "filename") String filename,
                                                             ModelAndView modelAndView) throws Exception {
        Optional<Torrent> torrent = torrentService.findByName(filename);
        if (!torrent.isPresent()) {
            throw new Exception("Torrent not found! Shouldn't have happened!");
        }
        byte[] torrentBytes = torrent.get().getTorrent();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(torrentBytes));
        String actualName = filename + ".torrent";
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + actualName)
                // Contet-Length
                .contentLength(torrentBytes.length) //
                .body(resource);
    }
}
