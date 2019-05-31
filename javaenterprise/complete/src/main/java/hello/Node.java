// tag::sample[]
package hello;

import javax.persistence.*;
import java.util.HashMap;

@Entity
public class Node {

    @Id
    private Long id;
    private double lat;
    private double lon;
    @Column(name = "username")
    private String user;
    @Column(length = 1024*25)
    private HashMap<String, String> tags;

    public Long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setUser(String user) {
        this.user = user;
    }

    protected Node() {
        tags = new HashMap<>();
    }

    public Node(Long id, double lat, double lon, String user, HashMap<String, String> tags) {
        this.id=id;
        this.lat = lat;
        this.lon = lon;
        this.user = user;
        this.tags = tags;
    }

    public HashMap<String, String> getTags() {
        return tags;
    }
}

