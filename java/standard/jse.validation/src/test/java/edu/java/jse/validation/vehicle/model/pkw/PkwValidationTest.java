package edu.java.jse.validation.vehicle.model.pkw;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.ArrayUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.java.jse.validation.vehicle.model.common.type.ConstraintViolationTemplate;
import edu.java.jse.validation.vehicle.model.parts.reifen.Reifen;
import edu.java.jse.validation.vehicle.model.pkw.validation.annotation.group.AllGroups;
import edu.java.jse.validation.vehicle.model.pkw.validation.annotation.group.PartsValidationGroup;

public class PkwValidationTest {

	// ... constants

	private final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

	// ... test methods

	@Before
	public void setUp() {

		Locale.setDefault(Locale.ENGLISH);
	}

	@Test
	public void test_Failure_Groups_All() {

		// ... prepare test data
		final Pkw pkw = createPkw_FullyCompleted();

		pkw.setModellName(null);
		abmontireEinenReifenUndMacheDieErstePlatt(pkw);

		// ... check preconditions

		// ... call operation under test
		final Set<ConstraintViolation<Pkw>> actualConstraintViolations = VALIDATOR.validate(pkw, AllGroups.class);

		// ... check postconditions
		Assert.assertTrue(actualConstraintViolations.size() > 0);

		final ConstraintViolationTemplate[] expectedTemplates = new ConstraintViolationTemplate[] {

				// ... class-level constraints
				new ConstraintViolationTemplate(Pkw.class, "",
						"{edu.java.jse.validation.vehicle.model.pkw.IstPkwFertig.message}"),

				// ... field-level constraints
				new ConstraintViolationTemplate(Pkw.class, "modellName",
						"{javax.validation.constraints.NotNull.message}"),
				new ConstraintViolationTemplate(Pkw.class, "reifen", "{javax.validation.constraints.Size.message}"),

				// ... property-level constraints
				new ConstraintViolationTemplate(Pkw.class, "fertig",
						"{javax.validation.constraints.AssertTrue.message}"),

				// ... cascaded custom field-level constraints
				new ConstraintViolationTemplate(Pkw.class, "reifen[0].pressureInAtm",
						"{edu.java.jse.validation.vehicle.model.parts.reifen.IstReifenAufgepumpt.message}"),

		};

		assert_ConstraintViolationsSet_Contains_Only(actualConstraintViolations, expectedTemplates);
	}

	@Test
	public void test_Failure_Groups_Default() {

		// ... prepare test data
		final Pkw pkw = createPkw_FullyCompleted();

		pkw.setModellName(null);
		abmontireEinenReifenUndMacheDieErstePlatt(pkw);

		// ... check preconditions

		// ... call operation under test
		final Set<ConstraintViolation<Pkw>> actualConstraintViolations = VALIDATOR.validate(pkw);

		// ... check postconditions
		Assert.assertTrue(actualConstraintViolations.size() > 0);

		final ConstraintViolationTemplate[] expectedTemplates = new ConstraintViolationTemplate[] {

				// ... class-level constraints
				new ConstraintViolationTemplate(Pkw.class, "",
						"{edu.java.jse.validation.vehicle.model.pkw.IstPkwFertig.message}"),

				// ... field-level constraints
				new ConstraintViolationTemplate(Pkw.class, "modellName",
						"{javax.validation.constraints.NotNull.message}"),
				new ConstraintViolationTemplate(Pkw.class, "reifen", "{javax.validation.constraints.Size.message}"),

				// ... property-level constraints
				new ConstraintViolationTemplate(Pkw.class, "fertig",
						"{javax.validation.constraints.AssertTrue.message}"),

		};

		assert_ConstraintViolationsSet_Contains_Only(actualConstraintViolations, expectedTemplates);
	}

