package mvc.modele.tictactoe;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceTicTacToe extends Remote {

	/**
	 * Renvoie l'id d'une partie
	 *
	 * @param void
	 * @return L'id incrémenté si jamais une partie contient 2 joueurs, sinon renvoie
	 *         l'id de la partie vide ou occupée par un seul joueur
	 */
	public int numPartie() throws RemoteException;

	/**
	 * Vérifie si l'un des 2 joueurs a gagné
	 *
	 * @param id L'id de la partie voulue
	 * @param tour Le tour actuel
	 * @param l le contenu de chaque label
	 * @return vrai si un joueur a gagné, faux sinon
	 */
	public boolean verificationVictoire(int id, int tour, String l1, String l2, String l3, String l4, String l5,
			String l6, String l7, String l8, String l9) throws RemoteException;

	/**
	 * Vérifie si il y a match nul
	 *
	 * @param id L'id de la partie voulue
	 * @return vrai si il y a match nul, faux sinon
	 */
	public boolean verificationMatchNul(int id) throws RemoteException;

	/**
	 * Renvoie la forme qui a été joué au tour d'avant
	 *
	 * @param id L'id de la partie voulue
	 * @return La forme qui vient d'être joué
	 */
	public String getFormeJoue(int id) throws RemoteException;

	/**
	 * Renvoie le nombre de joueurs
	 *
	 * @param id L'id de la partie voulue
	 * @return Le nombre de joueurs
	 */
	public int getNombreJoueur(int id) throws RemoteException;

	/**
	 * Set le nombre de joueurs
	 *
	 * @param id L'id de la partie voulue
	 * @param joueur 0, 1 ou 2
	 * @return void
	 */
	public void setNombreJoueur(int id, int joueur) throws RemoteException;

	/**
	 * Renvoie le tour actuel
	 *
	 * @param id L'id de la partie voulue
	 * @return Le tour actuel
	 */
	public int getTourActuel(int id) throws RemoteException;

	/**
	 * Set le tour actuel
	 *
	 * @param id L'id de la partie voulue
	 * @param tour le tour actuel
	 * @return void
	 */
	public void setTourActuel(int id, int tour) throws RemoteException;

	/**
	 * Renvoie la forme actuelle
	 *
	 * @param id L'id de la partie voulue
	 * @return La forme actuelle
	 */
	public String getFormeActuel(int id) throws RemoteException;


	/**
	 * Set la forme qui doit être jouée
	 *
	 * @param id L'id de la partie voulue
	 * @param forme "X" ou "O"
	 * @return void
	 */
	public void setFormeActuel(int id, String forme) throws RemoteException;

	/**
	 * Renvoie un tableau contenant le texte de chaque label
	 *
	 * @param id L'id de la partie voulue
	 * @return Un tableau contenant le texte de chaque label
	 */
	public String[] getLabels(int id) throws RemoteException;

	/**
	 * Set le contenu dans chaque label
	 *
	 * @param id L'id de la partie voulue
	 * @param l un tableau de String contenant le texte des labels
	 * @return void
	 */
	public void setLabels(int id, String[] l) throws RemoteException;

	/**
	 * Set la fin de la partie
	 *
	 * @param id L'id de la partie voulue
	 * @param fin 0 si la partie n'est pas finie, 1 si l'un des joueurs a gagné, 2 si il y a match nul, 3 si l'un des joueurs a quitté la partie
	 * @return void
	 */
	public void setFin(int id, int fin) throws RemoteException;

	/**
	 * Renvoie si la partie est finie et le numéro correspondant à comment elle s'est terminée
	 *
	 * @param id L'id de la partie voulue
	 * @return 0 si la partie n'est pas finie, 1 si l'un des joueurs a gagné, 2 si il y a match nul, 3 si l'un des joueurs a quitté la partie
	 */
	public int getFinPartie(int id) throws RemoteException;

}
