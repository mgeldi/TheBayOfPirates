package de.htwberlin.de.TheBayOfPirates.service;


import de.htwberlin.de.TheBayOfPirates.entity.Torrent;
import de.htwberlin.de.TheBayOfPirates.entity.User;
import de.htwberlin.de.TheBayOfPirates.repository.TorrentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

public class TorrentServiceImpl implements TorrentService {

    private static int MAX_FILE_SIZE_IN_KILO_BYTES = 500;

    @Autowired
    private TorrentRepository torrentRepository;

    @Autowired
    private UserService userService;

    TorrentServiceImpl(TorrentRepository torrentRepository, UserService userService){
        this.torrentRepository = torrentRepository;
        this.userService = userService;
    }

    private long getFileSizeKiloBytes(File file){
        return (long) file.length() / 1024;
    }

    @Override
    public void saveTorrent(File torrentFile, String userEmail, String description) throws Exception {
        if(torrentFile.getName().endsWith(".torrent")
                && getFileSizeKiloBytes(torrentFile) <= MAX_FILE_SIZE_IN_KILO_BYTES){

            FileInputStream fis = new FileInputStream(torrentFile);
            Torrent torrent = new Torrent();
            torrent.setDescription(description);
            Optional<User> user = userService.findByUserEmail(userEmail);
            if(!user.isPresent())
                throw new Exception("User not found!");
            torrent.setUser(user.get());
            torrent.setName(torrentFile.getName().split(".")[0]);
            torrentRepository.save(torrent);

        } else {
            throw new Exception("Not a Torrent!");
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
}
