package mvc.controleur;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import mvc.modele.allumettes.InterfaceAllumettes;

public class ControleurJeuAllumette implements Initializable {

	private InterfaceAllumettes allumette;

	@FXML
	private Button boutonUn, boutonDeux, boutonQuitter;

	@FXML
	private Label nombreAllumettesPossedes, labelPartie, tourJoueur, compteurAllumettesPartie;

	int nbAllumettesJoueur = 0;
	int nbAllumettesPartie = 0;
	int nbAllumettesTotal = 0;
	int idPartie;
	int tour;

	// ImageView de toutes les allumettes possiblement présentent dans la partie
	@FXML
	private ImageView a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19;
	@FXML
	private ArrayList<ImageView> listeAllumettes = new ArrayList<ImageView>();

	/*
	 * Fonction initialize
	 */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			listeAllumettes.add(a1); // ajout des images allumettes en fonction du chiffre aléatoire reçu
			listeAllumettes.add(a2);
			listeAllumettes.add(a3);
			listeAllumettes.add(a4);
			listeAllumettes.add(a5);
			listeAllumettes.add(a6);
			listeAllumettes.add(a7);
			listeAllumettes.add(a8);
			listeAllumettes.add(a9);
			listeAllumettes.add(a10);
			listeAllumettes.add(a11);
			listeAllumettes.add(a12);
			listeAllumettes.add(a13);
			listeAllumettes.add(a14);
			listeAllumettes.add(a15);
			listeAllumettes.add(a16);
			listeAllumettes.add(a17);
			listeAllumettes.add(a18);
			listeAllumettes.add(a19);

