package com.yamin.model;

public class Participant {
    private int id;
    private String name;
    private String email;
    private String password;
    private Integer batchId;

    public Participant() {}

    public Participant(int id, String name, String email, String password, Integer batchId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.batchId = batchId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getBatchId() { return batchId; }
    public void setBatchId(Integer batchId) { this.batchId = batchId; }
}
