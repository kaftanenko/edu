package com.diss.document.impl

import com.diss.document.impl.metainfo.template.DocumentMetaInfoTemplate



class DocumentTemplate {

	String name

	String description

	static hasMany = {  supportedMetaInfoTemplates : DocumentMetaInfoTemplate  }

	static constraints = {
		name blank: false, maxSize: 128
		description maxSize: 1024
	}
}
