package com.example.towerd.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tour {
    private DoubleProperty x, y;
    private double puissance;
    private int prixTour;
    private double portee;
    private Terrain terrain;
    private Environnement env;
    private ObservableList<Ennemi> ennemis;
    private String id;

    public String getId() {
        return id;
    }

    private static int compteur;

    public Tour(double x, double y, Terrain terrain, double puissance, int prixTour, double portee, Environnement env) {
        this.ennemis = FXCollections.observableArrayList();
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.puissance = puissance;
        this.prixTour = prixTour;
        this.portee = portee;
        this.terrain = terrain;
        this.env = env;
        this.id = "T" + compteur;
        compteur++;
    }

    public int getPrixTour() {
        return prixTour;
    }
    public double getX() {
        return xProperty().getValue();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public double getY() {
        return yProperty().get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public double getPortee() {
        return portee;
    }

    private boolean ennemiAPortee(Ennemi ennemi) {
        double distance = Math.sqrt(Math.pow(ennemi.getX() - getX(), 2) + Math.pow(ennemi.getY() - getY(), 2));
        return distance <= portee;
    }
    public void ennemiPorteeTour(){
        for (int i = 0; i < env.getEnnemis().size(); i++){
            Ennemi a = env.getEnnemis().get(i);
            if (ennemiAPortee(a)){
                ennemis.add(a);
            }
            if (!ennemiAPortee(a)){
                ennemis.remove(a);
            }
        }
    }

    public void ennemiMort(Ennemi ennemi){
        if (ennemi.getVie() <= 0){
            ennemis.remove(ennemi);
        }
    }

    public boolean tourBienPlacee() {
        int x = (int) getX() / 32;
        int y = (int) getY() / 32;

        if (terrain.getCodesTuiles()[y][x] == 3 ) {
            return true;
        }

        return false;
    }
    public void attaque() {
        for (Ennemi e : ennemis) {
            if (e.getVie() > 0) {
                e.subirDegat((int) puissance);
                System.out.println("attaque");
            }
        }
    }

}