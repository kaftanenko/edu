package edu.java.jee.jpa.service.impl.hibernate.masterchild;

import static edu.java.jee.jpa.service.impl.hibernate.masterchild.MasterPersistenceServiceTestHelper.get_New_Test_Master_With_Maximal_DataAmount_And_Dependencies;
import static edu.java.jee.jpa.service.impl.hibernate.masterchild.MasterPersistenceServiceTestHelper.get_New_Test_Master_With_Maximal_DataAmount_Without_Dependencies;
import static edu.java.jee.jpa.service.impl.hibernate.masterchild.MasterPersistenceServiceTestHelper.get_New_Test_Master_With_Minimal_DataAmount;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import edu.java.jee.jpa.model.masterchild.ChildEntity;
import edu.java.jee.jpa.model.masterchild.MasterEntity;
import edu.java.jee.jpa.service.api.masterchild.IMasterPersistenceService;
import edu.java.jee.jpa.service.impl.hibernate.common.AbstractPersistenceServiceTest;
import edu.java.jee.jpa.service.impl.hibernate.common.error.DataNotFoundRuntimeException;

public class MasterPersistenceServiceTest extends AbstractPersistenceServiceTest {

	// ... constants

	private static final long TEST_DATA__NON_EXISTING__ENTITY__ID_0 = 0L;

	private static final long TEST_DATA__EXISTING__MASTER__WITH__MIN_DATA_AMOUNT__ID_1 = 1L;

	private static final long TEST_DATA__EXISTING__MASTER__WITH__MAX_DATA_AMOUNT__WITHOUT__DEPENDENCIES__ID_2 = 2L;

	private static final long TEST_DATA__EXISTING__MASTER__WITH__MAX_DATA_AMOUNT__AND__DEPENDENCIES__ID_3 = 3L;

	private static final int TEST_DATA__SOME_DATA_FIELD__INVALID_VALUE__777 = 777;

	private static final int TEST_DATA__MASTER__SOME_DATA_FIELD_VALUE__FOR_0_HITS__7 = 7;

	private static final int TEST_DATA__MASTER__SOME_DATA_FIELD_VALUE__FOR_2_HITS__2 = 2;

	private static final int TEST_DATA__CHILD__SOME_DATA_FIELD_VALUE__FOR_0_HITS__7 = 7;

	private static final int TEST_DATA__CHILD__SOME_DATA_FIELD_VALUE__FOR_1_HIT__5 = 5;

	private static final List<Integer> TEST_DATA__MASTER__SOME_DATA_FIELD_VALUES__RELEVANT_FOR_TEST_CASES = Arrays
			.asList(new Integer[] { TEST_DATA__MASTER__SOME_DATA_FIELD_VALUE__FOR_0_HITS__7,
					TEST_DATA__MASTER__SOME_DATA_FIELD_VALUE__FOR_2_HITS__2 });

	// ... properties

	private final IMasterPersistenceService serviceUnderTest = new MasterPersistenceService(
			createEntityManagerFactory());

	// ... test methods

	@Test(expectedExceptions = DataNotFoundRuntimeException.class)
	public void test_GetById_Failure_For_Not_Existing_Master() {

		// ... prepare test data
		final long id = TEST_DATA__NON_EXISTING__ENTITY__ID_0;

		// ... call operation under test
		serviceUnderTest.getById(id);
	}

	@Test
	public void test_GetById_Success_For_Existing_Master_With_Minimal_DataAmount() {

		// ... prepare test data
		final long id = TEST_DATA__EXISTING__MASTER__WITH__MIN_DATA_AMOUNT__ID_1;

		// ... call operation under test
		final MasterEntity persistentMaster = serviceUnderTest.getById(id);

		// ... verify postconditions
		assertThat(persistentMaster.getSomeDataField()).isNull();

		verify_Master_Has_No_Childs(persistentMaster);
	}

	@Test
	public void test_GetById_Success_For_Existing_Master_With_Maximal_DataAmount_Without_Dependencies() {

		// ... prepare test data
		final long id = TEST_DATA__EXISTING__MASTER__WITH__MAX_DATA_AMOUNT__WITHOUT__DEPENDENCIES__ID_2;

		// ... call operation under test
		final MasterEntity persistentMaster = serviceUnderTest.getById(id);

		// ... verify postconditions
		assertThat(persistentMaster.getSomeDataField()).isNotNull();

		verify_Master_Has_No_Childs(persistentMaster);
	}

