package com.example.gigatlon.domain;

public class Cycle {
    private int id;
    private String name;
    private String detail;
    private String type;
    private int order;
    private int reptitions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getReptitions() {
        return reptitions;
    }

    public void setReptitions(int reptitions) {
        this.reptitions = reptitions;
    }

    public Cycle(int id, String name, String detail, String type, int order, int reptitions) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.order = order;
        this.reptitions = reptitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cycle cycle = (Cycle) o;
        return id == cycle.getId();
    }
}