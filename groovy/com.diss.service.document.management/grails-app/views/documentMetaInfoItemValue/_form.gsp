<%@ page import="com.diss.document.impl.metainfo.DocumentMetaInfoItemValue" %>



<div class="fieldcontain ${hasErrors(bean: documentMetaInfoItemValueInstance, field: 'itemValue', 'error')} required">
	<label for="itemValue">
		<g:message code="documentMetaInfoItemValue.itemValue.label" default="Item Value" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="itemValue" required="" value="${documentMetaInfoItemValueInstance?.itemValue}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: documentMetaInfoItemValueInstance, field: 'templateItemType', 'error')} required">
	<label for="templateItemType">
		<g:message code="documentMetaInfoItemValue.templateItemType.label" default="Template Item Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="templateItemType" name="templateItemType.id" from="${com.diss.document.impl.metainfo.template.DocumentMetaInfoTemplateItemType.list()}" optionKey="id" required="" value="${documentMetaInfoItemValueInstance?.templateItemType?.id}" class="many-to-one"/>

</div>

