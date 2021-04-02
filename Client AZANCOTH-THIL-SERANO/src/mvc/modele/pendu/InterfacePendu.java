package mvc.modele.pendu;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfacePendu extends Remote {

	public String affichage(int id) throws RemoteException;

	public void setNombreErreurs(int id, int nb) throws RemoteException;

	public int dessinerPendu(int id) throws RemoteException;

	public boolean partieTerminee(int id) throws RemoteException;
	
	public int nouvellePartie() throws RemoteException;

	public void generationMotAleatoire(int id) throws RemoteException;

	public String getMotAleatoire(int id) throws RemoteException;

	public void setMotCache(int id, char[] mot) throws RemoteException;

	public void ecritLettres(int id, char lettre) throws RemoteException;

}
