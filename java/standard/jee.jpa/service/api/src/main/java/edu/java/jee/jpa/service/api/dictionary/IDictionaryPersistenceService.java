package edu.java.jee.jpa.service.api.dictionary;

import edu.java.jee.jpa.model.dictionary.DictionaryEntryEntity;

public interface IDictionaryPersistenceService {

	// ... custom finder methods

	public abstract DictionaryEntryEntity[] findDictionaryEntryBySomeUseCase(String someUseCase);

}
