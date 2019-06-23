package com.example.showprep.spotify;

public class User {
    private String display_name;
    private String email;
    private String href;
    private String id;
    private String product;
    private String type;
    private String uri;

    public User(String display_name, String email, String href, String id, String product, String type, String uri) {
        this.display_name = display_name;
        this.email = email;
        this.href = href;
        this.id = id;
        this.product = product;
        this.type = type;
        this.uri = uri;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getEmail() {
        return email;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }
}
