package de.htwberlin.de.TheBayOfPirates.repository;

import de.htwberlin.de.TheBayOfPirates.entity.Torrent;
import de.htwberlin.de.TheBayOfPirates.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Valid;
import java.util.Optional;

public interface TorrentRepository extends JpaRepository<Torrent, Integer> {

    public Optional<Torrent> findByName(String name);

    public Optional<Torrent> findByTorrentID(int torrentID);

}
