package com.diss.document.impl.metainfo.template

class DocumentMetaInfoTemplate {

	String name

	String description

	static hasMany = { itemTypes: DocumentMetaInfoTemplateItemType }

	static constraints = {

		name blank: false, maxSize: 128
		description maxSize: 1024
	}
}
