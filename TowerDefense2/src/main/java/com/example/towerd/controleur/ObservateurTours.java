package com.example.towerd.controleur;

import com.example.towerd.Main;
import com.example.towerd.modele.Artilleur;
import com.example.towerd.modele.Charr;
import com.example.towerd.modele.Tour;
import javafx.collections.ListChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class ObservateurTours implements ListChangeListener<Tour> {
    private Pane panneauJeu;
    private Image imageCharr;
    private Map<Tour, ImageView> tourSprites = new HashMap<>();
    private Image imageArt;
    private Image imageBomb;
    private ImageView iv6;
    private Image imageMeteore;

    public ObservateurTours(Pane panneauJeu){
        this.panneauJeu = panneauJeu;
        URL urlImageTou = Main.class.getResource("charr.jpeg");
        imageCharr = new Image(String.valueOf(urlImageTou));
        URL urlImageArt = Main.class.getResource("artilleur.jpeg");
        imageArt = new Image(String.valueOf(urlImageArt));
        URL urlImageBomb = Main.class.getResource("bombardier.jpeg");
        imageBomb = new Image(String.valueOf(urlImageBomb));
        URL urlImageMeteore = Main.class.getResource("meteore.jpeg");
        imageMeteore = new Image(String.valueOf(urlImageMeteore));
    }

    public void créerSpriteTour(Tour t) {
        if (t instanceof Charr) {
            iv6 = new ImageView(imageCharr);
        }else {
            if(t instanceof Artilleur){
            iv6 = new ImageView(imageArt);
        } else {
            iv6 = new ImageView(imageBomb);
            }
        }
        iv6.translateXProperty().bind(t.xProperty());
        iv6.translateYProperty().bind(t.yProperty());
        panneauJeu.getChildren().add(iv6);
        tourSprites.put(t, iv6);
        Circle porteeCircle = new Circle();
        porteeCircle.setFill(Color.TRANSPARENT);
        porteeCircle.setStroke(Color.BLUE);
        porteeCircle.setStrokeWidth(2.0);
        porteeCircle.setRadius(t.getPortee());
        porteeCircle.setId(t.getId());
        porteeCircle.centerXProperty().bind(t.xProperty().add(16));  // Ajoutez un décalage de 16 pour centrer le cercle sur la tour
        porteeCircle.centerYProperty().bind(t.yProperty().add(16));
        panneauJeu.getChildren().add(porteeCircle);
    }

    private void enleverSpriteTour(Tour tour) {
        ImageView imageView = tourSprites.get(tour);
        panneauJeu.getChildren().remove(imageView);
        panneauJeu.getChildren().remove(this.panneauJeu.lookup("#" + tour.getId()));
    }

    @Override
    public void onChanged(Change<? extends Tour> c) {
        while (c.next()) {
            // Gestion des nouveaux-nés
            for (Tour nouveau : c.getAddedSubList()) {
                créerSpriteTour(nouveau);
            }
            for (Tour disparait : c.getRemoved()) {
                enleverSpriteTour(disparait);
            }

        }
    }

}

