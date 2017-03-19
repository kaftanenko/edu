package edu.java.jee.jpa.service.impl.hibernate.masterchild;

import edu.java.jee.jpa.model.masterchild.ChildEntity;
import edu.java.jee.jpa.model.masterchild.MasterEntity;

public class MasterPersistenceServiceTestHelper {

	// ... constants

	private static final int SOME_DATA_FIELD__ISNT_USED__BY__ANY_TEST_CASE__VALUE__1 = 1;

	// ... business methods

	public static MasterEntity get_New_Test_Master_With_Minimal_DataAmount() {

		final MasterEntity master = new MasterEntity();

		// ...

		return master;
	}

	public static MasterEntity get_New_Test_Master_With_Maximal_DataAmount_Without_Dependencies() {

		final MasterEntity master = get_New_Test_Master_With_Minimal_DataAmount();

		master.setSomeDataField(SOME_DATA_FIELD__ISNT_USED__BY__ANY_TEST_CASE__VALUE__1);

		return master;
	}

	public static MasterEntity get_New_Test_Master_With_Maximal_DataAmount_And_Dependencies() {

		final MasterEntity master = get_New_Test_Master_With_Maximal_DataAmount_Without_Dependencies();

		for (int i = 0; i < 3; i++) {

			final ChildEntity modelChildrBean = get_New_Test_Child_With_Maximal_DataAmount();
			master.addChild(modelChildrBean);
		}

		return master;
	}

	private static ChildEntity get_New_Test_Child_With_Maximal_DataAmount() {

		final ChildEntity child = new ChildEntity();

		child.setSomeDataField(SOME_DATA_FIELD__ISNT_USED__BY__ANY_TEST_CASE__VALUE__1);

		return child;
	}

}
