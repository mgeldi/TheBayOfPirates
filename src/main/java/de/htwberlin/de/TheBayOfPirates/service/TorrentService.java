package de.htwberlin.de.TheBayOfPirates.service;

import de.htwberlin.de.TheBayOfPirates.entity.Torrent;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
public interface TorrentService {

    Torrent saveTorrent(File torrentFile, String userEmail, String description) throws Exception;

    Torrent saveTorrentBytes(byte[] torrentBytes, String filename, String userEmail, String description) throws Exception;

    File loadTorrent(String torrentName) throws Exception;

    public Optional<Torrent> findByName(String name);

    public Optional<Torrent> findByTorrentID(int torrentID);

}
