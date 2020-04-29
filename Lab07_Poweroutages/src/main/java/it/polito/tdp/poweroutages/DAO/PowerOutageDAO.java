package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {

	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<PowerOutage> getPowerOutagesByNerc(Nerc nerc) {

		String sql = "SELECT * FROM poweroutages AS p, nerc AS n WHERE p.nerc_id=n.id AND n.value=?";
		List<PowerOutage> pos=new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, nerc.getValue());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				PowerOutage p=new PowerOutage(res.getInt("id"), res.getInt("event_type_id"), res.getInt("tag_id"), res.getInt("area_id"), res.getInt("responsible_id"), res.getInt("customers_affected"),
						res.getTimestamp("date_event_began").toLocalDateTime(),res.getTimestamp("date_event_finished").toLocalDateTime());
				pos.add(p);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return pos;
	
	}

	public PowerOutage getPowerOutage(int id) {
		String sql = "SELECT * FROM poweroutages WHERE id=?";
		PowerOutage p=null;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				p=new PowerOutage(res.getInt("id"), res.getInt("event_type_id"), res.getInt("tag_id"), res.getInt("area_id"), res.getInt("responsible_id"), res.getInt("customers_affected"),
						res.getTimestamp("date_event_began").toLocalDateTime(),res.getTimestamp("date_event_finished").toLocalDateTime());
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return p;
	}

}
