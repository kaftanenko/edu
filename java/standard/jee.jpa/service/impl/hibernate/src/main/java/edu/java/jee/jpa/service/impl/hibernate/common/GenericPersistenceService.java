package edu.java.jee.jpa.service.impl.hibernate.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import edu.java.jee.jpa.model.common.identity.IIdentifiableByLong;
import edu.java.jee.jpa.service.api.common.IGenericPersistenceService;
import edu.java.jee.jpa.service.impl.hibernate.common.error.BusinessErrorHelper;
import edu.java.jee.jpa.service.impl.hibernate.common.error.DataErrorHelper;

public abstract class GenericPersistenceService<MASTER_ENTITY_TYPE>
		implements IGenericPersistenceService<MASTER_ENTITY_TYPE> {

	// ... context properties

	protected final EntityManagerFactory emf;

	// ... constructors

	public GenericPersistenceService(final EntityManagerFactory emf) {

		this.emf = emf;
	}

	// ... CRUD methods: getById(..)

	@Override
	// @Transactional(isolation = Isolation.READ_COMMITTED, propagation =
	// Propagation.SUPPORTS)
	public final MASTER_ENTITY_TYPE getById(final long masterEntityId) {

		final EntityManager em = emf.createEntityManager();

		try {

			final MASTER_ENTITY_TYPE entity = getMasterObject(em, masterEntityId);

			detachMasterObject(em, entity);

			return entity;

		} catch (final Exception ex) {
			throw BusinessErrorHelper.handleFatalException(ex);
		} finally {
			em.close();
		}
	}

	/**
	 * Template-Methode zum Implementieren reiner DAO-Funktionalität in der
	 * abgeleiteten Klassen.
	 * <p>
	 * Es sollte hier keine Acht solchen Fragen gegeben werden, wie die
	 * Transaktionsverwaltung, dauerhafte Bindung einer Entity-Instanz an einen
	 * DB-Objekt etc., da diese in der aufrufenden Methode
	 * {@link #getById(long)} bereits geregelt sind.
	 * <p>
	 * Beim Ausimplementieren dieser Methode sollten aktiv die Hilfsmethode(-n)
	 * <ul>
	 * <li>{@link #_getById(EntityManager, Class, long)}</li>
	 * </ul>
	 * eingesetzt werden, die unmittelbar mit dem JPA-Framework arbeiten.
	 * <p>
	 * Falls eine Master-Entity mit den JPA-Mitteln richtig annotiert ist und es
	 * keine Sonderregelungen für ihre Aufbau gibt, reicht einen einzigen Aufruf
	 * so einer Hilfsmethode am Master-Entity, um die gesamte
	 * Persistence-Operation an ihr und all ihrer Abhängigkeiten zu vollenden.
	 * 
	 * @param em
	 * @param masterEntityId
	 * @return
	 */
	protected abstract MASTER_ENTITY_TYPE getMasterObject(final EntityManager em, final long masterEntityId);

	protected static <ENTITY_TYPE> ENTITY_TYPE _getById(final EntityManager em, final Class<ENTITY_TYPE> entityType,
			final long entityId) {

		final ENTITY_TYPE entity = em.find(entityType, entityId);

		if (entity == null) {
			throw DataErrorHelper.handleDataNotFoundException(entityType, "id", entityId);
		}

		return entity;
	}

	// ... CRUD methods: saveOrUpdate(..)

	// @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor =
	// Exception.class)
	@Override
	public final void saveOrUpdate(final MASTER_ENTITY_TYPE analyse) {

		try {

			final EntityManager em = emf.createEntityManager();

			try {

				em.getTransaction().begin();
				{
					saveOrUpdateMasterObject(em, analyse);
				}

				em.getTransaction().commit();

				detachMasterObject(em, analyse);

			} catch (final Exception ex) {

				em.getTransaction().rollback();

				throw ex;
			} finally {
				em.close();
			}

		} catch (final Exception ex) {
			throw BusinessErrorHelper.handleFatalException(ex);
		}
	}

	/**
	 * Template-Methode zum Implementieren reiner DAO-Funktionalität in der
	 * abgeleiteten Klassen.
	 * <p>
	 * Es sollte hier keine Acht solchen Fragen gegeben werden, wie die
	 * Transaktionsverwaltung, dauerhafte Bindung einer Entity-Instanz an einen
	 * DB-Objekt etc., da diese in der aufrufenden Methode
	 * {@link #saveOrUpdate(Object)} bereits geregelt sind.
	 * <p>
	 * Beim Ausimplementieren dieser Methode sollten aktiv die Hilfsmethode(-n)
	 * <ul>
	 * <li>{@link #_saveOrUpdate(EntityManager, Object)}</li>
	 * <li>{@link #_saveOrUpdate(EntityManager, IIdentifiableByLong)}</li>
	 * </ul>
	 * eingesetzt werden, die unmittelbar mit dem JPA-Framework arbeiten.
	 * <p>
	 * Falls eine Master-Entity mit den JPA-Mitteln richtig annotiert ist und es
	 * keine Sonderregelungen für ihre Aufbau gibt, reicht einen einzigen Aufruf
	 * so einer Hilfsmethode am Master-Entity, um die gesamte
	 * Persistence-Operation an ihr und all ihrer Abhängigkeiten zu vollenden.
	 * 
	 * @param em
	 * @param entity
	 */
	protected abstract void saveOrUpdateMasterObject(final EntityManager em, final MASTER_ENTITY_TYPE entity);

	protected static void _saveOrUpdate(final EntityManager em, final Object entity) {

		try {
			_saveOrUpdate(em, entity);
		} catch (final ClassCastException ex) {
			throw BusinessErrorHelper.handleImplementationException("Der Entitytyp " + entity.getClass().getName()
					+ " muss die IIdentifiableByLong-Schnittstelle implementieren, sonst wird's durch die aktuelle Implementierung nicht unterstützt.");
		}
	}

	protected static void _saveOrUpdate(final EntityManager em, final IIdentifiableByLong entity) {

		final boolean isExistingEntity = entity.hasId();
		if (isExistingEntity) {
			em.merge(entity);
		} else {
			em.persist(entity);
		}
	}

	// ... helper methods: detach

	/**
	 * Template-Methode zum Implementieren reiner DAO-Funktionalität in der
	 * abgeleiteten Klassen.
	 * <p>
	 * Es sollte hier keine Acht solchen Fragen gegeben werden, wie die
	 * Transaktionsverwaltung, dauerhafte Bindung einer Entity-Instanz an einen
	 * DB-Objekt etc., da diese in der aufrufenden Methoden bereits geregelt
	 * sind.
	 * <p>
	 * Beim Ausimplementieren dieser Methode sollten aktiv die Hilfsmethode(-n)
	 * <ul>
	 * <li>{@link #_detach(EntityManager, Object)}</li>
	 * </ul>
	 * eingesetzt werden, die unmittelbar mit dem JPA-Framework arbeiten.
	 * <p>
	 * Falls eine Master-Entity mit den JPA-Mitteln richtig annotiert ist und es
	 * keine Sonderregelungen für ihre Aufbau gibt, reicht einen einzigen Aufruf
	 * so einer Hilfsmethode am Master-Entity, um die gesamte
	 * Persistence-Operation an ihr und all ihrer Abhängigkeiten zu vollenden.
	 * 
	 * @param em
	 * @param masterEntityId
	 * @return
	 */
	protected abstract void detachMasterObject(final EntityManager em, final MASTER_ENTITY_TYPE analyse);

	protected static void _detach(final EntityManager em, final Object entity) {

		em.detach(entity);
	}

}
