package mvc.modele.allumettes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;

@SuppressWarnings("serial")
public class ImplAllumettes extends UnicastRemoteObject implements InterfaceAllumettes {

	HashMap<Integer, PartieAllumettes> listePartie = new HashMap<Integer, PartieAllumettes>();

	int idPartie = 0; // Initialisation de l'id Partie à 0

	/*
	 * Constructeur
	 */

	public ImplAllumettes() throws RemoteException {
		super();

	}

	/*
	 * Fonction permettant à l'IA de jouer de façon autonome
	 */

	@Override
	public void coupIA(int id) throws RemoteException {
		int r; // variable permettant de stocker le nombre d'allumettes que l'IA prendra
		int nbAllumettes = getNbAllumettePartie(id); // variable récupérant le nombre d'allumettes en partie
		if (nbAllumettes == 1) { // Si il reste qu'une seule allumette en jeu, l'IA est forcée de prendre une
			r = 1; // seule allumette
		} else {
			Random coupOrdi = new Random(); // Sinon l'IA prend de façon aléatoire 1 à 2 allumette(s) par tour
			r = coupOrdi.nextInt(2) + 1;
		}

		setNbAllumettePartie(id, nbAllumettes - r); // set du nombre d'allumettes en partie en prenant les allumettes
		// totales et en retirant le nombre d'allumettes pris par l'IA
	}

	@Override
	public int getTour(int id) throws RemoteException {
		return listePartie.get(id).getTour(); // récupération du tour avec id
	}

	@Override
	public void setTour(int id, int tour) throws RemoteException {
		listePartie.get(id).setTour(tour); // Mise en application du tour avec id
	}

	/*
	 * Fonction permettant de générer aléatoirement un nombre d'allumettes impaires
	 * en partie (entre 7 et 19 allumettes)
	 */

	@Override
	public int generationAleatoireAllumettes(int id) throws RemoteException {

		int nbAllumettesPartie = new Random().nextInt(12) + 7; // genere un nombre d'allumettes entre 7 et 19
		if (nbAllumettesPartie % 2 == 0) { // Si le nombre d'allumettes est paire lors de la génération
			nbAllumettesPartie++; // faire +1 allumette en partie

		}

		return nbAllumettesPartie; // retour du nombre d'allumettes en partie une fois l'opération effectuée
	}

	/*
	 * Fonction permettant de soustraire les allumettes en partie
	 */

	@Override
	public void soustraireAllumettes(int id, int nbRetirer) throws RemoteException {
		int nombreAllumette = listePartie.get(id).getNbAllumettesPartie(); // création d'une variable récupérant le
		// nombre d'allumettes d'une partie
		nombreAllumette -= nbRetirer; // nouvelle valeur de la variable en enlevant le nombre d'allumettes à
		// soustraire
		setNbAllumettePartie(id, nombreAllumette); // set du nouveau nombre d'allumettes en partie après l'opération

	}

	/*
	 * Récupération du nombre d'allumettes du joueur
	 */

	@Override
	public int getNombreAllumettesJoueur(int id) throws RemoteException {

		return listePartie.get(id).getNbAllumetteJoueur();
	}

	/*
	 * set du nombre allumettes du joueur
	 */

	@Override
	public void setNombreAllumettesJoueur(int id, int nbAllumettesJoueur) throws RemoteException {
		listePartie.get(id).setNbAllumetteJoueur(nbAllumettesJoueur);
	}

	/*
	 * récupération du nombre d'allumettes en partie
	 */

	@Override
	public int getNbAllumettePartie(int id) throws RemoteException {

		return listePartie.get(id).getNbAllumettesPartie();
	}

	/*
	 * set du nombre d'allumettes en partie
	 */

	@Override
	public void setNbAllumettePartie(int id, int nbAllumettes) throws RemoteException {
		listePartie.get(id).setNbAllumettesPartie(nbAllumettes);

	}

	/*
	 * Fonction permettant de créer une nouvelle partie
	 */

	@Override
	public int nouvellePartie() throws RemoteException {
		idPartie++; // +1 partie à chaque nouvelle partie
		PartieAllumettes nouvellePartie = new PartieAllumettes(); // Utilisation de la classe PartieAllumettes pour une
		// nouvelle partie d'allumettes
		listePartie.put(idPartie, nouvellePartie);

		return idPartie; // retour de la partie créée
	}

}
