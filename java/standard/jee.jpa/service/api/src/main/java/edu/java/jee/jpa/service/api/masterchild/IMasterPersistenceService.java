package edu.java.jee.jpa.service.api.masterchild;

import edu.java.jee.jpa.model.masterchild.MasterEntity;
import edu.java.jee.jpa.service.api.common.IGenericPersistenceService;

public interface IMasterPersistenceService extends IGenericPersistenceService<MasterEntity> {

	// ... custom finder methods

	public abstract MasterEntity[] findMasterEntityBySomeDataField(Integer someFieldValue);

	public abstract MasterEntity[] findMasterEntityHavingChildsWithSomeDataFieldEqualsTo(
			Integer childSomeDataFieldValue);

}
