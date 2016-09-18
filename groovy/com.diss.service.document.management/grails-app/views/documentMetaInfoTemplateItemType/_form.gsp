<%@ page import="com.diss.document.impl.metainfo.template.DocumentMetaInfoTemplateItemType" %>



<div class="fieldcontain ${hasErrors(bean: documentMetaInfoTemplateItemTypeInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="documentMetaInfoTemplateItemType.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="128" required="" value="${documentMetaInfoTemplateItemTypeInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: documentMetaInfoTemplateItemTypeInstance, field: 'domainValuesAreExtendable', 'error')} ">
	<label for="domainValuesAreExtendable">
		<g:message code="documentMetaInfoTemplateItemType.domainValuesAreExtendable.label" default="Domain Values Are Extendable" />
		
	</label>
	<g:checkBox name="domainValuesAreExtendable" value="${documentMetaInfoTemplateItemTypeInstance?.domainValuesAreExtendable}" />

</div>

<div class="fieldcontain ${hasErrors(bean: documentMetaInfoTemplateItemTypeInstance, field: 'multiplicity', 'error')} required">
	<label for="multiplicity">
		<g:message code="documentMetaInfoTemplateItemType.multiplicity.label" default="Multiplicity" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="multiplicity" required="" value="${documentMetaInfoTemplateItemTypeInstance?.multiplicity}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: documentMetaInfoTemplateItemTypeInstance, field: 'sortOrder', 'error')} required">
	<label for="sortOrder">
		<g:message code="documentMetaInfoTemplateItemType.sortOrder.label" default="Sort Order" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="sortOrder" type="number" value="${documentMetaInfoTemplateItemTypeInstance.sortOrder}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: documentMetaInfoTemplateItemTypeInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="documentMetaInfoTemplateItemType.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="type" required="" value="${documentMetaInfoTemplateItemTypeInstance?.type}"/>

</div>

