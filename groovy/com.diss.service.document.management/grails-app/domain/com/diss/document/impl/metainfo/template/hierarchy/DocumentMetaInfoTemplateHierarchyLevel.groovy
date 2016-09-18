package com.diss.document.impl.metainfo.template.hierarchy

import com.diss.document.impl.metainfo.template.DocumentMetaInfoTemplate



class DocumentMetaInfoTemplateHierarchyLevel {

	DocumentMetaInfoTemplate template

	static hasMany = { subLevels: DocumentMetaInfoTemplateHierarchyLevel }

	static constraints = {
	}
}
