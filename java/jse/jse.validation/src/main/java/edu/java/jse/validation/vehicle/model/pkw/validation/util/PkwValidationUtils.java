package edu.java.jse.validation.vehicle.model.pkw.validation.util;

import edu.java.jse.validation.vehicle.model.parts.reifen.Reifen;
import edu.java.jse.validation.vehicle.model.pkw.Pkw;

public class PkwValidationUtils {

	// ... business methods

	public static boolean isCompleted(final Pkw pkw) {

		if (pkw.getModellName() == null) {

			return false;
		}

		final Reifen[] pkwReifen = pkw.getReifen();
		if (pkwReifen == null || pkwReifen.length != Pkw.STANDARD_REIFEN_COUNT__4) {

			return false;
		}

		return true;
	}

}
