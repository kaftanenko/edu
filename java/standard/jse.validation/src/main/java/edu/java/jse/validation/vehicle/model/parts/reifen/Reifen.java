package edu.java.jse.validation.vehicle.model.parts.reifen;

import edu.java.jse.validation.vehicle.model.parts.reifen.validation.annotation.IstReifenAufgepumpt;
import edu.java.jse.validation.vehicle.model.pkw.validation.annotation.group.PartsValidationGroup;

public class Reifen {

	// ... properties

	@IstReifenAufgepumpt(groups = PartsValidationGroup.class)
	private double pressureInAtm;

	// ... getter/setter methods

	public double getPressureInAtm() {

		return pressureInAtm;
	}

	public void setPressureInAtm(double pressureInAtm) {

		this.pressureInAtm = pressureInAtm;
	}

}
