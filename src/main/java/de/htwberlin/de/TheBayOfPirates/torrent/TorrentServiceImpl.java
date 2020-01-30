package de.htwberlin.de.TheBayOfPirates.torrent;


import de.htwberlin.de.TheBayOfPirates.rating.UserRatingService;
import de.htwberlin.de.TheBayOfPirates.user.User;
import de.htwberlin.de.TheBayOfPirates.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TorrentServiceImpl implements TorrentService {

    @Autowired
    private TorrentRepository torrentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRatingService userRatingService;

    public TorrentServiceImpl(TorrentRepository torrentRepository, UserService userService) {
        this.torrentRepository = torrentRepository;
        this.userService = userService;
    }

    @Override
    public Torrent saveTorrentBytes(byte[] torrentBytes, String filename, String userEmail, String description) throws Exception {
        if (filename.endsWith(".torrent")
                && (torrentBytes.length / 1000) <= MAX_FILE_SIZE_IN_KILO_BYTES) {
            Torrent torrent = new Torrent();
            torrent.setDescription(description);
            Optional<User> user = userService.findByUserEmail(userEmail);
            if (!user.isPresent()) {
                throw new Exception("User not found!");
            }
            torrent.setUser(user.get());
            String actualName = filename.substring(0, filename.length() - ".torrent".length());
            torrent.setName(actualName);
            torrent.setTorrent(torrentBytes);
            torrentRepository.save(torrent);
            return torrent;
        } else {
            throw new Exception("Not a torrent!");
        }
    }

    @Override
    public Optional<Torrent> findByName(String name) {
        return torrentRepository.findByName(name);
    }

    @Override
    public Optional<Torrent> findByTorrentID(int torrentID) {
        return torrentRepository.findByTorrentID(torrentID);
    }

    @Override
    public void removeTorrentByName(String name) throws Exception {
        Optional<Torrent> torrent = torrentRepository.findByName(name);
        if (torrent.isPresent()) {
            userRatingService.removeAllRatingsOfTorrent(torrent.get().getTorrentID());
            torrentRepository.delete(torrent.get());
        }
    }

    @Override
    public Page<Torrent> getTorrentPagesBySearch(String searchTerm, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return torrentRepository.findAllByNameContaining(searchTerm, pageRequest);
    }
}