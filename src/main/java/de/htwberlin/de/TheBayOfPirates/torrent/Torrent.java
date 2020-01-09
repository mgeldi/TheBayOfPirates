package de.htwberlin.de.TheBayOfPirates.torrent;

import com.sun.istack.NotNull;
import de.htwberlin.de.TheBayOfPirates.user.User;
import jdk.vm.ci.meta.Local;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "torrents")
public class Torrent {

    @Id
    @Column(name="torrentid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int torrentID;

    public int getTorrentID() {
        return torrentID;
    }

    @Column(name="name", nullable = false, unique = true)
    @Length(min = 4, max = 50, message = "Your torrent name must be between 4 and 50 characters long")
    private String name;

    @NotNull
    @Length(min = 20, max = 1000, message = "Please provide a sufficient description that" +
            " is between 20 and 1000 characters long!")
    private String description;

    @ManyToOne
    @JoinTable(name = "uploadedby", joinColumns = @JoinColumn(name = "torrentid"),
            inverseJoinColumns = @JoinColumn(name = "userid"))
    private User user;

    private LocalDateTime uploadedDateTime;

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

    @PrePersist
    private void saveTimeStamp(){
        uploadedDateTime = LocalDateTime.now();
    }

    public LocalDateTime getUploadedDateTime() {
        return uploadedDateTime;
    }
}
