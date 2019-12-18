package de.htwberlin.de.TheBayOfPirates.torrent;

import de.htwberlin.de.TheBayOfPirates.service.TorrentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class TorrentControllerTest {

    private TorrentService torrentService;
    private TorrentController torrentController;

    @BeforeEach
    void setUp() {
        torrentService = mock(TorrentService.class);
        torrentController = new TorrentController(torrentService);
    }

    @Test
    void getTorrent() {
    }

    @Test
    void testGetTorrent() {
    }

    @Test
    void postTorrent() {
    }

    @Test
    void testPostTorrent() {
    }

    @Test
    void downloadFile1() {
    }
}