package edu.java.jse.validation.vehicle.model.pkw;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.java.jse.validation.vehicle.model.common.Vehicle;
import edu.java.jse.validation.vehicle.model.parts.reifen.Reifen;
import edu.java.jse.validation.vehicle.model.pkw.validation.annotation.IstPkwFertig;
import edu.java.jse.validation.vehicle.model.pkw.validation.util.PkwValidationUtils;

@IstPkwFertig
public class Pkw extends Vehicle {

	// ... constants

	public static final int STANDARD_REIFEN_COUNT__4 = 4;

	// ... properties

	@NotNull
	private String modellName;

	@Size(min = STANDARD_REIFEN_COUNT__4, max = STANDARD_REIFEN_COUNT__4)
	@Valid
	private Reifen[] reifen;

	// ... getter/setter methods

	public String getModellName() {

		return modellName;
	}

	public void setModellName(String model) {

		this.modellName = model;
	}

	public Reifen[] getReifen() {

		return reifen;
	}

	public void setReifen(Reifen[] reifen) {

		this.reifen = reifen;
	}

	// ... domain specific constraint methods

	@AssertTrue
	public boolean isFertig() {

		return PkwValidationUtils.isCompleted(this);
	}

}
