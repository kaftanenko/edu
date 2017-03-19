package edu.java.jee.jpa.service.impl.hibernate.masterchild;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import edu.java.jee.jpa.model.masterchild.MasterEntity;
import edu.java.jee.jpa.service.api.masterchild.IMasterPersistenceService;
import edu.java.jee.jpa.service.impl.hibernate.common.GenericPersistenceService;
import edu.java.jee.jpa.service.impl.hibernate.common.error.BusinessErrorHelper;

public class MasterPersistenceService extends GenericPersistenceService<MasterEntity>
		implements IMasterPersistenceService {

	// ... constructors

	public MasterPersistenceService(final EntityManagerFactory emf) {

		super(emf);
	}

	// ... finder methods

	@Override
	public MasterEntity[] findMasterBySomeDataField(final Integer masterSomeDataFieldValue) {

		final EntityManager em = emf.createEntityManager();

		try {

			final String criteriaQuery = "select master from MasterEntity master where master.someDataField = :someDataFieldValue";
			final Query query = em.createQuery(criteriaQuery);

			query.setParameter("someDataFieldValue", masterSomeDataFieldValue);

			final List<?> result = query.getResultList();
			return result.toArray(new MasterEntity[0]);
		} catch (final Exception ex) {
			throw BusinessErrorHelper.handleFatalException(ex);
		} finally {
			em.close();
		}
	}

	@Override
	public MasterEntity[] findMasterHavingChildrenWithSomeDataField(final Integer childSomeDataFieldValue) {

		final EntityManager em = emf.createEntityManager();

		try {

			final String criteriaQuery = "select master from MasterEntity master, ChildEntity child where master.id = child.master.id and child.someDataField = :someDataFieldValue";
			final Query query = em.createQuery(criteriaQuery);

			query.setParameter("someDataFieldValue", childSomeDataFieldValue);

			final List<?> result = query.getResultList();
			return result.toArray(new MasterEntity[0]);
		} catch (final Exception ex) {
			throw BusinessErrorHelper.handleFatalException(ex);
		} finally {
			em.close();
		}
	}

	// ... helper methods

	@Override
	protected MasterEntity getMasterObject(final EntityManager em, final long masterEntityId) {

		return _getById(em, MasterEntity.class, masterEntityId);
	}

	@Override
	protected void saveOrUpdateMasterObject(final EntityManager em, final MasterEntity masterEntity) {

		_saveOrUpdate(em, masterEntity);
	}

	@Override
	protected void detachMasterObject(final EntityManager em, final MasterEntity masterEntity) {

		_detach(em, masterEntity);
	}

}
