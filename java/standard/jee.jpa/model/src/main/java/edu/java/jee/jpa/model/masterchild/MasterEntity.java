package edu.java.jee.jpa.model.masterchild;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import edu.java.jee.jpa.model.common.GenericEntityObject;

@Entity
@Table(name = _DbMapping.TABLE_MODEL_MASTER)
public class MasterEntity extends GenericEntityObject {

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

	// ... reference properties to depended entities

	@OneToMany(mappedBy = "master", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private final List<ChildEntity> childs = new ArrayList<ChildEntity>();

	// ... constructors

	public MasterEntity() {
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

	// ...

	public ChildEntity[] getChilds() {

		return childs.toArray(new ChildEntity[] {});
	}

	public void addChild(final ChildEntity child) {

		child.setMaster(this);
		childs.add(child);
	}

	public void removeChild(final ChildEntity child) {

		childs.remove(child);
	}

}
