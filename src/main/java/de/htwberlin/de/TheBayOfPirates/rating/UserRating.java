package de.htwberlin.de.TheBayOfPirates.rating;

import de.htwberlin.de.TheBayOfPirates.torrent.Torrent;
import de.htwberlin.de.TheBayOfPirates.user.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table(name = "userrating")
public class UserRating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ratingID;

    @ManyToOne
    private Torrent torrentID;

    @ManyToOne
    private User userID;

    @Column(name = "rating")
    @Min(0)
    @Max(10)
    private Double rating = 0.0;

    public UserRating() {

    }

    public UserRating(Torrent torrent, User user) {
        this.torrentID = torrent;
        this.userID = user;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
