package com.example.towerd.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Random;

public class Environnement {
    private ObservableList<Ennemi> ennemis;
    private ObservableList<Tour> tours;
    private Terrain terrain;
    private int nbTours;
    private int nbEnnemisAcréer;
    private IntegerProperty argent;
    private IntegerProperty vies;
    private int compteurDifficulte;


    public Environnement(Terrain terrain) {
        ennemis = FXCollections.observableArrayList();
        tours = FXCollections.observableArrayList();
        vies = new SimpleIntegerProperty(5);
        this.terrain = terrain;
        this.nbEnnemisAcréer = 0;
        this.argent = new SimpleIntegerProperty(getArgent());
        this.compteurDifficulte = compteurDifficulte;
    }
    public void setCompteurDifficulte(int compteurDifficulte){
        this.compteurDifficulte = compteurDifficulte;
    }
    public int getCompteurDifficulte(){
        return compteurDifficulte;
    }
    public int getNombreVies() {
        return vies.getValue();
    }
    public void déduireArgent(int montant) {
        this.argentProperty().setValue(argentProperty().getValue() - montant);
    }
    public void perdreVie() {
        vies.setValue(vies.getValue()-1);
    }

    public IntegerProperty getViesProperty() {
        return vies;
    }

    public ObservableList<Ennemi> getEnnemis() {
        return ennemis;
    }

    public ObservableList<Tour> getTours() {
        return tours;
    }

    public int getArgent() {
        return 70;
    }

    public IntegerProperty argentProperty() {
        return argent;
    }

    public void unTour() {
        for (Ennemi ennemi : ennemis) {
            ennemi.seDeplace();
        }

        for (int i = ennemis.size() - 1; i >= 0; i--) {
            Ennemi ennemi = ennemis.get(i);
            if (ennemis.get(i).getVie() <= 0) {
                System.out.println("ennemi mort");
                ennemis.remove(ennemis.get(i));
                argentProperty().setValue(argentProperty().getValue() + ennemi.getPrixEnnemi());
            } else {
                if (ennemis.get(i).estArrivéTuileFinale()) {
                    ennemis.remove(ennemis.get(i));
                    perdreVie();
                }
            }
        }

        if (nbEnnemisAcréer > 0) {
            if (nbTours % 4 == 0) {
                Random random = new Random();
                double randomValue = random.nextDouble();

                Ennemi ennemi = null;
                if (compteurDifficulte == 1){
                    ennemi = new Gobelin(0, 0, terrain);
                }
                if (compteurDifficulte == 2){
                    ennemi = new Ogre(0, 0, terrain);
                }

                if (compteurDifficulte == 3) {
                    if (randomValue < 1.0 / 3.0) {
                        ennemi = new Gobelin(0, 0, terrain);
                    } else {
                        ennemi = new Ogre(0, 0, terrain);
                    }
                }
                if (compteurDifficulte == 4) {
                    ennemi = new Dragon(0, 0, terrain);

                }
                if (compteurDifficulte == 5) {

                    if (randomValue < 1.0 / 3.0) {
                        ennemi = new Gobelin(0, 0, terrain);
                    } else if (randomValue < 2.0 / 3.0) {
                        ennemi = new Ogre(0, 0, terrain);
                    } else {
                        ennemi = new Dragon(0, 0, terrain);
                    }

                }

                if (ennemi != null) {
                    ennemis.add(ennemi);
                    nbEnnemisAcréer--;
                }
            }
        }

        for (Tour tour : tours) {
            tour.ennemiPorteeTour();
            tour.attaque();
        }

        nbTours++;
    }

    public void créerVagueEnnemi() {
        if (compteurDifficulte==1) {
            nbEnnemisAcréer = 5;
        } else if (compteurDifficulte ==2){
            nbEnnemisAcréer = 7;
        } else if (compteurDifficulte ==3){
            nbEnnemisAcréer = 10;
        } else if (compteurDifficulte ==4){
            nbEnnemisAcréer = 13;
        } else {
            nbEnnemisAcréer = 17;
        }
    }

    public void créerTour(Tour tour) {
        tours.add(tour);
    }

    public Tour tourPresent(int x, int y) {
        for (int i = 0; i < tours.size(); i++) {
            Tour v = tours.get(i);
            if ((x / 32 == v.getX() / 32) && (y / 32 == v.getY() / 32)) {
                terrain.getCodesTuiles()[y / 32][x / 32] = 4;
                return v;
            }
        }
        return null;
    }
}