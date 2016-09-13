package com.diss.document.impl.metainfo.template

class DocumentMetaInfoTemplateItemTypeDomainValue {

	static belongsTo = { domainItemType: DocumentMetaInfoTemplateItemType }

	String domainValue

	static constraints = {
	}
}
