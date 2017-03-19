package edu.java.jee.jpa.model.dictionary;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = _DbMapping.TABLE_MODEL_DICTIONARY)
public class DictionaryEntryEntity implements Serializable {

	// ... constants

	public static final long serialVersionUID = 1L;

	// ... properties

	@Id
	private String someKey;

	private String someValue;

	private String someUseCase;

	// ... getter/setter methods

	public String getSomeKey() {
		return someKey;
	}

	public String getSomeValue() {
		return someValue;
	}

	public String getSomeUseCase() {
		return someUseCase;
	}

}
