package com.diss.document.impl.metainfo.template

class DocumentMetaInfoTemplateItemType {

	static belongsTo = { template: DocumentMetaInfoTemplate }

	String name

	int sortOrder

	String type

	String multiplicity

	static hasMany = { domainValues: DocumentMetaInfoTemplateItemTypeDomainValue }

	Boolean domainValuesAreExtendable

	static constraints = {
		name blank: false, maxSize: 128
	}

	static mapping = { sortOrder employeename: "asc" }
}
