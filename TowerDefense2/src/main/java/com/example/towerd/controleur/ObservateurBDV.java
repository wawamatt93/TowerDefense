//package com.example.towerd.controleur;
//
//import com.example.towerd.modele.BarreDeVie;
//import javafx.collections.ListChangeListener;
//import javafx.scene.control.ProgressBar;
//import javafx.scene.layout.Pane;
//
//public class ObservateurBDV {
//    private Pane panneauJeu;
//
//    private BarreDeVie barreDeVieVue;
//    public ObservateurBDV(Pane panneauJeu) {
//        this.panneauJeu = panneauJeu;
//        barreDeVieVue = new BarreDeVie(Pane panneauJeu);
//    }
//
//    @Override
//    public void onChanged(ListChangeListener.Change<? extends BarreDeVie> change) {
//        while (change.next()) {
//            if (change.wasAdded()) {
//                for (BarreDeVie b : change.getAddedSubList()) {
//                    System.out.println("add");
//                    BarreDeVie.afficherBarreVie(b);
//                }
//            }
//        }
//    }
//
//}
