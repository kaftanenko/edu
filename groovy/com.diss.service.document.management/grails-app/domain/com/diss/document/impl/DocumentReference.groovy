package com.diss.document.impl

class DocumentReference {

	String documentUrl

	DocumentTemplate template

	static constraints = { documentUrl maxSize: 1024 }
}
