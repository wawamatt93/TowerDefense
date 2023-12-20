package com.example.towerd.controleur;

import com.example.towerd.Main;
import com.example.towerd.modele.*;
import javafx.collections.ListChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ObservateurEnnemis implements ListChangeListener<Ennemi> {
    private Pane panneauJeu;
    private Map<Ennemi, ImageView> ennemiSprites = new HashMap<>();
    private ImageView iv5;
    private Image imageGob;
    private Image imageOgre;
    private Image imageDragon;


    public ObservateurEnnemis(Pane panneauJeu){
        this.panneauJeu = panneauJeu;
        URL urlImageGob = Main.class.getResource("gobelin.jpeg");
        imageGob = new Image(String.valueOf(urlImageGob));

        URL urlImageOgre = Main.class.getResource("ogre.jpeg");
        imageOgre = new Image(String.valueOf(urlImageOgre));

        URL urlImageDragon = Main.class.getResource("dragon.jpeg");
        imageDragon = new Image(String.valueOf(urlImageDragon));
    }

    private void creerSpriteEnnemi(Ennemi e) {
        if (e instanceof Gobelin) {
            iv5 = new ImageView(imageGob);
        }else {
            if(e instanceof Ogre){
                iv5 = new ImageView(imageOgre);
            } else {
                iv5 = new ImageView(imageDragon);
            }
        }

        iv5.translateXProperty().bind(e.xProperty());
        iv5.translateYProperty().bind(e.yProperty());
        ennemiSprites.put(e, iv5);
        panneauJeu.getChildren().add(iv5);
    }

    private void enleverSpriteEnnemi(Ennemi ennemiMort) {
        ImageView imageView = ennemiSprites.get(ennemiMort);
        panneauJeu.getChildren().remove(imageView);
    }

    @Override
    public void onChanged(Change<? extends Ennemi> c) {
        while (c.next()) {
            // Gestion des nouveaux-nés
            for (Ennemi nouveau : c.getAddedSubList()) {
                creerSpriteEnnemi(nouveau);
            }
            // Gestion des morts
            for (Ennemi mort : c.getRemoved()) {
                enleverSpriteEnnemi(mort);
            }
        }
    }
}
