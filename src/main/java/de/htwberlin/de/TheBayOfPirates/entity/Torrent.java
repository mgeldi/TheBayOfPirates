package de.htwberlin.de.TheBayOfPirates.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "torrents")
public class Torrent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int torrentID;

    @Column(name="name", nullable = false)
    private String name;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "uploadedby", joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "torrentid"))
    private User user;

    @Lob
    @Column(name="torrent", unique = false, nullable = false)
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] torrent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getTorrent() {
        return torrent;
    }

    public void setTorrent(byte[] torrent) {
        this.torrent = torrent;
    }
}
