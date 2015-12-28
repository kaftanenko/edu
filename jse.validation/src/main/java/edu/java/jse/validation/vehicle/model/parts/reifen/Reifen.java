package edu.java.jse.validation.vehicle.model.parts.reifen;

import edu.java.jse.validation.vehicle.model.parts.reifen.validation.annotation.IstReifenAufgepumpt;

public class Reifen {

	// ... properties

	@IstReifenAufgepumpt
	private double pressureInAtm;

	// ... getter/setter methods

	public double getPressureInAtm() {

		return pressureInAtm;
	}

	public void setPressureInAtm(double pressureInAtm) {

		this.pressureInAtm = pressureInAtm;
	}

}
