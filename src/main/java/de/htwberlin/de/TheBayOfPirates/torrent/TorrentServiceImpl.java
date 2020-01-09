package de.htwberlin.de.TheBayOfPirates.torrent;


import de.htwberlin.de.TheBayOfPirates.user.User;
import de.htwberlin.de.TheBayOfPirates.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class TorrentServiceImpl implements TorrentService {

    private static int MAX_FILE_SIZE_IN_KILO_BYTES = 500;

    @Autowired
    private TorrentRepository torrentRepository;

    @Autowired
    private UserService userService;

    public TorrentServiceImpl(TorrentRepository torrentRepository, UserService userService) {
        this.torrentRepository = torrentRepository;
        this.userService = userService;
    }

    private long getFileSizeKiloBytes(File file) {
        return (long) file.length() / 1024;
    }

    @Override
    public Torrent saveTorrent(File torrentFile, String userEmail, String description) throws Exception {
        if (torrentFile.getName().endsWith(".torrent")
                && getFileSizeKiloBytes(torrentFile) <= MAX_FILE_SIZE_IN_KILO_BYTES) {

            FileInputStream fis = new FileInputStream(torrentFile);
            Torrent torrent = new Torrent();
            torrent.setDescription(description);
            Optional<User> user = userService.findByUserEmail(userEmail);
            if (!user.isPresent())
                throw new Exception("User not found!");
            torrent.setUser(user.get());
            String actualName = torrentFile.getName().substring(0, torrentFile.getName().length() - ".torrent".length());
            torrent.setName(actualName);
            byte[] byteTorrent = Files.readAllBytes(torrentFile.toPath());
            torrent.setTorrent(byteTorrent);
            torrentRepository.save(torrent);
            return torrent;
        } else {
            throw new Exception("Not a Torrent!");
        }
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
    public File loadTorrent(String torrentName) throws Exception {
        File torrentFile = new File(System.getProperty("user.home") + "/" + torrentName + ".torrent");
        if (torrentFile.exists()) {
            System.out.println("BUP");
            return torrentFile;
        } else {
            torrentFile.createNewFile();
            Optional<Torrent> torrent = torrentRepository.findByName(torrentName);
            System.out.println(torrentName + torrent.isPresent());
            if (!torrent.isPresent()) {
                System.out.println("BEEEEEP");
                throw new Exception("Torrent not found in Repository!");
            } else {

                try {

                    // Initialize a pointer
                    // in file using OutputStream
                    OutputStream
                            os
                            = new FileOutputStream(torrentFile);

                    // Starts writing the bytes in it
                    os.write(torrent.get().getTorrent());
                    System.out.println("Successfully"
                            + " byte inserted");

                    // Close the file
                    os.close();
                    os.flush();
                } catch (Exception e) {
                    System.out.println("Exception: " + e);
                    e.printStackTrace();
                }
                return torrentFile;
            }
        }

    }


    public void saveStandardTorrent() throws Exception {
        System.out.println("Entered postcunstruct");
        File file = new File("~/Downloads/archlinux-2019.12.01-x86_64.iso.torrent");
        if (file.exists()) {

            System.out.println(file.getName());
            String actualName = file.getName().substring(0, file.getName().length() - ".torrent".length());
            if (torrentRepository.findByName(actualName).isPresent()) {
                System.out.println("Default torrent already in database!");
                return;
            }
            System.out.println("Torrent was loaded!" + file.getName());
            saveTorrent(file, "werder@gmail.com", "This is a test torrent file in our" +
                    "database!");
        } else {
            System.out.println("Torrent not found!");
        }
    }


    public void loadStandardTorrent() throws Exception {
        File file = this.loadTorrent("archlinux-2019.12.01-x86_64.iso");
        if (file.exists()) {
            System.out.println("Torrent was restored out of database!");
            if (file.getName().equals("archlinux-2019.12.01-x86_64.iso")) {
                System.out.println("Name was also fine!");
            }
        } else {
            System.out.println("Torrent could not be restored: " + file.getName() + " " + file.getAbsolutePath());
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
    public void removeTorrentByID(int torrentID) {
        Optional<Torrent> torrent = torrentRepository.findByTorrentID(torrentID);
        torrentRepository.delete(torrent.get());
    }

    @Override
    public void removeTorrentByName(String name) {
        Optional<Torrent> torrent = torrentRepository.findByName(name);
        torrentRepository.delete(torrent.get());
    }

    @Override
    public Page<Torrent> getTorrentPagesBySearch(String searchTerm, int page) {
        PageRequest pageRequest = PageRequest.of(page, TorrentService.PAGESIZE);
        Page<Torrent> resultPage = torrentRepository.findAllByNameContaining(searchTerm, pageRequest);
        System.out.println("Searchterm was: " + searchTerm);
        System.out.println("Result was:" + resultPage.toString());
        System.out.println("Item count: " + resultPage.getTotalElements());
        return resultPage;
    }
}