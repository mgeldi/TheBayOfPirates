package de.htwberlin.de.TheBayOfPirates.torrent;

import de.htwberlin.de.TheBayOfPirates.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TorrentController {

    @Autowired
    TorrentService torrentService;

    public TorrentController(TorrentService torrentService) {
        this.torrentService = torrentService;
    }

    @GetMapping(value = "/torrent/name={name:.+}")
    public String getTorrent(Model model, @PathVariable String name, Principal principal, ModelMap modelMap) {
        modelMap.addAttribute("principal", principal);
        modelMap.addAttribute("user", new User());
        System.out.println(name);
        Optional<Torrent> torrent = torrentService.findByName(name);
        if (torrent.isPresent()) {
            model.addAttribute("torrent", torrent.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Torrent not found!");
        }

        return "showtorrent";
    }

    @GetMapping(value = "/torrent/id={id}")
    public String getTorrent(Model model, @PathVariable int id, Principal principal, ModelMap modelMap) {
        modelMap.addAttribute("principal", principal);
        modelMap.addAttribute("user", new User());
        Optional<Torrent> torrent = torrentService.findByTorrentID(id);
        if (torrent.isPresent()) {
            model.addAttribute("torrent", torrent.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Torrent not found!");
        }

        return "showtorrent";
    }

    @GetMapping(value = "/torrent/upload")
    public ModelAndView postTorrent(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("principal", principal);
        modelMap.addAttribute("user", new User());
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
                e.printStackTrace();
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

    @PostMapping(value = "/torrent/delete")
    public ModelAndView deleteTorrent(@RequestParam(name = "filename") String filename, Principal principal) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Torrent> torrent = torrentService.findByName(filename);
        if (torrent.isPresent()) {
            if(principal.getName().equals(torrent.get().getUser().getEmail())){
                torrentService.removeTorrentByName(filename);
                modelAndView.setViewName("redirect:/torrent/upload");
                return modelAndView;
            } else {
                throw new Exception("Not the original uploader!");
            }
        } else{
            throw new Exception("Torrent not found");
        }
    }

    @GetMapping(value = "/torrent/search={name}/page={page}")
    public ModelAndView searchForTorrentByName(@PathVariable String name, @PathVariable @Min(1) int page, Principal principal, ModelMap modelMap) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelMap.addAttribute("user", new User());
        modelMap.addAttribute("principal", principal);
        Page<Torrent> torrentPage = torrentService.getTorrentPagesBySearch(name, page -1); //-1 because it starts at 0
        modelMap.addAttribute("totalPages", torrentPage.getTotalPages());
        System.out.println(torrentPage.getTotalPages());
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("isFirstPage", page == 1);
        modelMap.addAttribute("isLastPage", page == torrentPage.getTotalPages());
        modelMap.addAttribute("searchURL", "/torrent/search=" + name + "/page=");
        modelMap.addAttribute("torrentPage", torrentPage);

        int totalPages = torrentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }

        modelAndView.setViewName("search");
        return modelAndView;
    }
}