	@Test
	public void test_GetById_Success_For_Existing_Master_With_Maximal_DataAmount_And_Dependencies() {

		// ... prepare test data
		final long id = TEST_DATA__EXISTING__MASTER__WITH__MAX_DATA_AMOUNT__AND__DEPENDENCIES__ID_3;

		// ... call operation under test
		final MasterEntity persistentMaster = serviceUnderTest.getById(id);

		// ... verify postconditions
		final int expectedChildsCount = 3;
		verify_Master_Has_Childs(persistentMaster, expectedChildsCount);
	}

	// ...

	@Test(expectedExceptions = IllegalStateException.class)
	public void test_SaveOrUpdate_Failure_On_Invalid_Master_Data() throws Exception {

		// ... prepare test data
		final MasterEntity entity = get_New_Test_Master_With_Minimal_DataAmount();
		entity.setSomeDataField(TEST_DATA__SOME_DATA_FIELD__INVALID_VALUE__777);

		// ... call operation under test
		serviceUnderTest.saveOrUpdate(entity);
	}

	@Test
	public void test_SaveOrUpdate_Success_For_New_Master_With_Minimal_DataAmount() throws Exception {

		// ... prepare test data
		final MasterEntity master = get_New_Test_Master_With_Minimal_DataAmount();

		// ... call operation under test
		serviceUnderTest.saveOrUpdate(master);

		// ... verify postconditions
		final long masterId = master.getId();
		final MasterEntity persistentMaster = serviceUnderTest.getById(masterId);

		assertThat(persistentMaster.getSomeDataField()).isNull();

		verify_Master_Has_No_Childs(persistentMaster);
	}

	@Test
	public void test_SaveOrUpdate_Success_For_New_Master_With_Maximal_DataAmount_Without_Dependencies()
			throws Exception {

		// ... prepare test data
		final MasterEntity master = get_New_Test_Master_With_Maximal_DataAmount_Without_Dependencies();

		// ... call operation under test
		serviceUnderTest.saveOrUpdate(master);

		// ... verify postconditions
		final long masterId = master.getId();
		final MasterEntity persistentMaster = serviceUnderTest.getById(masterId);

		assertThat(persistentMaster.getSomeDataField()).isNotNull();

		verify_Master_Has_No_Childs(persistentMaster);
	}

	@Test
	public void test_SaveOrUpdate_Success_For_New_Master_With_Maximal_DataAmount_And_Dependencies() throws Exception {

		// ... prepare test data
		final MasterEntity master = get_New_Test_Master_With_Maximal_DataAmount_And_Dependencies();
		final int expectedChildsCount = 3;

		// ... call operation under test
		serviceUnderTest.saveOrUpdate(master);

		// ... verify postconditions
		final long masterId = master.getId();
		final MasterEntity persistentMaster = serviceUnderTest.getById(masterId);

		assertThat(persistentMaster.getSomeDataField()).isNotNull();

		verify_Master_Has_Childs(persistentMaster, expectedChildsCount);
	}

	@Test
	public void test_SaveOrUpdate_Success_For_New_Master_With_Maximal_DataAmount_And_Dependencies_By_Partially_Removing_Of_Some_Optional_Dependencies()
			throws Exception {

		// ... prepare test data
		final MasterEntity master = create_New_Test_Master_With_Maximal_DataAmount_And_Dependencies();
		final int expectedChildsCount = 2;

		final ChildEntity child = master.getChilds()[1];
		master.removeChild(child);

		final ChildEntity[] childs = master.getChilds();
		assertThat(expectedChildsCount).isEqualTo(childs.length);

		final int someDataField = master.getSomeDataField();
		int newSomeDataFieldValue = someDataField;
		do {
			newSomeDataFieldValue++;
			newSomeDataFieldValue %= 10;
		} while (TEST_DATA__MASTER__SOME_DATA_FIELD_VALUES__RELEVANT_FOR_TEST_CASES.contains(newSomeDataFieldValue));
		master.setSomeDataField(newSomeDataFieldValue);

		// ... call operation under test
		serviceUnderTest.saveOrUpdate(master);

		// ... verify postconditions
		final long masterId = master.getId();
		final MasterEntity persistentMaster = serviceUnderTest.getById(masterId);

		// TODO ...
	}

