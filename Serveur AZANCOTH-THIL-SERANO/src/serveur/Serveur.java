package serveur;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import mvc.modele.allumettes.ImplAllumettes;
import mvc.modele.pendu.ImplPendu;
import mvc.modele.tictactoe.ImplTicTacToe;

public class Serveur {
	public static void main(String[] argv) {
		try {
			int port = 8000;
			LocateRegistry.createRegistry(port);

			ImplPendu pendu = new ImplPendu();
			Naming.rebind("rmi://localhost:" + port + "/pendu", pendu);

			ImplAllumettes allumette = new ImplAllumettes();
			Naming.rebind("rmi://localhost:" + port + "/allumette", allumette);

			ImplTicTacToe tictactoe = new ImplTicTacToe();
			Naming.rebind("rmi://localhost:" + port + "/tictactoe", tictactoe);

			System.out.println("Serveur de jeu prêt !");
		} catch (Exception e) {
			System.out.println("Serveur de jeu échec " + e);
		}
	}

}
