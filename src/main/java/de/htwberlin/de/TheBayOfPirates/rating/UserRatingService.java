package de.htwberlin.de.TheBayOfPirates.rating;

import org.springframework.stereotype.Service;

@Service
public interface UserRatingService {
    void getRatingOfTorrentByID(int torrentID) throws Exception;

    void giveRatingToTorrent(String email, int torrentID, double rating) throws Exception;

    void removeAllRatingsOfTorrent(int torrentID) throws Exception;
}
