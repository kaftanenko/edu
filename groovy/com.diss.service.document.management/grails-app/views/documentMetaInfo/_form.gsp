<%@ page import="com.diss.document.impl.metainfo.DocumentMetaInfo" %>



<div class="fieldcontain ${hasErrors(bean: documentMetaInfoInstance, field: 'documentReference', 'error')} required">
	<label for="documentReference">
		<g:message code="documentMetaInfo.documentReference.label" default="Document Reference" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="documentReference" name="documentReference.id" from="${com.diss.document.impl.DocumentReference.list()}" optionKey="id" required="" value="${documentMetaInfoInstance?.documentReference?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: documentMetaInfoInstance, field: 'template', 'error')} required">
	<label for="template">
		<g:message code="documentMetaInfo.template.label" default="Template" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="template" name="template.id" from="${com.diss.document.impl.metainfo.template.DocumentMetaInfoTemplate.list()}" optionKey="id" required="" value="${documentMetaInfoInstance?.template?.id}" class="many-to-one"/>

</div>

