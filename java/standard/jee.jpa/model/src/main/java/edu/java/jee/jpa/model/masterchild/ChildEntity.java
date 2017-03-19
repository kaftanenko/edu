package edu.java.jee.jpa.model.masterchild;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import edu.java.jee.jpa.model.common.GenericEntityObject;

@Entity
@Table(name = _DbMapping.TABLE_MODEL_CHILD)
public class ChildEntity extends GenericEntityObject {

	// ... constants

	public static final long serialVersionUID = 1L;

	// ... ID property

	@Id
	@GeneratedValue(generator = "genericGenerator")
	@GenericGenerator(name = "genericGenerator", strategy = "increment")
	private Long id;

	// ... own data properties

	// @Digits(integer = 1, fraction = 0)
	private Integer someDataField;

	@ManyToOne
	@JoinColumn(name = "MASTER_ID")
	private MasterEntity master;

	// ... constructors

	public ChildEntity() {
		super();
	}

	// ... getter/setter methods

	@Override
	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Integer getSomeDataField() {
		return someDataField;
	}

	public void setSomeDataField(final Integer someDataField) {
		this.someDataField = someDataField;
	}

	public void setMaster(final MasterEntity master) {

		this.master = master;
	}

}
