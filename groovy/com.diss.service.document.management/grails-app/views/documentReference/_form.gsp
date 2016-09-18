<%@ page import="com.diss.document.impl.DocumentReference" %>



<div class="fieldcontain ${hasErrors(bean: documentReferenceInstance, field: 'documentUrl', 'error')} required">
	<label for="documentUrl">
		<g:message code="documentReference.documentUrl.label" default="Document Url" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="documentUrl" cols="40" rows="5" maxlength="1024" required="" value="${documentReferenceInstance?.documentUrl}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: documentReferenceInstance, field: 'template', 'error')} required">
	<label for="template">
		<g:message code="documentReference.template.label" default="Template" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="template" name="template.id" from="${com.diss.document.impl.DocumentTemplate.list()}" optionKey="id" required="" value="${documentReferenceInstance?.template?.id}" class="many-to-one"/>

</div>

