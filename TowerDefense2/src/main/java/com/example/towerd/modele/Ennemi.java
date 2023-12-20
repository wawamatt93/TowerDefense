package com.example.towerd.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
public class Ennemi {
    private IntegerProperty x;
    private IntegerProperty y;
    private int vitesse;
    protected Terrain terrain;
    private double vie;
    private int preced;
    private int prixEnnemi;

    public Ennemi(int x, int y, int vitesse, Terrain terrain, double vie, int prixEnnemi) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.vitesse = vitesse;
        this.terrain = terrain;
        this.vie = vie;
        this.preced = 0;
        this.prixEnnemi = prixEnnemi;
    }

    public int getVitesse(){
        return vitesse;
    }
    public int getPrixEnnemi(){
        return prixEnnemi;
    }

    public IntegerProperty xProperty(){
        return x;
    }

    public IntegerProperty yProperty(){
        return y;
    }

    public int getX() {
        return xProperty().getValue();
    }

    public void setX(int x) {
        this.xProperty().setValue(x);
    }

    public int getY() {
        return yProperty().getValue();
    }

    public void setY(int y) {
        this.yProperty().setValue(y);
    }
    public double getVie() {
        return vie;
    }
    public void subirDegat(int degats){
        this.vie-=degats;
        if (vie < 0){
            vie = 0;
        }
    }

    public void seDeplace() {
        int[][] codesTuiles = terrain.getCodesTuiles();
        int tailleTuile = 32;
        int nouvelleX = getX() / tailleTuile; // Convertir la position en indices de tuiles
        int nouvelleY = getY() / tailleTuile; // Convertir la position en indices de tuiles
        int vitesse = getVitesse();

        for (int i = 0; i < vitesse; i++) {
             //Vérifier si la tuile en dessous est un chemin (tuile 1)
            if (nouvelleY + 1 < codesTuiles.length &&
                    (codesTuiles[nouvelleY + 1][nouvelleX] == 1 || codesTuiles[nouvelleY + 1][nouvelleX] == 2) && preced != 4) {
                nouvelleY += 1;
                preced = 1;
            }
            // Vérifier si la tuile à droite est un chemin (tuile 1)
            else if (nouvelleX + 1 < codesTuiles[0].length &&
                    (codesTuiles[nouvelleY][nouvelleX + 1] == 1 || codesTuiles[nouvelleY][nouvelleX + 1] == 2) && preced != 3) {
                nouvelleX += 1;
                preced = 2;
            }
            // Vérifier si la tuile à gauche est un chemin (tuile 1)
            else if (nouvelleX - 1 >= 0 &&
                    (codesTuiles[nouvelleY][nouvelleX - 1] == 1 || codesTuiles[nouvelleY][nouvelleX - 1] == 2) && preced != 2) {
                nouvelleX -= 1;
                preced = 3;
            }
            // Vérifier si la tuile au dessus est un chemin (tuile 1)
            else if (nouvelleY - 1 >= 0 &&
                    (codesTuiles[nouvelleY - 1][nouvelleX] == 1 || codesTuiles[nouvelleY - 1][nouvelleX] == 2) && preced != 1) {
                nouvelleY -= 1;
                preced = 4;
            }

            // Convertir les indices de tuiles en position réelle
            setX(nouvelleX * tailleTuile);
            setY(nouvelleY * tailleTuile);
        }
    }

    public boolean estArrivéTuileFinale() {
        if (terrain.getCodesTuiles()[getY() / 32][getX() / 32] == 2)
            return true;
        else return false;
    }
}