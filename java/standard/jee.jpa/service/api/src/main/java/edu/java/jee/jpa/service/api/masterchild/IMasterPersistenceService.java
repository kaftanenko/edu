package edu.java.jee.jpa.service.api.masterchild;

import edu.java.jee.jpa.model.masterchild.MasterEntity;
import edu.java.jee.jpa.service.api.common.IGenericPersistenceService;

public interface IMasterPersistenceService extends IGenericPersistenceService<MasterEntity> {

	// ... custom finder methods

	public abstract MasterEntity[] findMasterBySomeDataField(Integer someDataFieldValue);

	public abstract MasterEntity[] findMasterHavingChildrenWithSomeDataField(Integer childSomeDataFieldValue);

}
