package de.htwberlin.de.TheBayOfPirates.service;

import de.htwberlin.de.TheBayOfPirates.entity.Torrent;

import java.io.File;
import java.util.Optional;

public interface TorrentService {

    void saveTorrent(File torrentFile, String userEmail, String description) throws Exception;

    public Optional<Torrent> findByName(String name);

    public Optional<Torrent> findByTorrentID(int torrentID);

}
