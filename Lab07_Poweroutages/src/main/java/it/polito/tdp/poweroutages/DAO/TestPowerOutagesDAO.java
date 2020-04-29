package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.time.Duration;
import java.time.Period;

import it.polito.tdp.poweroutages.model.PowerOutage;

public class TestPowerOutagesDAO {

	public static void main(String[] args) {
		
		PowerOutageDAO pdao = new PowerOutageDAO() ;
		
		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");
			
			PowerOutageDAO dao = new PowerOutageDAO() ;
			
			System.out.println(dao.getNercList()) ;

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}
		PowerOutage po=pdao.getPowerOutage(1);
		System.out.println(po);
		Duration d= Duration.between(po.getDateEventBegan(),po.getDateEventFinished());
		Long ore=d.toHours();
		Long minuti=d.toMinutes();
		System.out.println("ore: "+ore+" minuti: "+minuti);

	}

}
