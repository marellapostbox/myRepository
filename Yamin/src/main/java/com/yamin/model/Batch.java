package com.yamin.model;

public class Batch {
    private int id;
    private String name;
    private String timing;

    public Batch() {}

    public Batch(int id, String name, String timing) {
        this.id = id;
        this.name = name;
        this.timing = timing;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTiming() { return timing; }
    public void setTiming(String timing) { this.timing = timing; }

}
