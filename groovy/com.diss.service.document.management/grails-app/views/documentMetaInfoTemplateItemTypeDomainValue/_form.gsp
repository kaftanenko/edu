<%@ page import="com.diss.document.impl.metainfo.template.DocumentMetaInfoTemplateItemTypeDomainValue" %>



<div class="fieldcontain ${hasErrors(bean: documentMetaInfoTemplateItemTypeDomainValueInstance, field: 'domainValue', 'error')} required">
	<label for="domainValue">
		<g:message code="documentMetaInfoTemplateItemTypeDomainValue.domainValue.label" default="Domain Value" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="domainValue" required="" value="${documentMetaInfoTemplateItemTypeDomainValueInstance?.domainValue}"/>

</div>

