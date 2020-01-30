package de.htwberlin.de.TheBayOfPirates.rating;

import de.htwberlin.de.TheBayOfPirates.torrent.Torrent;
import de.htwberlin.de.TheBayOfPirates.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRatingRepository extends JpaRepository<UserRating, Integer> {
    List<UserRating> findAllByTorrentID(Torrent torrent);

    List<UserRating> findAllByUserID(User user);

    void removeAllByTorrentID(Torrent torrent);

    Optional<UserRating> findByUserID(User user);

    Optional<UserRating> findByUserIDAndTorrentID(User user, Torrent torrent);
}