	// ...

	@Test
	public void test_FindMastersBySomeDataField_Success_With_No_Hits() {

		// ... prepare test data
		final int masterSomeDataFieldValue = TEST_DATA__MASTER__SOME_DATA_FIELD_VALUE__FOR_0_HITS__7;
		final int expectedMastersCount = 0;

		// ... call operation(s) under test
		final MasterEntity[] masters = serviceUnderTest.findMasterEntityBySomeDataField(masterSomeDataFieldValue);

		// ... verify post-conditions
		assertThat(expectedMastersCount).isEqualTo(masters.length);
	}

	@Test
	public void test_FindMastersBySomeDataField_Success_With_Some_Hits() {

		// ... prepare test data
		final int masterSomeDataFieldValue = TEST_DATA__MASTER__SOME_DATA_FIELD_VALUE__FOR_2_HITS__2;
		final int expectedMastersCount = 2;

		// ... call operation(s) under test
		final MasterEntity[] masters = serviceUnderTest.findMasterEntityBySomeDataField(masterSomeDataFieldValue);

		// ... verify post-conditions
		assertThat(expectedMastersCount).isEqualTo(masters.length);
	}

	@Test
	public void test_Find_Masters_Having_Child_With_SomeDataField_Equals_To_Success_For_No_Hits() {

		// ... prepare test data
		final int childSomeDataFieldValue = TEST_DATA__CHILD__SOME_DATA_FIELD_VALUE__FOR_1_HIT__5;
		final int expectedMastersCount = 1;

		// ... call operation(s) under test
		final MasterEntity[] masters = serviceUnderTest
				.findMasterEntityHavingChildsWithSomeDataFieldEqualsTo(childSomeDataFieldValue);

		// ... verify post-conditions
		assertThat(expectedMastersCount).isEqualTo(masters.length);
	}

	@Test
	public void test_Find_Masters_Having_Child_With_SomeDataField_Equals_To_Success_For_Some_Hits() {

		// ... prepare test data
		final int childSomeDataFieldValue = TEST_DATA__CHILD__SOME_DATA_FIELD_VALUE__FOR_0_HITS__7;
		final int expectedMastersCount = 0;

		// ... call operation(s) under test
		final MasterEntity[] masters = serviceUnderTest
				.findMasterEntityHavingChildsWithSomeDataFieldEqualsTo(childSomeDataFieldValue);

		// ... verify post-conditions
		assertThat(expectedMastersCount).isEqualTo(masters.length);
	}

	// ...

	private MasterEntity create_Test_Master_With_Minimal_DataAmount() {

		final MasterEntity master = get_New_Test_Master_With_Minimal_DataAmount();
		final int expectedChildsCount = 0;

		serviceUnderTest.saveOrUpdate(master);

		final Long masterId = master.getId();
		assertThat(master.getId()).isNotNull();

		verify_Master_Exists(masterId);

		final MasterEntity persistentMaster = serviceUnderTest.getById(masterId);

		verify_Master_Has_Childs(persistentMaster, expectedChildsCount);

		return persistentMaster;
	}

	private MasterEntity create_New_Test_Master_With_Maximal_DataAmount_And_Dependencies() {

		final MasterEntity master = get_New_Test_Master_With_Maximal_DataAmount_And_Dependencies();
		final int expectedChildsCount = 3;

		serviceUnderTest.saveOrUpdate(master);

		final Long masterId = master.getId();
		assertThat(master.getId()).isNotNull();

		verify_Master_Exists(masterId);

		final MasterEntity persistentMaster = serviceUnderTest.getById(masterId);

		verify_Master_Has_Childs(persistentMaster, expectedChildsCount);

		return persistentMaster;
	}

	// ... helper methods

	private void verify_Master_Exists(final Long masterId) {

		assertThat(serviceUnderTest.getById(masterId)).isNotNull();
	}

	private void verify_Master_Has_No_Childs(final MasterEntity master) {

		verify_Master_Has_Childs(master, 0);
	}

	private void verify_Master_Has_Childs(final MasterEntity master, final int expectedChildsCount) {

		final ChildEntity[] persistentChilds = master.getChilds();
		assertThat(expectedChildsCount).isEqualTo(persistentChilds.length);
	}

}
