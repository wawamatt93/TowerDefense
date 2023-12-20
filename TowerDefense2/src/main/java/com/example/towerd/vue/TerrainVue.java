package com.example.towerd.vue;

import com.example.towerd.Main;
import com.example.towerd.modele.Terrain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;


public class TerrainVue  {
    private TilePane tilePane;
    private Terrain terrain;
    public TerrainVue(TilePane tilePane, Terrain terrain) {
        this.tilePane = tilePane;
        this.terrain = terrain;
    }
    public void afficherTerrain() {
        // lire les images
        Image imageChemin = new Image(Main.class.getResource("chemin.jpeg").toString());
        Image imageHerbe = new Image(Main.class.getResource("herbe.jpeg").toString());
        Image imageFinal = new Image(Main.class.getResource("final.jpeg").toString());
        Image imageTour = new Image(Main.class.getResource("ZoneA.jpeg").toString());

        int[][] codesTuiles = terrain.getCodesTuiles();

        for (int row = 0; row < codesTuiles.length; row++) {
            for (int col = 0; col < codesTuiles[row].length; col++) {
                ImageView iv = null;
                if (codesTuiles[row][col] == 0) {
                    iv = new ImageView(imageHerbe);
                } else if (codesTuiles[row][col] == 1) {
                    iv = new ImageView(imageChemin);
                } else if (codesTuiles[row][col] == 2) {
                    iv = new ImageView(imageFinal);
                } else if (codesTuiles[row][col] == 3) {
                    iv = new ImageView(imageTour);
                }
                tilePane.getChildren().add(iv);
            }
        }
    }
}

