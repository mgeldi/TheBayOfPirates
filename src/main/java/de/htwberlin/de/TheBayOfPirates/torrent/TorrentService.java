package de.htwberlin.de.TheBayOfPirates.torrent;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public interface TorrentService {

    /**
     * For setting the amount of items shown per page
     */
    public static final int PAGESIZE = 3;

    Torrent saveTorrent(File torrentFile, String userEmail, String description) throws Exception;

    Torrent saveTorrentBytes(byte[] torrentBytes, String filename, String userEmail, String description) throws Exception;

    File loadTorrent(String torrentName) throws Exception;

    Optional<Torrent> findByName(String name);

    Optional<Torrent> findByTorrentID(int torrentID);

    void removeTorrentByID(int torrentID);

    void removeTorrentByName(String name);

    Page<Torrent> getTorrentPagesBySearch(String searchTerm, int page);

}
