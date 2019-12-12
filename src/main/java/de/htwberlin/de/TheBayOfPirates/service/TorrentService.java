package de.htwberlin.de.TheBayOfPirates.service;

import de.htwberlin.de.TheBayOfPirates.entity.Torrent;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
public interface TorrentService {

    void saveTorrent(File torrentFile, String userEmail, String description) throws Exception;

    File loadTorrent(String torrentName) throws Exception;

    public Optional<Torrent> findByName(String name);

    public Optional<Torrent> findByTorrentID(int torrentID);

}
