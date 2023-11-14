package edu.upc.dsa.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Mapa {
    int id;
    static AtomicInteger nextId = new AtomicInteger();
    // Constructor
    public Mapa(){this.id = nextId.incrementAndGet();}
    public int getId(){return this.id;}
}
