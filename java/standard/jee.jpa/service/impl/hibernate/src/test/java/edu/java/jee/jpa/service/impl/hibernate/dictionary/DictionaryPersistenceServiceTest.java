package edu.java.jee.jpa.service.impl.hibernate.dictionary;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import edu.java.jee.jpa.model.dictionary.DictionaryEntryEntity;
import edu.java.jee.jpa.service.api.dictionary.IDictionaryPersistenceService;
import edu.java.jee.jpa.service.impl.hibernate.common.AbstractPersistenceServiceTest;

public class DictionaryPersistenceServiceTest extends AbstractPersistenceServiceTest {

	// ... constants

	private static final String TEST_DATA__DICTIONARY__SOME_USE_CASE_VALUE__FOR_0_HITS__BY__NOT_EXISTING_USE_CASE = "not-existing-use-case";
	private static final String[] TEST_DATA__DICTIONARY__SOME_KEY__VALUES_SET__FOR_0_HIT__BY__NOT_EXISTING_USE_CASE = new String[] {};

	private static final String TEST_DATA__DICTIONARY__SOME_USE_CASE_VALUE__FOR_1_HIT__BY__USE_CASE_1 = "use-case-1";
	private static final String[] TEST_DATA__DICTIONARY__SOME_KEY__VALUES_SET__FOR_1_HIT__BY__USE_CASE_1 = new String[] {
			"key_1" };

	private static final String TEST_DATA__DICTIONARY__SOME_USE_CASE_VALUE__FOR_3_HITS__BY__USE_CASE_2 = "use-case-2";
	private static final String[] TEST_DATA__DICTIONARY__SOME_KEY__VALUES_SET__FOR_3_HITS__BY__USE_CASE_2 = new String[] {
			"key_1", "key_2", "key_3" };

	// ... properties

	private final IDictionaryPersistenceService serviceUnderTest = new DictionaryPersistenceService(
			createEntityManagerFactory());

	// ... test methods

	@Test
	public void test_FindDictionaryEntriesBySomeUseCase_Success_With_No_Hits() {

		// ... prepare test data
		final String useCaseToFindBy = TEST_DATA__DICTIONARY__SOME_USE_CASE_VALUE__FOR_0_HITS__BY__NOT_EXISTING_USE_CASE;
		final String[] expectedSomeKeyValues = TEST_DATA__DICTIONARY__SOME_KEY__VALUES_SET__FOR_0_HIT__BY__NOT_EXISTING_USE_CASE;

		// ... call generic test
		test_FindDictionaryEntriesBySomeUseCase_Success(useCaseToFindBy, expectedSomeKeyValues);
	}

	@Test
	public void test_FindDictionaryEntriesBySomeUseCase_Success_With_Single_Hit() {

		// ... prepare test data
		final String useCaseToFindBy = TEST_DATA__DICTIONARY__SOME_USE_CASE_VALUE__FOR_1_HIT__BY__USE_CASE_1;
		final String[] expectedSomeKeyValues = TEST_DATA__DICTIONARY__SOME_KEY__VALUES_SET__FOR_1_HIT__BY__USE_CASE_1;

		// ... call generic test
		test_FindDictionaryEntriesBySomeUseCase_Success(useCaseToFindBy, expectedSomeKeyValues);
	}

	@Test
	public void test_FindDictionaryEntriesBySomeUseCase_Success_With_Multiple_Hits() {

		// ... prepare test data
		final String useCaseToFindBy = TEST_DATA__DICTIONARY__SOME_USE_CASE_VALUE__FOR_3_HITS__BY__USE_CASE_2;
		final String[] expectedSomeKeyValues = TEST_DATA__DICTIONARY__SOME_KEY__VALUES_SET__FOR_3_HITS__BY__USE_CASE_2;

		// ... call generic test
		test_FindDictionaryEntriesBySomeUseCase_Success(useCaseToFindBy, expectedSomeKeyValues);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void test_FindDictionaryEntriesBySomeUseCase_Failure() {

		// ... prepare test data
		final String useCaseToFindBy = TEST_DATA__DICTIONARY__SOME_USE_CASE_VALUE__FOR_1_HIT__BY__USE_CASE_1;
		final String[] expectedSomeKeyValues = TEST_DATA__DICTIONARY__SOME_KEY__VALUES_SET__FOR_3_HITS__BY__USE_CASE_2;

		// ... call generic test
		test_FindDictionaryEntriesBySomeUseCase_Success(useCaseToFindBy, expectedSomeKeyValues);
	}

	private void test_FindDictionaryEntriesBySomeUseCase_Success(final String useCaseToFindBy,
			final String[] expectedSomeKeyValues) {

		// ... call operation(s) under test
		final DictionaryEntryEntity[] dictionaryEntries = serviceUnderTest
				.findDictionaryEntryBySomeUseCase(useCaseToFindBy);

		// ... verify post-conditions
		final int expectedDictionaryEntriesCount = expectedSomeKeyValues.length;
		assertThat(expectedDictionaryEntriesCount).isEqualTo(dictionaryEntries.length);

		final String[] dictionarySomeKeyValues = retrieveSomeKeyValues(dictionaryEntries);
		assertThat(expectedSomeKeyValues).containsExactlyInAnyOrder(dictionarySomeKeyValues);
	}

	// ... helper methods

	private String[] retrieveSomeKeyValues(final DictionaryEntryEntity[] dictionaryEntries) {

		final List<String> someKeys = new ArrayList<String>();

		for (final DictionaryEntryEntity dictionaryEntry : dictionaryEntries) {

			final String keyValue = dictionaryEntry.getSomeKey();
			someKeys.add(keyValue);
		}

		return someKeys.toArray(new String[0]);
	}

}
