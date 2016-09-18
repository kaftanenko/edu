package com.diss.document.impl.metainfo.template



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DocumentMetaInfoTemplateItemTypeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DocumentMetaInfoTemplateItemType.list(params), model:[documentMetaInfoTemplateItemTypeInstanceCount: DocumentMetaInfoTemplateItemType.count()]
    }

    def show(DocumentMetaInfoTemplateItemType documentMetaInfoTemplateItemTypeInstance) {
        respond documentMetaInfoTemplateItemTypeInstance
    }

    def create() {
        respond new DocumentMetaInfoTemplateItemType(params)
    }

    @Transactional
    def save(DocumentMetaInfoTemplateItemType documentMetaInfoTemplateItemTypeInstance) {
        if (documentMetaInfoTemplateItemTypeInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoTemplateItemTypeInstance.hasErrors()) {
            respond documentMetaInfoTemplateItemTypeInstance.errors, view:'create'
            return
        }

        documentMetaInfoTemplateItemTypeInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'documentMetaInfoTemplateItemType.label', default: 'DocumentMetaInfoTemplateItemType'), documentMetaInfoTemplateItemTypeInstance.id])
                redirect documentMetaInfoTemplateItemTypeInstance
            }
            '*' { respond documentMetaInfoTemplateItemTypeInstance, [status: CREATED] }
        }
    }

    def edit(DocumentMetaInfoTemplateItemType documentMetaInfoTemplateItemTypeInstance) {
        respond documentMetaInfoTemplateItemTypeInstance
    }

    @Transactional
    def update(DocumentMetaInfoTemplateItemType documentMetaInfoTemplateItemTypeInstance) {
        if (documentMetaInfoTemplateItemTypeInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoTemplateItemTypeInstance.hasErrors()) {
            respond documentMetaInfoTemplateItemTypeInstance.errors, view:'edit'
            return
        }

        documentMetaInfoTemplateItemTypeInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'DocumentMetaInfoTemplateItemType.label', default: 'DocumentMetaInfoTemplateItemType'), documentMetaInfoTemplateItemTypeInstance.id])
                redirect documentMetaInfoTemplateItemTypeInstance
            }
            '*'{ respond documentMetaInfoTemplateItemTypeInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(DocumentMetaInfoTemplateItemType documentMetaInfoTemplateItemTypeInstance) {

        if (documentMetaInfoTemplateItemTypeInstance == null) {
            notFound()
            return
        }

        documentMetaInfoTemplateItemTypeInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'DocumentMetaInfoTemplateItemType.label', default: 'DocumentMetaInfoTemplateItemType'), documentMetaInfoTemplateItemTypeInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'documentMetaInfoTemplateItemType.label', default: 'DocumentMetaInfoTemplateItemType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
