package de.htwberlin.de.TheBayOfPirates.torrent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TorrentRepository extends JpaRepository<Torrent, Integer> {

    Optional<Torrent> findByName(String name);

    Optional<Torrent> findByTorrentID(int torrentID);

    //for getting torrents as pages
    Page<Torrent> findAllByNameContaining(String searchString, Pageable pageable);

}
