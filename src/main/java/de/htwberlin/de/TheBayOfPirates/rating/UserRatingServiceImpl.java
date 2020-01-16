package de.htwberlin.de.TheBayOfPirates.rating;

import de.htwberlin.de.TheBayOfPirates.torrent.Torrent;
import de.htwberlin.de.TheBayOfPirates.torrent.TorrentRepository;
import de.htwberlin.de.TheBayOfPirates.user.User;
import de.htwberlin.de.TheBayOfPirates.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if(!torrent.isPresent()){
            throw new Exception("Torrent does not exist!");
        }
        List<UserRating> ratings = userRatingRepository.findAllByTorrentID(torrent.get());
        if (ratings.isEmpty()) {
            throw new Exception("No Ratings for this torrent!");
        } else {
            int numberOfRatings = ratings.size();
            double rating = 0.0;
            for (UserRating userRating : ratings) {
                rating += userRating.getRating();
            }
            rating /= numberOfRatings;
            torrent.get().setRating(rating);
            torrentRepository.saveAndFlush(torrent.get());
        }
    }

    @Override
    public void giveRatingToTorrent(String email, int torrentID, double rating) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        Optional<Torrent> torrent = torrentRepository.findByTorrentID(torrentID);
        if(!user.isPresent()){
            throw new Exception("User not found! Unexpected Error!");
        }
        if(!torrent.isPresent()){
            throw new Exception("Torrent not found! Unexpected Error!");
        }
        Optional<UserRating> loadedUserRating = userRatingRepository.findByUserID(user.get());
        if(loadedUserRating.isPresent()){
            loadedUserRating.get().setRating(rating);
            userRatingRepository.saveAndFlush(loadedUserRating.get());
        } else {
            UserRating userRating = new UserRating(torrent.get(), user.get());
            userRating.setRating(rating);
            userRatingRepository.saveAndFlush(userRating);
        }
        getRatingOfTorrentByID(torrentID);
    }
}
