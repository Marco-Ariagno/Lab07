package it.polito.tdp.poweroutages.model;

import java.time.Duration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {

	private PowerOutageDAO podao;
	private int oreInMinuti;
	private int anni;
	private int bestNumero;
	private List<PowerOutage> bestSoluzione;
	private List<PowerOutage> tutti;

	public Model() {
		podao = new PowerOutageDAO();
		oreInMinuti = 0;
		anni = 0;
		bestNumero = 0;
		bestSoluzione = null;
		tutti = null;
	}
	

	public int getBestNumero() {
		return bestNumero;
	}



	public void setBestNumero(int bestNumero) {
		this.bestNumero = bestNumero;
	}



	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<PowerOutage> calcolaSottoinsiemi(int oreMax, int anni, Nerc nerc) {
		this.oreInMinuti = oreMax * 60;
		this.anni = anni;
		tutti = new ArrayList<>(podao.getPowerOutagesByNerc(nerc));
		
		List<PowerOutage> parziale = new ArrayList<>();
		
		
		cerca(parziale, 0);
		
		return bestSoluzione;
	}

	private void cerca(List<PowerOutage> parziale, int livello) {
	
		if(calcolaMinuti(parziale)>oreInMinuti)
			return;
		if(controllaDate(parziale)==false)
			return;
		
		if(calcolaCoinvolte(parziale)>bestNumero || bestSoluzione==null) {
			bestNumero=calcolaCoinvolte(parziale);
			bestSoluzione=new ArrayList<>(parziale);
		}
		
		if(livello==tutti.size())
			return;
		
		
		parziale.add(tutti.get(livello));
		cerca(parziale,livello+1);
		parziale.remove(tutti.get(livello));
		
		cerca(parziale,livello+1);
	}

	public int calcolaMinuti(List<PowerOutage> parziale) {
		if(parziale.size()==0) {
			return 0;
		}
		int ore = 0;
		for (PowerOutage p : parziale) {
			ore += (int) Duration.between(p.getDateEventBegan(), p.getDateEventFinished()).toMinutes();
		}
		return ore;
	}

	public int calcolaCoinvolte(List<PowerOutage> parziale) {
		int tot = 0;
		for (PowerOutage p : parziale) {
			tot += p.getCustomersAffected();
		}
		return tot;
	}

	public boolean controllaDate(List<PowerOutage> parziale) {
		if(parziale.size()==0) {
			return true;
		}
		LocalDateTime lmax = LocalDateTime.MAX;
		LocalDateTime lmin = LocalDateTime.MIN;
		for (PowerOutage p : parziale) {
			if (p.getDateEventBegan().isAfter(lmin)) {
				lmin = p.getDateEventBegan();
			}
			if (p.getDateEventFinished().isBefore(lmax)) {
				lmax = p.getDateEventFinished();
			}
		}
		if (Duration.between(lmin, lmax).toDays() < (365 * 4 + 1)) {
			return true;
		}
		return false;
	}

	

	public int getAnni() {
		return anni;
	}

}
