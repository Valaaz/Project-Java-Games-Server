package mvc.modele.tictactoe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

@SuppressWarnings("serial")
public class ImplTicTacToe extends UnicastRemoteObject implements InterfaceTicTacToe {

	int idPartie = 1;
	/* HashMap avec comme clé l'id d'une partie et comme valeur une instanciation d'une partie de TicTacToe */
	HashMap<Integer, PartieTicTacToe> listePartie = new HashMap<Integer, PartieTicTacToe>();

	public ImplTicTacToe() throws RemoteException {
		super();
	}

	@Override
	public int numPartie() throws RemoteException {
		/*
		 * Parcours de la liste des parties pour vérifier s'il y a un joueur seul ou 2
		 * joueurs
		 */
		Set<Integer> listKeys = listePartie.keySet();
		Iterator<Integer> iterateur = listKeys.iterator();
		while (iterateur.hasNext()) {
			Object key = iterateur.next();
			if (listePartie.get(key)
					.getNbJoueur() != 2) { /* Si il n'y a pas déjà 2 joueurs, on renvoie l'id de la partie actuelle */
				return (int) key;
			}
		}

		PartieTicTacToe nouvellePartie = new PartieTicTacToe();
		listePartie.put(idPartie, nouvellePartie); /* Sinon on le place dans la liste avec une nouvelle instance de PartieTicTacToe */

		return idPartie++; // et on renvoie un id incrémenté
	}

	// On a du passer le contenu de chaque label en paramètres à cause d'une exception mais une amélioration est possible
	@Override
	public boolean verificationVictoire(int id, int tour, String l1, String l2, String l3, String l4, String l5,
			String l6, String l7, String l8, String l9) throws RemoteException {

		/* On vérifie, grâce à la fonction getFormeJoue(), si une série de 3 labels horizontaux, verticaux ou
		 * diagonaux correspond à la forme qui a été jouée
		 * Si oui on renvoie true, sinon false */
		if (l1.equals(getFormeJoue(id)) && l2.equals(getFormeJoue(id))
				&& l3.equals(getFormeJoue(id))) {
			System.out.println("Victoire des " + getFormeJoue(id) + " !!");
			return true;
		}
		if (l4.equals(getFormeJoue(id)) && l5.equals(getFormeJoue(id))
				&& l6.equals(getFormeJoue(id))) {
			System.out.println("Victoire des " + getFormeJoue(id) + " !!");
			return true;
		}
		if (l7.equals(getFormeJoue(id)) && l8.equals(getFormeJoue(id))
				&& l9.equals(getFormeJoue(id))) {
			System.out.println("Victoire des " + getFormeJoue(id) + " !!");
			return true;
		}
		if (l1.equals(getFormeJoue(id)) && l4.equals(getFormeJoue(id))
				&& l7.equals(getFormeJoue(id))) {
			System.out.println("Victoire des " + getFormeJoue(id) + " !!");
			return true;
		}
		if (l2.equals(getFormeJoue(id)) && l5.equals(getFormeJoue(id))
				&& l8.equals(getFormeJoue(id))) {
			System.out.println("Victoire des " + getFormeJoue(id) + " !!");
			return true;
		}
		if (l3.equals(getFormeJoue(id)) && l6.equals(getFormeJoue(id))
				&& l9.equals(getFormeJoue(id))) {
			System.out.println("Victoire des " + getFormeJoue(id) + " !!");
			return true;
		}
		if (l1.equals(getFormeJoue(id)) && l5.equals(getFormeJoue(id))
				&& l9.equals(getFormeJoue(id))) {
			System.out.println("Victoire des " + getFormeJoue(id) + " !!");
			return true;
		}
		if (l7.equals(getFormeJoue(id)) && l5.equals(getFormeJoue(id))
				&& l3.equals(getFormeJoue(id))) {
			System.out.println("Victoire des " + getFormeJoue(id) + " !!");
			return true;
		}
		return false;
	}

	@Override
	public boolean verificationMatchNul(int id) throws RemoteException {
		// Récuperation du contenu des labels de la partie voulue
		String[] l = getLabels(id);

		// Si chaque label n'est pas vide et que la vérification de victoire d'un joueur n'a pas retourné true alors c'est qu'il y a match nul
		if (l[0] != "" && l[1] != "" && l[2] != "" && l[3] != "" && l[4] != "" && l[5] != "" && l[6] != "" && l[7] != ""
				&& l[8] != "") {
			System.out.println("Match nul !!");
			return true;
		}

		return false;
	}

	@Override
	public String getFormeJoue(int id) throws RemoteException {
		// On récupère le tour actuel et on le décrémente pour obtenir le tour qui a été joué
		int tour = listePartie.get(id).getTour() - 1;

		if (tour == 1)	// Si le tour vaut 1 on renvoie la forme X
			return "X";
		else
			return "O";	// Sinon on renvoie la forme O
	}

	// Méthodes appelants les gettes et setters de l'instance de la partie voulue

	@Override
	public int getNombreJoueur(int id) throws RemoteException {
		return listePartie.get(id).getNbJoueur();
	}

	@Override
	public void setNombreJoueur(int id, int joueur) throws RemoteException {
		listePartie.get(id).setNbJoueur(joueur);
	}

	@Override
	public int getTourActuel(int id) throws RemoteException {
		return listePartie.get(id).getTour();
	}

	@Override
	public void setTourActuel(int id, int tour) throws RemoteException {
		listePartie.get(id).setTour(tour);
	}

	@Override
	public String getFormeActuel(int id) throws RemoteException {
		return listePartie.get(id).getForme();
	}

	@Override
	public void setFormeActuel(int id, String forme) throws RemoteException {
		listePartie.get(id).setForme(forme);
	}

	@Override
	public String[] getLabels(int id) throws RemoteException {
		return listePartie.get(id).getTabLabel();
	}

	@Override
	public void setLabels(int id, String[] l) throws RemoteException {
		listePartie.get(id).setTabLabel(l);
	}

	@Override
	public int getFinPartie(int id) throws RemoteException {
		return listePartie.get(id).isFin();
	}

	@Override
	public void setFin(int id, int fin) throws RemoteException {
		listePartie.get(id).setFin(fin);
	}

}
