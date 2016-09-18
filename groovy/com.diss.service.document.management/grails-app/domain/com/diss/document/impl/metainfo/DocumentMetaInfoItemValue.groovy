package com.diss.document.impl.metainfo

import com.diss.document.impl.metainfo.template.DocumentMetaInfoTemplateItemType



class DocumentMetaInfoItemValue {

	static belongsTo = { metaInfo: DocumentMetaInfo }

	DocumentMetaInfoTemplateItemType templateItemType

	String itemValue

	static constraints = {
	}
}
