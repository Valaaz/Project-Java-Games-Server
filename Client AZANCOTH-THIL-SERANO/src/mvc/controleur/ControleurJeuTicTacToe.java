package mvc.controleur;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mvc.modele.tictactoe.InterfaceTicTacToe;

public class ControleurJeuTicTacToe implements Initializable {

	@FXML
	private Label label1, label2, label3, label4, label5, label6, label7, label8, label9, labelIdPartie;

	// Ces 2 labels doivent être instanciés avec le "new Label()" pour ne pas
	// générer un "NullPointerException" dans les threads
	@FXML
	private Label tourJoueur = new Label();
	@FXML
	private Label labelJoueur = new Label();
	@FXML
	private Button btnQuitter;

	private int tour = 1;
	private int idPartie;

	// Nombre de joueur
	private int nbJoueur = 0;
	// Interface du TicTacToe qui nous permettra d'utiliser les méthodes du serveur
	private InterfaceTicTacToe intTtt;

	// Numéro du joueur
	private int numJoueur;

	// Tableau des 9 labels de la grille
	String[] labels;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			intTtt = (InterfaceTicTacToe) Naming.lookup("rmi://localhost:8000/tictactoe");

			// Récupération de l'id de la partie actuelle
			idPartie = intTtt.numPartie();

			// On remet la fin à 0 ce qui évite d'avoir possiblement une partie annulée dès qu'un joueur en rejoint une
			intTtt.setFin(idPartie, 0);

			// Récupération du nombre de joueurs
			nbJoueur = intTtt.getNombreJoueur(idPartie);
			// On incrémente ce même nombre car on vient de rejoindre la partie
			intTtt.setNombreJoueur(idPartie, nbJoueur + 1);

			labelIdPartie.setText("Partie n°" + idPartie);

			// On dessine la grille du morpion
			label2.setStyle("-fx-border-color: black; -fx-border-style: hidden solid hidden solid;");
			label5.setStyle("-fx-border-color: black;");
			label8.setStyle("-fx-border-color: black; -fx-border-style: hidden solid hidden solid;");
			label4.setStyle("-fx-border-color: black; -fx-border-style: solid hidden solid hidden;");
			label6.setStyle("-fx-border-color: black; -fx-border-style: solid hidden solid hidden;");

			// On récupère le tableau du contenu des labels
			labels = intTtt.getLabels(idPartie);


			/* On remplit ce tableau avec un String vide pour éviter qu'un joueur rejoignant une partie
			 * qui a été quittée reprenne le contenu des labels non vide */
			for (int i = 0; i < 9; i++)
				labels[i] = "";

			// On set le nouveau tableau
			intTtt.setLabels(idPartie, labels);

			// On applique un handler à chaque label qui permet de détecter le clic de la souris
			label1.setOnMouseClicked(handler);
			label2.setOnMouseClicked(handler);
			label3.setOnMouseClicked(handler);
			label4.setOnMouseClicked(handler);
			label5.setOnMouseClicked(handler);
			label6.setOnMouseClicked(handler);
			label7.setOnMouseClicked(handler);
			label8.setOnMouseClicked(handler);
			label9.setOnMouseClicked(handler);

			// Désactivation des labels tant que la partie n'a pas commencé
			bloquerLabel();