			allumette = (InterfaceAllumettes) Naming.lookup("rmi://localhost:8000/allumette");
			idPartie = allumette.nouvellePartie(); // Mise de l'id de la partie avec nouvellePartie()
			nbAllumettesPartie = allumette.generationAleatoireAllumettes(idPartie); // Stockage du nombre d'allumettes
			// en partie avec la génération
			// aléatoire
			nbAllumettesTotal = nbAllumettesPartie;
			allumette.setNbAllumettePartie(idPartie, nbAllumettesPartie); // set du nombre d'allumettes en partie
			tour(); // Utilisation de la fonction tour pour directement donner la main au joueur

		} catch (MalformedURLException | RemoteException | NotBoundException | InterruptedException e) { // catch des
			// erreurs
			System.out.println("Init : " + e);

		}

		for (int i = 0; i < nbAllumettesPartie; i++) { // boucle affichant les images d'allumettes en fonction du nombre
			// d'allumettes en partie
			listeAllumettes.get(i).setVisible(true);
		}

		labelPartie.setText("Partie n°" + idPartie); // Label indiquant le numéro de partie en cours

		nbAllumettesJoueur = 0; // Mise à 0 du nombre d'allumettes du joueur en début de partie

		compteurAllumettesPartie.setText("" + nbAllumettesPartie); // Indication du nombre d'allumettes en partie (en
		// plus des images)
		nombreAllumettesPossedes.setText("Vous possédez " + nbAllumettesJoueur + " allumettes"); // Indication du nombre
		// d'allumettes
		// possédées par le
		// joueur

		boutonDeux.setDisable(false); // Affichage du bouton retirant deux allumettes

	}

	/*
	 * Fonction permettant de faire une vérification de fin de partie
	 */

	public void verifFinDePartie() {
		if (nbAllumettesPartie <= 0) { // Si le nombre d'allumettes est inférieur ou égal à 0 alors
			finDePartie(); // lancement de la fonction fin de partie
		}
	}

	/*
	 * Fonction permettant l'arrêt du jeu
	 */

	public void finDePartie() {
		Alert alert = new Alert(AlertType.INFORMATION); // création d'une alerte pop-up

		if (nbAllumettesJoueur % 2 == 0) { // Si le compteur du joueur paire alors c'est une défaite
			/*
			 * Affichage de l'alerte avec les détails voulus (Titre, Header et Contenu de
			 * l'alerte)
			 */
			alert.setTitle("Défaite");
			alert.setHeaderText("Dommage vous avez perdu ...");
			alert.setContentText("Réessayez une prochaine fois");
			/*
			 * Sinon victoire du joueur
			 */
		} else { // Sinon victoire
			alert.setTitle("Victoire");
			alert.setHeaderText("Bravo vous avez gagné !");
			alert.setContentText("Le goût de la victoire est en vous");

		}
		alert.show(); // Affichage de l'alerte
		/*
		 * Stage permet de quitter directement la fenêtre de jeu
		 */
		Stage stage = (Stage) boutonQuitter.getScene().getWindow();
		stage.close();

	}

	/*
	 * Fonction retirnt une allumette
	 */

	@FXML
	void retirerUneAllumette() throws RemoteException, InterruptedException {

		allumette.soustraireAllumettes(idPartie, 1); // retirer une allumette de la partie

		allumette.setNombreAllumettesJoueur(idPartie, nbAllumettesJoueur + 1); // ajouter 1 au compteur du joueur de la
		// partie

		nbAllumettesPartie = allumette.getNbAllumettePartie(idPartie); // récupération du nombre d'allumettes en partie
		nbAllumettesJoueur = allumette.getNombreAllumettesJoueur(idPartie); // récupération du nombre d'allumettes du
		// joueur

		compteurAllumettesPartie.setText("" + nbAllumettesPartie); // réécrire le nombre d'allumettes de la partie au
		// compteur
		nombreAllumettesPossedes.setText("Vous possédez " + nbAllumettesJoueur + " allumettes"); // réécrire le nombre
		// d'allumettes du
		// joueur en partie

		tour(); // appel de la fonction tour pour donner la main à l'adversaire

	}

	/*
	 * Fonction retirant deux allumettes
	 */

	@FXML
	void retirerDeuxAllumettes() throws RemoteException, InterruptedException {

		allumette.soustraireAllumettes(idPartie, 2); // retirer deux allumettes de la partie

		allumette.setNombreAllumettesJoueur(idPartie, nbAllumettesJoueur + 2); // ajouter 2 au compteur du joueur de la
		// partie

		nbAllumettesPartie = allumette.getNbAllumettePartie(idPartie); // récupération du nombre d'allumettes en partie
		nbAllumettesJoueur = allumette.getNombreAllumettesJoueur(idPartie); // récupération du nombre d'allumettes du
		// joueur

		compteurAllumettesPartie.setText("" + nbAllumettesPartie); // réécrire le nombre d'allumettes de la partie au
		// compteur
		nombreAllumettesPossedes.setText("Vous possédez " + nbAllumettesJoueur + " allumettes");// réécrire le nombre
		// d'allumettes du
		// joueur en partie

		tour(); // appel de la fonction tour pour donner la main à l'adversaire
	}

	/*
	 * Fonction permettant de jouer les tours de la partie
	 */

	public void tour() throws RemoteException, InterruptedException {
		tour = allumette.getTour(idPartie); // récupération du tour de la partie

		compteurAllumettesPartie.setText("" + nbAllumettesPartie); // affichage du nombre d'allumettes en partie

		// boucle permettant d'afficher les allumettes petit à petit au cours de la
		// partie
		for (int i = 0; i < nbAllumettesTotal; i++) {
			listeAllumettes.get(i).setVisible(false);
		}
		for (int i = 0; i < nbAllumettesPartie; i++) {
			listeAllumettes.get(i).setVisible(true);
		}

		// Création d'une pause permettant de faire patienter le joueur pendant que l'IA
		// joue

		PauseTransition pause = new PauseTransition(Duration.seconds(2)); // pause de 2 secondes à chaque tour
		pause.setOnFinished(event -> {

			try {
				allumette.coupIA(idPartie); // appel de coupIA permettant à l'IA de jouer son tour
				compteurAllumettesPartie.setText("" + nbAllumettesPartie); // re affichage du nombre d'allumettes en partie une fois le tour de l'IA joué
				nbAllumettesPartie = allumette.getNbAllumettePartie(idPartie); // récupération du nombre d'allumettes en partie
				allumette.setTour(idPartie, tour - 1); // tour - 1 permettant de donner la main au joueur
				tour(); // appel de la fonction permettant de faire jouer le joueur
			} catch (RemoteException | InterruptedException e) {
				System.out.println(e);
			}

		});

		if (tour == 1) { // si le tour == 1
			tourJoueur.setText("A votre tour..."); // affichage du texte "a votre tour..." pour le joueur
			boutonUn.setDisable(false); // activation du bouton 1
			boutonDeux.setDisable(false); // activation du bouton 2
			allumette.setTour(idPartie, tour + 1); // set de tour + 1
		} else if (nbAllumettesPartie >= 1) { // Sinon si le nombre d'allumettes en partie est égal ou supérieur à 1
			// alors
			boutonUn.setDisable(true); // désactivation du bouton 1
			boutonDeux.setDisable(true); // désactivation du bouton 2
			tourJoueur.setText("Au tour de l'adversaire..."); // affichage du texte "a votre tour..." pour l'IA
			pause.play(); // faire jouer la pause, permettant de faire jouer l'IA durant cette pause

		}

		if (nbAllumettesPartie < 2) { // si le nombre d'allumettes en partie est inférieur à 2 alors
			boutonDeux.setDisable(true); // désactivation du bouton 2
		}

		if (nbAllumettesPartie <= 0) { // Si nombre d'allumettes en jeu est inférieur ou égal à 0 alors
			boutonUn.setDisable(true); // Désactiver le bouton 1
			finDePartie(); // appel de la fonction fin de partie
		}
	}

	/*
	 * Fonction permettant de quitter le jeu à tout moment
	 */

	@FXML
	public void quitter() {
		/*
		 * Stage permet de quitter directement la fenêtre de jeu
		 */
		Stage stage = (Stage) boutonQuitter.getScene().getWindow();
		stage.close();

	}

}
