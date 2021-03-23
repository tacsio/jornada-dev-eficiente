package io.tacsio.mercadolivre.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.util.UUID;

@Entity
public class Image {

    @Id
    private String id;

    private String name;

    private String location;

    private String uri;

    @PrePersist
    void generateId() {
        if (id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public Image() {
    }

    public Image(String name, String location) {
        this.name = name;
        this.location = location;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