			if (intTtt.getNombreJoueur(idPartie) == 1) {	// Si le nombre de joueurs vaut 1..
				tourJoueur.setText("En attente d'un deuxième joueur.."); 	// Le label du tour affiche en attente le temps de l'attente
				numJoueur = 1;	// ..on attribut ce nombre en tant que numéro du joueur..

				new Thread(attente).start(); 	// ..et on lance le thread qui va attendre un deuxième joueur
			} else {
				numJoueur = 2;	// Sinon on le numéro du joueur est 2
				labelJoueur.setText("Vous êtes le joueur " + numJoueur + ", forme : O");
				System.out.println("JOUEUR " + numJoueur);

				// Si le joueur est le numéro 2 on peut lancer la partie et on affiche que c'est au joueur 1 de joueur
				tourJoueur.setText("Au tour du joueur " + intTtt.getTourActuel(idPartie));

				// On set le tour au tour numéro 1 car le joueur 1 commence
				intTtt.setTourActuel(idPartie, tour);
			}

		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			System.out.println("RMI exception" + e);
		}

		// On lance le thread qui nous permettra d'écouter le serveur quand le tour changera
		new Thread(joue).start();
	}

	/**
	 * Effectue un tour
	 */
	public void tour() {
		try {
			majLabels();	// On met à jour les labels

			if (intTtt.getTourActuel(idPartie) == numJoueur)	// Le tour vaut 1 et que le numéro du joueur est le 1..
				tourJoueur.setText("A votre tour");			// ..on affiche que c'est à son tour de joueur
			else
				tourJoueur.setText("Tour de l'adversaire");		// Sinon c'est que c'est au joueur 2 de jouer

			// Si c'est au joueur 1 ou au joueur 2 de jouer, on réactive les labels
			if (intTtt.getTourActuel(idPartie) == 1 && numJoueur == 1
					|| intTtt.getTourActuel(idPartie) == 2 && numJoueur == 2) {
				label1.setDisable(false);
				label2.setDisable(false);
				label3.setDisable(false);
				label4.setDisable(false);
				label5.setDisable(false);
				label6.setDisable(false);
				label7.setDisable(false);
				label8.setDisable(false);
				label9.setDisable(false);
			} else {						// Sinon on les désactive car ce n'est pas à son tour de jouer
				label1.setDisable(true);
				label2.setDisable(true);
				label3.setDisable(true);
				label4.setDisable(true);
				label5.setDisable(true);
				label6.setDisable(true);
				label7.setDisable(true);
				label8.setDisable(true);
				label9.setDisable(true);
			}

		} catch (RemoteException e) {
			System.out.println("Tour exception " + e);
		}
	}

	/**
	 * Appose la forme du joueur dans le label sélectionné
	 * @param l label sélectionné par le joueur
	 */
	public void poseForme(Label l) {
		try {
			if (intTtt.getTourActuel(idPartie) == 1) {	// Si c'est au joueur 1 de jouer..
				l.setText("X");					// ..alors on appose la forme X..
				l.setTextFill(Color.BLUE);		//..et on la colore en bleu pour rendre plus joli et différencier les joueurs
				intTtt.setTourActuel(idPartie, 2);	// Incrémente le tour ce qui permet au joueur 2 de jouer
			} else {		// Si c'est au joueur 2 de jouer..
				l.setText("O");		// ..alors on appose la forme O..
				l.setTextFill(Color.RED);	//..et on la colore en rouge pour rendre plus joli et et différencier les joueurs
				intTtt.setTourActuel(idPartie, 1);	// Décrémente le tour ce qui permet au joueur 1 de jouer
			}
		} catch (RemoteException e) {
			System.out.println("Tour exception " + e);
		}
	}

	/**
	 * handler qui permet de détecter le clic de la souris sur un label
	 */
	EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			try {
				/* Si le label reçoit un clic et qu'il n'est pas vide alors on appose la forme
				 * puis on set la forme apposée dans le tableau du contenu des labels */
				if (e.getSource() == label1 && label1.getText() == "") {
					poseForme(label1);
					labels[0] = intTtt.getFormeJoue(idPartie);
				}
				if (e.getSource() == label2 && label2.getText() == "") {
					poseForme(label2);
					labels[1] = intTtt.getFormeJoue(idPartie);
				}
				if (e.getSource() == label3 && label3.getText() == "") {
					poseForme(label3);
					labels[2] = intTtt.getFormeJoue(idPartie);
				}
				if (e.getSource() == label4 && label4.getText() == "") {
					poseForme(label4);
					labels[3] = intTtt.getFormeJoue(idPartie);
				}
				if (e.getSource() == label5 && label5.getText() == "") {
					poseForme(label5);
					labels[4] = intTtt.getFormeJoue(idPartie);
				}
				if (e.getSource() == label6 && label6.getText() == "") {
					poseForme(label6);
					labels[5] = intTtt.getFormeJoue(idPartie);
				}
				if (e.getSource() == label7 && label7.getText() == "") {
					poseForme(label7);
					labels[6] = intTtt.getFormeJoue(idPartie);
				}
				if (e.getSource() == label8 && label8.getText() == "") {
					poseForme(label8);
					labels[7] = intTtt.getFormeJoue(idPartie);
				}
				if (e.getSource() == label9 && label9.getText() == "") {
					poseForme(label9);
					labels[8] = intTtt.getFormeJoue(idPartie);
				}
				e.consume();

				// On set le tableau du contenu des labels afin que l'autre joueur récupère les changements
				intTtt.setLabels(idPartie, labels);

				// On envoie le tour et le contenu de chaque label et si la méthode retourne vrai alors on set la fin de la partie avec comme numéro 1
				if (intTtt.verificationVictoire(idPartie, tour, label1.getText(), label2.getText(), label3.getText(),
						label4.getText(), label5.getText(), label6.getText(), label7.getText(), label8.getText(),
						label9.getText()) == true) {
					intTtt.setFin(idPartie, 1);
				} else if (intTtt.verificationMatchNul(idPartie) == true) {	/* Sinon si il y a match nul on set la fin de la partie avec comme numéro 2 */
					intTtt.setFin(idPartie, 2);
				}

				// On appelle la fonction tour afin de changer de tour
				tour();

			} catch (RemoteException re) {
				System.out.println(re);
			}
		}
	};

	/**
	 * Désactive les labels
	 */
	public void bloquerLabel() {
		label1.setDisable(true);
		label2.setDisable(true);
		label3.setDisable(true);
		label4.setDisable(true);
		label5.setDisable(true);
		label6.setDisable(true);
		label7.setDisable(true);
		label8.setDisable(true);
		label9.setDisable(true);
	}

	/**
	 * Met à jour le contenu des labels de la grille
	 * @param void
	 * @throws RemoteException
	 */
	public void majLabels() throws RemoteException {
		// On récupère le contenu des labels pour afficher chaque changement
		labels = intTtt.getLabels(idPartie);

		label1.setText(labels[0]);
		label2.setText(labels[1]);
		label3.setText(labels[2]);
		label4.setText(labels[3]);
		label5.setText(labels[4]);
		label6.setText(labels[5]);
		label7.setText(labels[6]);
		label8.setText(labels[7]);
		label9.setText(labels[8]);
	}

	/*
	 * Quitte la fenêtre en arrêtant toutes les tasks en cours
	 */
	@FXML
	public void quitter() throws RemoteException {
		if (attente.cancel())
			System.out.println("Attente cancel");
		if (joue.cancel())
			System.out.println("Joue cancel");

		try {
			intTtt.setFin(idPartie, 3);	// On set la fin de la partie avec comme numéro 3
		} catch (RemoteException e) {
			System.out.println(e);
		}

		// On set le nombre de joueur à 0 pour libérer la partie seulement si le joueur était seul
		// Cela permet déviter des problèmes de superposition de partie
		if (intTtt.getNombreJoueur(idPartie) == 1)
			intTtt.setNombreJoueur(idPartie, 0);
		//}

		Stage stage = (Stage) btnQuitter.getScene().getWindow();	// On récupère la fenêtre de jeu..
		stage.close();				// ..puis on la ferme
	}

	/**
	 * Affiche une alerte qui annonce si le joueur a gagné ou perdu
	 */
	private void afficheResultat() {
		System.out.println("------FIN Victoire joueur " + numJoueur + " ------");

		Alert alert = new Alert(AlertType.INFORMATION);

		try {
			if (intTtt.getFormeJoue(idPartie).equals("X")) { 	/* Si la forme qui vient d'être jouée avant
																la fin de partie est le X, c'est le joueur 1 qui gagne */
				if (numJoueur == 1) {	// On affiche alors la victoire du joueur 1
					alert.setContentText("Vous avez gagné !!");
					alert.setHeaderText("Victoire");
				} else {	// Et la défaite du joueur 2
					alert.setContentText("Vous avez perdu..");
					alert.setHeaderText("Défaite");
				}
			} else {			// Sinon c'est le joueur 2 qui gagne
				if (numJoueur == 1) {	// On affiche alors la victoire du joueur 2
					alert.setContentText("Vous avez perdu..");
					alert.setHeaderText("Défaite");
				} else {	// Et la défaite du joueur 1
					alert.setContentText("Vous avez gagné !!");
					alert.setHeaderText("Victoire");
				}
			}
		} catch (RemoteException e) {
			System.out.println(e);
		}

		/* Le code suivant permet d'appliquer l'icône de l'application à la fenêtre d'alert
		 * en la transformant en pane */
		DialogPane pane = alert.getDialogPane();

		ObjectProperty<ButtonType> result = new SimpleObjectProperty<>();
		for (ButtonType type : pane.getButtonTypes()) {
			ButtonType resultValue = type;
			((Button) pane.lookupButton(type)).setOnAction(e -> {
				result.set(resultValue);
				pane.getScene().getWindow().hide();
			});
		}

		pane.getScene().setRoot(new Label());
		Scene scene = new Scene(pane);

		Stage dialog = new Stage();
		dialog.setTitle("Joueur " + numJoueur);		// On affiche le numéro du joueur en titre pour pouvoir la différencier
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.getIcons().add(new Image("/mvc/vue/images/icon.svg.png")); 	// On applique l'icône de l'application pour embellir l'alerte

		dialog.show();
	}

	/**
	 * Affiche une alerte qui annonce un match nul
	 */
	private void afficheMatchNul() {
		System.out.println("------FIN match nul joueur " + numJoueur + " ------");

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Match nul");
		alert.setContentText("Aucun des deux joueurs n'a gagné.");

		/* Le code suivant permet d'appliquer l'icône de l'application à la fenêtre d'alert
		 * en la transformant en pane */
		DialogPane pane = alert.getDialogPane();

		ObjectProperty<ButtonType> result = new SimpleObjectProperty<>();
		for (ButtonType type : pane.getButtonTypes()) {
			ButtonType resultValue = type;
			((Button) pane.lookupButton(type)).setOnAction(e -> {
				result.set(resultValue);
				pane.getScene().getWindow().hide();
			});
		}

		pane.getScene().setRoot(new Label());
		Scene scene = new Scene(pane);

		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setScene(scene);
		dialog.setTitle("Joueur " + numJoueur);		// On affiche le numéro du joueur en titre pour pouvoir la différencier
		dialog.setResizable(false);
		dialog.getIcons().add(new Image("/mvc/vue/images/icon.svg.png"));	// On applique l'icône de l'application pour embellir l'alerte

		dialog.show();
	}

	/**
	 * Préviens le joueur que son adversaire a quitté la partie
	 */
	private void quitterMatch() {
		System.out.println("------FIN joueur quitte ------");

		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Un joueur a quitté le match");
		alert.setContentText("Votre adversaire a quitté la partie");

		/* Le code suivant permet d'appliquer l'icône de l'application à la fenêtre d'alert
		 * en la transformant en pane */
		DialogPane pane = alert.getDialogPane();

		ObjectProperty<ButtonType> result = new SimpleObjectProperty<>();
		for (ButtonType type : pane.getButtonTypes()) {
			ButtonType resultValue = type;
			((Button) pane.lookupButton(type)).setOnAction(e -> {
				result.set(resultValue);
				pane.getScene().getWindow().hide();
			});
		}

		pane.getScene().setRoot(new Label());
		Scene scene = new Scene(pane);

		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setScene(scene);
		dialog.setTitle("Joueur " + numJoueur);		// On affiche le numéro du joueur en titre pour pouvoir la différencier
		dialog.setResizable(false);
		dialog.getIcons().add(new Image("/mvc/vue/images/icon.svg.png"));	// On applique l'icône de l'application pour embellir l'alerte

		// Montre l'alerte puis attend que le joueur ferme la fenêtre pour exécuter le code suivant
		dialog.showAndWait();

		Stage stage = (Stage) btnQuitter.getScene().getWindow();	// On récupère la fenêtre de jeu..
		stage.close();								// ..puis on la ferme
	}

	/**
	 * Task qui tournera jusqu'a la fin de la partie et qui permet de changer de tour
	 */
	Task<Void> joue = new Task<Void>() {
		@Override
		public Void call() {
			try {
				while (intTtt.getFinPartie(idPartie) == 0) {	// Tant que la partie est en cours..
					if (attente.isDone() && numJoueur == 1 || numJoueur == 2) {		/* ..si l'attente du deuxième joueur est fini
																				et qu'on est le joueur 1 ou alors qu'on est le joueur 2 */

						/* On est obligé d'utiliser Platform.runLater en défissant un nouveau Runnable car JavaFX n'est pas thread-safe
						 * et Platform.runLater nous permet d'effectuer des modifications graphique dans une task sans planter */
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								tour();		// On appelle tour pour lancer un tour
							}
						});

						TimeUnit.SECONDS.sleep(1); // On met en pause la task 1 seconde sinon la task plante car ça va trop vite
					}
				}
				//Quand la partie est finie on passe au reste du code

				/* On est obligé d'utiliser Platform.runLater en défissant un nouveau Runnable car JavaFX n'est pas thread-safe
				 * et Platform.runLater nous permet d'effectuer des modifications graphique dans une task sans planter */
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							bloquerLabel();		// Blocage des labels
							tourJoueur.setText("Fin de la partie");		// On informe que c'est la fin de la partie
							if (intTtt.getFinPartie(idPartie) == 1) {	// Si 1 : victoire d'un des joueurs..
								majLabels();	// Mise à jour des labels
								TimeUnit.SECONDS.sleep(1);
								afficheResultat();	// ..on affiche le résultat
							} else if (intTtt.getFinPartie(idPartie) == 2) {	// Si 2 : match nul..
								majLabels();	// Mise à jour des labels
								TimeUnit.SECONDS.sleep(1);
								afficheMatchNul();	// ..on affiche un match nul
							} else	// Sinon c'est qu'un des joueurs a quitté la partie
								quitterMatch();		// On prévient son adversaire
						} catch (RemoteException | InterruptedException e) {
							System.out.println(e);
						}
					}
				});

			} catch (RemoteException | InterruptedException e) {
			}

			return null;
		}
	};

	/**
	 * Task qui permet au premier joueur d'attendre un deuxième joueur
	 */
	Task<Void> attente = new Task<Void>() {
		@Override
		public Void call() {
			try {
				// Tant que le serveur ne dit pas qu'il y a 2 joueurs on attend
				while (intTtt.getNombreJoueur(idPartie) == 1) {
					System.out.println("en attente..");
					TimeUnit.SECONDS.sleep(2);	// On met la task en pause 2 secondes à chaque tour pour ne pas faire planter la fenetre
				}
				// On sort de la boycle quand le client reçoit qu'il y a 2 joueurs

				/* On est obligé d'utiliser Platform.runLater en défissant un nouveau Runnable car JavaFX n'est pas thread-safe
				 * et Platform.runLater nous permet d'effectuer des modifications graphique dans une task sans planter */
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// On affiche que le joueur est le joueur 1
						labelJoueur.setText("Vous êtes le joueur " + numJoueur + ", forme : X");
						System.out.println("JOUEUR " + numJoueur);

						try {
							// On affiche que c'est au joueur 1 de jouer
							tourJoueur.setText("Au tour du joueur " + intTtt.getTourActuel(idPartie));
							intTtt.setTourActuel(idPartie, tour);	// On set le tour au tour numéro 1 car c'est le joueur 1 qui commence
						} catch (RemoteException e) {
							System.out.println("Attente exception : " + e);
						}
					}
				});

			} catch (RemoteException | InterruptedException e) {
			}
			return null;
		}
	};

}