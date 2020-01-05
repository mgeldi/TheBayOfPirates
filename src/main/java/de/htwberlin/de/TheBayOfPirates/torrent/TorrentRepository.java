package de.htwberlin.de.TheBayOfPirates.torrent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TorrentRepository extends JpaRepository<Torrent, Integer> {

    public Optional<Torrent> findByName(String name);

    public Optional<Torrent> findByTorrentID(int torrentID);

}