	@Test
	public void test_Failure_Groups_PartsValidationGroup() {

		// ... prepare test data
		final Pkw pkw = createPkw_FullyCompleted();

		pkw.setModellName(null);
		abmontireEinenReifenUndMacheDieErstePlatt(pkw);

		// ... check preconditions

		// ... call operation under test
		final Set<ConstraintViolation<Pkw>> actualConstraintViolations = VALIDATOR.validate(pkw,
				PartsValidationGroup.class);

		// ... check postconditions
		Assert.assertTrue(actualConstraintViolations.size() > 0);

		final ConstraintViolationTemplate[] expectedTemplates = new ConstraintViolationTemplate[] {

		// ... cascaded custom field-level constraints
		new ConstraintViolationTemplate(Pkw.class, "reifen[0].pressureInAtm",
				"{edu.java.jse.validation.vehicle.model.parts.reifen.IstReifenAufgepumpt.message}"),

		};

		assert_ConstraintViolationsSet_Contains_Only(actualConstraintViolations, expectedTemplates);
	}

	private void abmontireEinenReifenUndMacheDieErstePlatt(final Pkw pkw) {

		final Reifen[] reifen = pkw.getReifen();

		final Reifen[] modifiedReifenSet = ArrayUtils.subarray(reifen, 0, reifen.length - 1);
		modifiedReifenSet[0].setPressureInAtm(0);

		pkw.setReifen(modifiedReifenSet);
	}

	@Test
	public void test_Succeeded_Groups_All() {

		// ... prepare test data
		final Pkw pkw = createPkw_FullyCompleted();

		// ... check preconditions

		// ... call operation under test
		final Set<ConstraintViolation<Pkw>> exs = VALIDATOR.validate(pkw, AllGroups.class);

		// ... check postconditions
		Assert.assertEquals(0, exs.size());
	}

	// ... test data generation methods

	private Pkw createPkw_FullyCompleted() {

		final Pkw pkw = new Pkw();

		pkw.setModellName("Some model ID");

		final int reifenCount = 4;
		final double pressureInAtm = 2.2d;
		final Reifen[] reifen = createReifen(reifenCount, pressureInAtm);
		pkw.setReifen(reifen);

		return pkw;
	}

	private Reifen[] createReifen(final int reifenCount, final double pressureInAtm) {

		final Reifen[] reifen = new Reifen[reifenCount];

		for (int i = 0; i < reifenCount; i++) {

			reifen[i] = new Reifen();
			reifen[i].setPressureInAtm(pressureInAtm);
		}

		return reifen;
	}

	// ... helper methods

	private void assert_ConstraintViolationsSet_Contains_Only(
			final Set<ConstraintViolation<Pkw>> actualConstraintViolations,
			final ConstraintViolationTemplate[] expectedTemplates) {

		final String errorMessage = "Die Anzahl der aktuellen ConstraintViolation's stimmt mit der erwarteten nicht überein.";
		Assert.assertEquals(errorMessage, expectedTemplates.length, actualConstraintViolations.size());

		assert_ConstraintViolationsSet_Contains(actualConstraintViolations, expectedTemplates);
	}

	private void assert_ConstraintViolationsSet_Contains(final Set<ConstraintViolation<Pkw>> constraintViolations,
			final ConstraintViolationTemplate[] expectedTemplates) {

		final List<ConstraintViolationTemplate> actualTemplates = convert_To_ConstraintViolationTemplates(constraintViolations);

		MatcherAssert.assertThat(actualTemplates, Matchers.hasItems(expectedTemplates));
	}

	private List<ConstraintViolationTemplate> convert_To_ConstraintViolationTemplates(
			final Set<ConstraintViolation<Pkw>> constraintViolations) {

		final List<ConstraintViolationTemplate> result = new ArrayList<ConstraintViolationTemplate>();

		for (ConstraintViolation<Pkw> constraintViolation : constraintViolations) {

			final ConstraintViolationTemplate tempate = new ConstraintViolationTemplate(constraintViolation);
			result.add(tempate);
		}

		return result;
	}

}
