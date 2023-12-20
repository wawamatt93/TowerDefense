package com.example.towerd.controleur;

import com.example.towerd.modele.*;
import com.example.towerd.vue.TerrainVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {
    private Environnement env;
    private Terrain terrain;
    private TerrainVue terrainVue;
    private Timeline gameLoop;
    private boolean jeuEnPause = false;

    @FXML
    private TilePane tilePane;

    @FXML
    private RadioButton charr;

    @FXML
    private RadioButton artilleur;

    @FXML
    private RadioButton bombardier;

    @FXML
    private void vagueButtonClicked() {
        env.setCompteurDifficulte(env.getCompteurDifficulte()+1);
        env.créerVagueEnnemi();

    }
    @FXML
    private void pauseButton() {
        if (!jeuEnPause) {
            // Si le jeu n'est pas en pause, arrêtez-le
            gameLoop.stop();
            jeuEnPause = true;
        } else {
            // Si le jeu est en pause, reprenez-le
            gameLoop.play();
            jeuEnPause = false;
        }

    }
    @FXML
    private void quitterButton(ActionEvent event) {
        Button quitterButton = (Button) event.getSource();
        // Obtenez une référence à la scène actuelle
        Stage stage = (Stage) quitterButton.getScene().getWindow();
        // Fermez la scène
        stage.close();
    }


    @FXML
    private Pane panneauJeu;

    @FXML
    private Button vagueButton;
    @FXML
    private Label argentInitial;
    @FXML
    private Label nombreVies;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        terrain = new Terrain();
        terrainVue = new TerrainVue(tilePane, terrain);
        terrainVue.afficherTerrain();

        initAnimation();
        gameLoop.play();

        env = new Environnement(terrain);
        nombreVies.setText(Integer.toString(env.getNombreVies()));
        env.getViesProperty().addListener((obs, old, nouv) -> this.nombreVies.setText(String.valueOf(nouv)));
        env.getEnnemis().addListener(new ObservateurEnnemis(panneauJeu));
        env.getTours().addListener(new ObservateurTours(panneauJeu));
        env.getTours().addListener(new ObservateurTours(panneauJeu));
        env.getEnnemis().addListener(new ObservateurEnnemis(panneauJeu));

        panneauJeu.setOnMouseClicked(event -> {
            poseTour(null, event.getX(), event.getY());
        });
        vagueButton.setOnAction(event -> vagueButtonClicked());
        argentInitial.setText(Integer.toString(env.getArgent()));
        env.argentProperty().addListener((obs,old,nouv) -> {
            argentInitial.setText(nouv.toString());
        });
    }

    private void initAnimation() {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                // on définit le FPS (nbre de frame par seconde)
                Duration.seconds(0.25),
                // on définit ce qui se passe à chaque frame
                // c'est un eventHandler d'ou le lambda
                (ev -> {
                    env.unTour();
                    if (env.getNombreVies() <= 0){
                        gameLoop.stop();
                        try {
                            System.out.println("Fin.");
                            finDeParti();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    // Ajouter la condition pour vérifier si compteurDifficulte = 5 et plus d'ennemis
                    if (env.getCompteurDifficulte() == 5 && env.getEnnemis().isEmpty()) {
                        gameLoop.stop();
                        try {
                            System.out.println("Vous Avez Gagner");
                            partiGagner();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                })
        );
        gameLoop.getKeyFrames().add(kf);
    }
    public void poseTour(ActionEvent event, double x, double y) {
        int positionX = (int) (x / 32) * 32;
        int positionY = (int) (y / 32) * 32;

        if (tourDejaPresente(positionX, positionY)) {
            Tour t = env.tourPresent(positionX, positionY);
            env.getTours().remove(t);
            terrain.getCodesTuiles()[positionY / 32][positionX / 32] = 3;
            double prixTourSupprimee = t.getPrixTour() / 2;
            env.argentProperty().setValue(env.argentProperty().getValue() + prixTourSupprimee);
            if(terrain.getCodesTuiles()[positionY / 32][positionX / 32] == 3){
                System.out.println("tour retirer. Vous avez récupéré" + prixTourSupprimee +"pièces.");}
            return;
        }
        Tour c;
        int prixTour = 0;

        if (charr.isSelected()) {
            c = new Charr(positionX, positionY, terrain, env);
            prixTour = c.getPrixTour();
        } else if (artilleur.isSelected()) {
            c = new Artilleur(positionX, positionY, terrain, env);
            prixTour = c.getPrixTour();
        } else if (bombardier.isSelected()) {
            c = new Bombardier(positionX, positionY, terrain, env);
            prixTour = c.getPrixTour();
        } else {
            return; // Aucun type de tour sélectionné
        }

        if (prixTour > env.argentProperty().getValue()) {
            System.out.println("Pas d'argent suffisant");
            return;
        }
        ajouterTour(c);

    }
    public void finDeParti()throws IOException{
        Stage primaryStage = (Stage) panneauJeu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL ressource = getClass().getResource("/com/example/towerd/GameOver.fxml");
        Parent root = fxmlLoader.load(ressource);
        Scene scene = new Scene(root,800,800);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Towerdefense");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public void partiGagner()throws IOException{
        Stage primaryStage = (Stage) panneauJeu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL ressource = getClass().getResource("/com/example/towerd/Win.fxml");
        Parent root = fxmlLoader.load(ressource);
        Scene scene = new Scene(root,800,800);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Towerdefense");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public boolean tourDejaPresente(int x, int y) {
        for (Tour tour : env.getTours()) {
            if (tour.getX() == x && tour.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public void ajouterTour(Tour c){
        if (c.tourBienPlacee()) {
            env.créerTour(c);
            System.out.println("Tour ajoutée");
            env.déduireArgent(c.getPrixTour());

        } else {
            System.out.println("Erreur d'ajout");
        }
    }
}
