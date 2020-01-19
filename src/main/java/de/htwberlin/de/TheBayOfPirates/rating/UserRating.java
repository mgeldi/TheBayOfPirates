package de.htwberlin.de.TheBayOfPirates.rating;

import de.htwberlin.de.TheBayOfPirates.torrent.Torrent;
import de.htwberlin.de.TheBayOfPirates.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Torrent torrentID;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
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
