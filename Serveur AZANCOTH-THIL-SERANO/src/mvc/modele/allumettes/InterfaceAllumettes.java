package mvc.modele.allumettes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceAllumettes extends Remote {

	public int nouvellePartie() throws RemoteException;

	void soustraireAllumettes(int id, int nbRetirer) throws RemoteException;

	int getNombreAllumettesJoueur(int id) throws RemoteException;

	int getNbAllumettePartie(int id) throws RemoteException;

	int generationAleatoireAllumettes(int id) throws RemoteException;

	void setNbAllumettePartie(int id, int nbAllumettes) throws RemoteException;

	void setNombreAllumettesJoueur(int id, int nbAllumettesJoueur) throws RemoteException;

	int getTour(int id) throws RemoteException;

	void setTour(int id, int tour) throws RemoteException;

	void coupIA(int id) throws RemoteException;

}
