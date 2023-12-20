package com.example.towerd.modele;

public class Bombardier extends Tour  {
    public Bombardier(double x, double y, Terrain terrain, Environnement env){
        super(x, y, terrain, 100, 75, 100, env);
    }
}
