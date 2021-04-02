package mvc.controleur;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControleurMenuPrincipal {

	@FXML
	private MenuItem menuItemQuitter;
	@FXML
	private MenuItem menuItemPendu, menuItemAllumettes, menuItemTicTacToe;
	@FXML
	private Button boutonPendu, boutonTicTacToe, boutonAllumette;

	// Chaque méthode appelle un jeu

	@FXML
	public void jeuDuPendu() {
		try {
			URL fxmlURL = getClass().getResource("/mvc/vue/FichePendu.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Parent root = fxmlLoader.load();

			Stage stage = new Stage();

			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Pendu");
			stage.getIcons().add(new Image("/mvc/vue/images/icon.svg.png"));
			stage.setScene(new Scene(root, 500, 500));
			stage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void jeuDesAllumettes() {
		try {
			URL fxmlURL = getClass().getResource("/mvc/vue/FicheAllumettes.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Parent root = fxmlLoader.load();

			Stage stage = new Stage();

			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Allumettes");
			stage.getIcons().add(new Image("/mvc/vue/images/icon.svg.png"));
			stage.setScene(new Scene(root, 600, 400));
			stage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void jeuDuTicTacToe() {
		try {
			URL fxmlURL = getClass().getResource("/mvc/vue/FicheTicTacToe.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Parent root = fxmlLoader.load();

			// Récupération du controleur afin de d'appeler la méthode quitter
			ControleurJeuTicTacToe jeuTtt = fxmlLoader.getController();
			Stage stage = new Stage();
			// On set la méthode quitter qui sera appelée à la fermeture de la fenetre par la croix rouge
			stage.setOnCloseRequest(e -> {
				try {
					jeuTtt.quitter();
				} catch (RemoteException e1) {
					System.out.println(e);
				}
			});

			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("TicTacToe");
			stage.getIcons().add(new Image("/mvc/vue/images/icon.svg.png"));
			stage.setScene(new Scene(root, 500, 500));
			stage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Définition des méthodes pour chaque menu item

	@FXML
	public void quitterApplication() {
		System.exit(0);
	}

	@FXML
	public void reglesPendu() {
		String regles = "Le pendu se joue avec l'ordinateur. Vous devez deviner le mot que l'ordinateur a choisi en sélectionnant une lettre."
				+ " Un trait apparaît sur le dessin du pendu à chaque mauvaise réponse. Si vous trouver le mot vous gagnez, si le dessin arrive au bout de sa forme vous perdez.";

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Règles du Pendu");
		alert.setHeaderText("Jeu du Pendu");
		alert.setContentText(regles);
		alert.setResizable(true);

		// Permet à la fenêtre de s'adapter à la taille du contenu
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.show();
	}

	@FXML
	public void reglesAllumettes() {
		String regles = "Ce jeu se joue avec l'ordinateur en tour par tour. Un nombre impair d'allumettes est disposé. Chacun des joueurs prend une ou 2 allumettes quand vient son tour."
				+ " Le jeu s'arrête quand le tas est vide et le gagant est celui qui possède un nombre impair d'allumettes.";

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Règles du jeu des Allumettes");
		alert.setHeaderText("Jeu des Allumettes");
		alert.setContentText(regles);
		alert.setResizable(true);

		// Permet à la fenêtre de s'adapter à la taille du contenu
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.show();
	}

	@FXML
	public void reglesTicTacToe() {
		String regles = "Le Tic-Tac-Toe ou Morpion se joue à 2 joueurs en tour par tour sur une grille en 3x3. Le gagnant est celui qui arrive aligner 3 de ses formes"
				+ " que ce soit horizontalement, verticalement ou en diagonal. L'un des joueurs joue avec des ronds et l'autre avec des croix.";

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Règles du Tic-Tac-Toe");
		alert.setHeaderText("Tic-Tac-Toe");
		alert.setContentText(regles);
		alert.setResizable(true);

		// Permet à la fenêtre de s'adapter à la taille du contenu
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.show();
	}

}
