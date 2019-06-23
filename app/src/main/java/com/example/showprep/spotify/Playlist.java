package com.example.showprep.spotify;

public class Playlist {
    private Boolean collaborative;
    private String description;
    private String href;
    private String id;
    private String name;
    private String snapshot_id;
    private String type;
    private String uri;
    /*TODO: Check if we need Paging and Track objects */

    public Playlist(Boolean collaborative, String description, String href, String id, String name, String snapshot_id, String type, String uri) {
        this.collaborative = collaborative;
        this.description = description;
        this.href = href;
        this.id = id;
        this.name = name;
        this.snapshot_id = snapshot_id;
        this.type = type;
        this.uri = uri;
    }

    public Boolean getCollaborative() {
        return collaborative;
    }

    public String getDescription() {
        return description;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSnapshot_id() {
        return snapshot_id;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }
}
