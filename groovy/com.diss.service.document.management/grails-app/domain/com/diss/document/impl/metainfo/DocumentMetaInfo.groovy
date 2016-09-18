package com.diss.document.impl.metainfo

import com.diss.document.impl.DocumentReference
import com.diss.document.impl.metainfo.template.DocumentMetaInfoTemplate


class DocumentMetaInfo {

	DocumentReference documentReference

	DocumentMetaInfoTemplate template

	static hasMany = { itemValues: DocumentMetaInfoItemValue }

	static constraints = {
	}
}
