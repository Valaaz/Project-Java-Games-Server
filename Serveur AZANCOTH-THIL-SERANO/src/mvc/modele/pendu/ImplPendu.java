package mvc.modele.pendu;

import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;

@SuppressWarnings("serial")
public class ImplPendu extends UnicastRemoteObject implements InterfacePendu {

	//On associe l'id d'une partie avec son instance (mot, motCache, nbErreurs)
	HashMap<Integer, PartiePendu> listePartie = new HashMap<Integer, PartiePendu>();

	//tableau de String contenant les mots que le joueur doit trouver
	String dictionnaire[] = {"aquarium", "banquise", "brocante", "clavecin", "logiciel", "objectif", 
			"parcours", "tabouret", "tourisme", "triangle", "utopique", "ascenseur", "avalanche", 
			"dangereux", "printemps", "capricorne", "scorpion", "sagittaire", "longitude", "latitude", 
			"troquet", "rugby", "football", "koala","tartiflette", "raclette", "jargon", "coccyx", 
			"podium", "bretzel", "cumulonimbus", "exigu", "zygomatique", "acrostiche", "labyrinthe",
			"thym", "ours", "fenouil", "automne", "polygone", "acrostiche", "polymorphe", "insonoriser",
			"arrosoir", "fourchette", "couteau", "tartiner", "arborescence", "curseur", 
			"historien", "pharmacie", "biscuit", "bouilloire", "grenouille", "anglicisme", "serrurerie", 
			"syllabe", "hippocampe", "wagon", "crawl", "pyjama", "solitude", "chips", "yeux", "boxe", 
			"saxe", "ahuri", "muffin", "escalier", "lyre", "thrombose", "embrayage", "algorithme"};
	
	int nbErreurs = 0;
	int idPartie = 0;
	
	public ImplPendu() throws RemoteException {
		super();
	}

	//fonction qui associe le tableau contenant l'état d'avancement du joueur, à une partie.
	
	@Override
	public void setMotCache(int id, char[] motCache) throws RemoteException {
		listePartie.get(id).setMotCache(motCache);
	}
	

	
	/* fonction a deux rôles : le premier est getMotCache(), permet donc de récupérer le tableau
	de caractère contenant les _ ou les lettres déjà trouvées, à partir de l'id de la partie. Le
	second est de renvoyer le tableau sous forme de string afin de pouvoir le placer dans le label
	prévu à cet effet dans la fiche FXML */
	
	@Override
	public String affichage(int id) {
		String motAffichage = "";
		char[] motCache = listePartie.get(id).getMotCache();
		for (int i = 0; i < motCache.length; i++) {
			motAffichage += motCache[i];
			motAffichage += " ";
		}
		return motAffichage;
	}
	
	

	/*fonction permettant de générer un mot aléatoire dans le dictionnaire*/
	
	@Override
	public void generationMotAleatoire(int id) throws RemoteException {
		String mot = "";
		Random nbAleatoire = new Random();
		mot = dictionnaire[nbAleatoire.nextInt(dictionnaire.length)];
		listePartie.get(id).setMot(mot);
	}
	
	
	
	/*fonction qui retourne le mot à trouver à partie d'un id de partie */
	
	@Override
	public String getMotAleatoire(int id) throws RemoteException {
		return listePartie.get(id).getMot();
	}
	

	
	/*fonction qui vérifie si la lettre donnée en paramètre est contenu dans le mot que l'utilisateur
	 doit trouver */
	
	@Override
	public void ecritLettres(int id, char lettre) throws RemoteException {
		boolean trouve = false;
		//mot à trouver
		String mot = listePartie.get(id).getMot();
		//tableau contenant l'avancement du joueur (soit des _ soit les lettres déjà trouvées
		char[] motCache = listePartie.get(id).getMotCache();

		for (int i = 0; i < mot.length(); i++) {
			if (mot.charAt(i) == lettre) {
				/* la lettre est contenue dans le mot à trouver, on la place donc au(x) bon(s) 
				endroit(s) dans le tableau de caractères et on change la valeur du booléen
				"trouve" à VRAI */
				motCache[i] = mot.toUpperCase().charAt(i);
				trouve = true;
			}
		}
		
		/*si la lettre n'est pas contenue dans le mot, on incrémente le compteur d'ereur lié à 
		cet idPartie*/
		if (trouve == false) {
			int nbErreurs = listePartie.get(id).getNbErreurs();
			listePartie.get(id).setNbErreurs(nbErreurs + 1);
		}
		
		listePartie.get(id).setMotCache(motCache);

	}
	

	
	/*fonction s'apparentant à getNbErreurs(), elle permet de renvoyer pour une partie donnée, le
	nombre d'erreurs que le joueur a déjà commis */
	
	@Override
	public int dessinerPendu(int id) throws RemoteException {
		return listePartie.get(id).getNbErreurs();
	}
	

	
	/*fonction qui associe un nomnbre d'erreur pour une partie donnée. On se servira de cette fonction
	pour augmenter le nombre d'erreur si le joueur se trompe */
	
	@Override
	public void setNombreErreurs(int id, int nb) throws RemoteException {
		listePartie.get(id).setNbErreurs(nb);
	}
	
	
	
	/*fonction permettant de voir si la partie est finie ou non. Pour cela on vérifie : soit si le 
	 nombre d'erreurs est supérieur ou égal à 11, soit si motCache correspond au mot qui doit être
	 trouvé. Dans ces cas, elle renvoie true, sinon false */
	
	public boolean partieTerminee(int id) throws RemoteException {
		boolean trouve = true; 
		int i=0; 
		if (listePartie.get(id).getNbErreurs()>=11)
			trouve = true;  
		else {
			while ((trouve == true) && (i<listePartie.get(id).mot.length())) {
				/*si la lettre du mot ne correspond pas à celle contenue dans le tableau c'est 
				que le joueur n'a pas encore trouvé toutes les lettres, on passe donc le booléen 
				trouve à FAUX et on sort de la boucle.*/
				if (listePartie.get(id).mot.toUpperCase().charAt(i) != listePartie.get(id).motCache[i]) 
					trouve = false; 
				i++; 
			}
		}
		return trouve; 
	}
	
	

	/*fonction permettant de créer une nouvelle partie de pendu ainsi que d'incrémenter la variable
	permettant de gèrer les id. On va ensuite les placer dans la hashmap*/
	
	@Override
	public int nouvellePartie() throws RemoteException {
		idPartie++;
		PartiePendu nouvellePartie = new PartiePendu();
		listePartie.put(idPartie, nouvellePartie);

		return idPartie;
	}

}
