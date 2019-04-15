package com.example.showprep.setlist;

public class City {
    private String id;
    private String name;
    private String stateCode;
    private String state;
    private Coords coords;
    private Country country;

    public City(String id, String name, String stateCode, String state) {
        this.id = id;
        this.name = name;
        this.stateCode = stateCode;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getState() {
        return state;
    }
}
