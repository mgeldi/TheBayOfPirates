package de.htwberlin.de.TheBayOfPirates.torrent;

import de.htwberlin.de.TheBayOfPirates.service.TorrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TorrentController {

    @Autowired
    TorrentService torrentService;
}
