package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ThemeInter extends Remote {


	public String HEADER() throws RemoteException;

	public void setHEADER(String hEADER)  throws RemoteException;
	
	public String SUBHEADER() throws RemoteException;

	public void setSUBHEADER(String sUBHEADER) throws RemoteException;

	public String LABEL() throws RemoteException;

	public void setLABEL(String lABEL) throws RemoteException;

	public String VALUE() throws RemoteException;

	public void setVALUE(String vALUE) throws RemoteException;

	public String VALUEDESCRI() throws RemoteException;

	public void setVALUEDESCRI(String vALUEDESCRI) throws RemoteException;

	public String DIVIDER() throws RemoteException;

	public void setDIVIDER(String dIVIDER) throws RemoteException;

	public String OPTION() throws RemoteException;

	public void setOPTION(String oPTION) throws RemoteException;

	public String OPTIONNUMBER() throws RemoteException;

	public void setOPTIONNUMBER(String oPTIONNUMBER) throws RemoteException;

	public String OPTIONDESCRI() throws RemoteException;

	public void setOPTIONDESCRI(String oPTIONDESCRI) throws RemoteException;

	public String UNAVAILABLE() throws RemoteException;

	public void setUNAVAILABLE(String uNAVAIL) throws RemoteException;

	public String UNAVAILREASON() throws RemoteException;

	public void setUNAVAILREASON(String uNAVAILREASON) throws RemoteException;

	public String INSTRUCTION() throws RemoteException;

	public void setINSTRUCTION(String iNSTRUCTION) throws RemoteException;

	public String ERROR() throws RemoteException;

	public void setERROR(String eRROR) throws RemoteException;

	public String ALERT() throws RemoteException;

	public void setALERT(String aLERT) throws RemoteException;

	public String UNIT() throws RemoteException;

	public void setUNIT(String uNIT) throws RemoteException;

	public String ENDINSTRUCT() throws RemoteException;

	public void setENDINSTRUCT(String eNDINSTRUCT) throws RemoteException;

	public String ENEMY() throws RemoteException;

	public void setENEMY(String eNEMY) throws RemoteException;

	public String NEUTRAL() throws RemoteException;

	public void setNEUTRAL(String nEUTRAL) throws RemoteException;

	public String ALLY() throws RemoteException;

	public void setALLY(String aLLY) throws RemoteException;
}
