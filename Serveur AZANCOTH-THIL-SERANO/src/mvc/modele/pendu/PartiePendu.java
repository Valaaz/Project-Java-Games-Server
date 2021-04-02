package mvc.modele.pendu;

public class PartiePendu {

	/*------------------------------*/
	/* initialisation des variables */
	/*------------------------------*/
	
	String mot;               // mot qu'on doit deviner
	char[] motCache;          // tableau contenant les _ ou les lettres déjà trouvés
	int nbErreurs = 0;
	int idPartie = 1;

	
	/*---------------------------------------*/
	/* Getters et Setters de chaque variable */
	/*---------------------------------------*/
	
	public String getMot() {
		return mot;
	}

	public void setMot(String mot) {
		this.mot = mot;
	}

	public char[] getMotCache() {
		return motCache;
	}

	public void setMotCache(char[] motCache) {
		this.motCache = motCache;
	}

	public int getNbErreurs() {
		return nbErreurs;
	}

	public void setNbErreurs(int nbErreurs) {
		this.nbErreurs = nbErreurs;
	}

	public int getIdPartie() {
		return idPartie;
	}

	public void setIdPartie(int idPartie) {
		this.idPartie = idPartie;
	}

}
