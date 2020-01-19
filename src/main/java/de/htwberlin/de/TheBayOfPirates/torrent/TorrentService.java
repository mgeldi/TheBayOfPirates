package de.htwberlin.de.TheBayOfPirates.torrent;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TorrentService {

    /**
     * For setting the amount of items shown per page
     */
    int PAGESIZE = 3;

    int MAX_FILE_SIZE_IN_KILO_BYTES = 500;

    Torrent saveTorrentBytes(byte[] torrentBytes, String filename, String userEmail, String description) throws Exception;

    Optional<Torrent> findByName(String name);

    Optional<Torrent> findByTorrentID(int torrentID);

    void removeTorrentByID(int torrentID) throws Exception;

    void removeTorrentByName(String name) throws Exception;

    Page<Torrent> getTorrentPagesBySearch(String searchTerm, int page);

}
