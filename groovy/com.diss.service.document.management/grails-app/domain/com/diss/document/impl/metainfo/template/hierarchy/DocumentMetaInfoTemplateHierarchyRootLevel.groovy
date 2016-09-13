package com.diss.document.impl.metainfo.template.hierarchy


class DocumentMetaInfoTemplateHierarchyRootLevel extends DocumentMetaInfoTemplateHierarchyLevel {

	String name

	String description

	static constraints = {
		name minSize: 1, maxSize: 128
		description maxSize: 1024
	}
}
