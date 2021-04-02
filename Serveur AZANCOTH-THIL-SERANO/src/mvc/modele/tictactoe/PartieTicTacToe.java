package mvc.modele.tictactoe;

public class PartieTicTacToe {

	// Initialisation des variables communes entre le serveur et le client
	private int idPartie = 1;
	private int nbJoueur = 0;
	private int tour = 1;
	String[] tabLabel = { "", "", "", "", "", "", "", "", "" };
	String forme;
	private int fin = 0;

	// Getters et setters de chaque variable
	public int getIdPartie() {
		return idPartie;
	}

	public void setIdPartie(int idPartie) {
		this.idPartie = idPartie;
	}

	public int getNbJoueur() {
		return nbJoueur;
	}

	public void setNbJoueur(int nbJoueur) {
		this.nbJoueur = nbJoueur;
	}

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public String[] getTabLabel() {
		return tabLabel;
	}

	public void setTabLabel(String[] tabLabel) {
		this.tabLabel = tabLabel;
	}

	public String getForme() {
		return forme;
	}

	public void setForme(String forme) {
		this.forme = forme;
	}

	public int isFin() {
		return fin;
	}

	public void setFin(int fin) {
		this.fin = fin;
	}

}
