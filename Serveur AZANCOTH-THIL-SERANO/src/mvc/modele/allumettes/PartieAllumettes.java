package mvc.modele.allumettes;

public class PartieAllumettes {

	// Initialisation des variables communes entre le serveur et le client
	private int nbAllumettesPartie = 0; // Initialisation du nombre d'allumettes en partie à 0
	private int nbAllumetteJoueur = 0; // Initialisation du nombre d'allumettes du joueur à 0
	private int tour = 1; // Initialisation du nombre d'allumettes du joueur à 0

	/*
	 * génération de tous les getters et setters de nos variables présentes
	 */

	public int getNbAllumettesPartie() {
		return nbAllumettesPartie;
	}

	public void setNbAllumettesPartie(int nbAllumettesPartie) {
		this.nbAllumettesPartie = nbAllumettesPartie;
	}

	public int getNbAllumetteJoueur() {
		return nbAllumetteJoueur;
	}

	public void setNbAllumetteJoueur(int nbAllumetteJoueur) {
		this.nbAllumetteJoueur = nbAllumetteJoueur;
	}

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

}
