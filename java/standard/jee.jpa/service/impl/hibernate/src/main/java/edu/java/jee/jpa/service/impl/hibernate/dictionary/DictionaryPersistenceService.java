package edu.java.jee.jpa.service.impl.hibernate.dictionary;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import edu.java.jee.jpa.model.dictionary.DictionaryEntryEntity;
import edu.java.jee.jpa.service.api.dictionary.IDictionaryPersistenceService;
import edu.java.jee.jpa.service.impl.hibernate.common.error.BusinessErrorHelper;

public class DictionaryPersistenceService implements IDictionaryPersistenceService {

	// ... context properties

	protected final EntityManagerFactory emf;

	// ... constructors

	public DictionaryPersistenceService(final EntityManagerFactory emf) {

		this.emf = emf;
	}

	// ... custom finder methods

	@Override
	public DictionaryEntryEntity[] findDictionaryEntryEntityByUseCase(final String useCase) {

		final EntityManager em = emf.createEntityManager();

		try {

			final String criteriaQuery = "select dictionaryEntry from DictionaryEntryEntity dictionaryEntry where dictionaryEntry.someUseCase = :useCaseValue order by dictionaryEntry.someKey";
			final Query query = em.createQuery(criteriaQuery);
			query.setParameter("useCaseValue", useCase);

			final List<?> result = query.getResultList();
			final DictionaryEntryEntity[] resultAsArray = result.toArray(new DictionaryEntryEntity[0]);

			return resultAsArray;

		} catch (final Exception ex) {
			throw BusinessErrorHelper.handleFatalException(ex);
		} finally {
			em.close();
		}
	}

}
