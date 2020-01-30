package de.htwberlin.de.TheBayOfPirates.rating;

import de.htwberlin.de.TheBayOfPirates.torrent.Torrent;
import de.htwberlin.de.TheBayOfPirates.torrent.TorrentRepository;
import de.htwberlin.de.TheBayOfPirates.user.User;
import de.htwberlin.de.TheBayOfPirates.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserRatingServiceImpl implements UserRatingService {

    @Autowired
    private TorrentRepository torrentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;

    @Override
    public void getRatingOfTorrentByID(int torrentID) throws Exception {
        Optional<Torrent> torrent = torrentRepository.findByTorrentID(torrentID);
        if (!torrent.isPresent()) {
            throw new Exception("Torrent does not exist!");
        }
        List<UserRating> ratings = userRatingRepository.findAllByTorrentID(torrent.get());
        if (ratings.size() == 0) {
            torrent.get().setRating(0.0);
            System.out.println("0 ratings");
        } else {
            int numberOfRatings = ratings.size();
            double rating = 0.0;
            for (UserRating userRating : ratings) {
                rating += userRating.getRating();
            }
            rating /= numberOfRatings;
            torrent.get().setRating(rating);
        }
        torrentRepository.saveAndFlush(torrent.get());
    }

    @Override
    public void giveRatingToTorrent(String email, int torrentID, double rating) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        Optional<Torrent> torrent = torrentRepository.findByTorrentID(torrentID);
        if (!user.isPresent()) {
            throw new Exception("User not found! Unexpected Error!");
        }
        if (!torrent.isPresent()) {
            throw new Exception("Torrent not found! Unexpected Error!");
        }
        Optional<UserRating> loadedUserRating = userRatingRepository.findByUserIDAndTorrentID(user.get(), torrent.get());
        if (loadedUserRating.isPresent()) {
            loadedUserRating.get().setRating(rating);
            System.out.println("loadedRating " + loadedUserRating.get().getRating());
            userRatingRepository.saveAndFlush(loadedUserRating.get());
        } else {
            UserRating userRating = new UserRating(torrent.get(), user.get());
            userRating.setRating(rating);
            userRatingRepository.saveAndFlush(userRating);
        }
        getRatingOfTorrentByID(torrentID);
    }

    @Transactional
    @Override
    public void removeAllRatingsOfTorrent(int torrentID) throws Exception {
        Optional<Torrent> torrent = torrentRepository.findByTorrentID(torrentID);
        if (torrent.isPresent()) {
            userRatingRepository.removeAllByTorrentID(torrent.get());
        } else {
            throw new Exception("Torrent not found!");
        }
    }

    @Override
    public Double getPreviousUserRatingOfTorrent(int torrentID, String email) throws Exception {
        Optional<Torrent> torrent = torrentRepository.findByTorrentID(torrentID);
        Optional<User> user = userRepository.findByEmail(email);
        if (torrent.isPresent() && user.isPresent()) {
            Optional<UserRating> userRating = userRatingRepository.findByUserIDAndTorrentID(user.get(), torrent.get());
            if (userRating.isPresent()) {
                return userRating.get().getRating();
            } else {
                return 0.0;
            }
        } else {
            throw new Exception("User or Torrent not found!");
        }
    }

    @Override
    public Double getPreviousUSerRatingOfTorrentByName(String name, String email) throws Exception {
        Optional<Torrent> torrent = torrentRepository.findByName(name);
        return getPreviousUserRatingOfTorrent(torrent.get().getTorrentID(), email);
    }
}
