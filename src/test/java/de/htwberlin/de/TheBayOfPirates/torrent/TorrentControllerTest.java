package de.htwberlin.de.TheBayOfPirates.torrent;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TorrentControllerTest {

    private TorrentService torrentService;
    private TorrentController torrentController;
    private Torrent mockedTorrent;
    private MockMvc mockMvc;
    private File torrentFile;
    private MockMultipartFile mockedTorrentFile;

    @BeforeEach
    void setUp() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        torrentFile = new File(classLoader.getResource("archlinux-2019.12.01-x86_64.iso.torrent").getFile());
        torrentService = mock(TorrentService.class);
        mockedTorrent = Mockito.mock(Torrent.class);
        Mockito.when(mockedTorrent.getTorrent()).thenReturn("Hello".getBytes());
        Mockito.when(mockedTorrent.getTorrentID()).thenReturn(1);
        Mockito.when(torrentService.findByName("archlinux-2019.12.01-x86_64.iso"))
                .thenReturn(java.util.Optional.ofNullable(mockedTorrent));
        Mockito.when(torrentService.findByTorrentID(mockedTorrent.getTorrentID())).thenReturn(Optional.of(mockedTorrent));
        Mockito.when(torrentService.getTorrentPagesBySearch(Mockito.anyString(), Mockito.anyInt())).thenReturn(Mockito.mock(Page.class));
        torrentController = new TorrentController(torrentService);
        mockMvc = MockMvcBuilders.standaloneSetup(torrentController).build();
        mockedTorrentFile = new MockMultipartFile(torrentFile.getName(), torrentFile.getName(),
                "text/plain", Files.readAllBytes(torrentFile.toPath()));
    }

    @Test
    void getTorrent() throws Exception {
        mockMvc.perform(get("/torrent/upload"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("torrentUpload"))
                .andExpect(model().attribute("description", ""));
    }

    @Test
    void getTorrentPageByName() throws Exception {
        MvcResult result = mockMvc.perform(get("/torrent/name=archlinux-2019.12.01-x86_64.iso"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("showtorrent"))
                .andReturn();

    }


    @Test
    void getTorrentPageByID() throws Exception {
        MvcResult result = mockMvc.perform(get("/torrent/id=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("showtorrent"))
                .andReturn();
    }


    /**
     * @Test void postTorrent() throws Exception {
     * System.out.println(torrentFile.getName());
     * mockMvc.perform(MockMvcRequestBuilders.multipart("/torrent/upload")
     * .file("file", mockedTorrentFile.getBytes())
     * .param("description", "hellohellohellohellohellohello"))
     * .andExpect(status().is2xxSuccessful())
     * .andExpect(model().attribute("error", "something"));
     * }
     */


    @Test
    void testDownloadTorrent() throws Exception {
        MvcResult result = mockMvc.perform(get("/torrent/download")
                .param("filename", "archlinux-2019.12.01-x86_64.iso"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Assert.assertEquals("Hello", result.getResponse().getContentAsString());
    }

    @Test
    void testSearchPage() throws Exception {
        MvcResult result = mockMvc.perform(get("/torrent/search=bla/page=1"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Assert.assertEquals("/torrent/search=bla/page=", result.getModelAndView().getModel().get("searchURL"));
        Assert.assertEquals(1, result.getModelAndView().getModel().get("currentPage"));
        Assert.assertEquals(0, result.getModelAndView().getModel().get("totalPages"));
    }
}