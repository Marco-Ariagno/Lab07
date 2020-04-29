/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */
package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ComboBox<Nerc> comboBoxNerc;

	@FXML
	private TextField txtAnni;

	@FXML
	private TextField txtOre;

	@FXML
	private Button btnAnalysis;

	@FXML
	private TextArea txtResult;

	@FXML
	void risolviCasoPeggiore(ActionEvent event) {
		txtResult.clear();
		int anni;
		int ore;
		try {
			anni = Integer.parseInt(txtAnni.getText());
			ore = Integer.parseInt(txtOre.getText());
		} catch (NumberFormatException nfe) {
			txtResult.appendText("Devi inserire un numero!!\n");
			return;
		}

		Nerc n = comboBoxNerc.getValue();
		List<PowerOutage> soluz = new ArrayList<>(model.calcolaSottoinsiemi(ore, anni, n));
		txtResult.appendText("Tot people affected: " + model.getBestNumero() + "\n");
		for (PowerOutage p : soluz) {
			txtResult.appendText(p + "\n");
		}
	}

	@FXML
	void initialize() {
		assert comboBoxNerc != null : "fx:id=\"comboBoxNerc\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtAnni != null : "fx:id=\"txtAnni\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtOre != null : "fx:id=\"txtOre\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnAnalysis != null : "fx:id=\"btnAnalysis\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model m) {
		this.model = m;
		List<Nerc> tuttiNerc = new ArrayList<Nerc>(model.getNercList());
		comboBoxNerc.getItems().addAll(tuttiNerc);
	}
}
