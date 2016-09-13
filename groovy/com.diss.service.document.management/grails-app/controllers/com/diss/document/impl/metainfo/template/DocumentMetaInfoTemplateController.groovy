package com.diss.document.impl.metainfo.template



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DocumentMetaInfoTemplateController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DocumentMetaInfoTemplate.list(params), model:[documentMetaInfoTemplateInstanceCount: DocumentMetaInfoTemplate.count()]
    }

    def show(DocumentMetaInfoTemplate documentMetaInfoTemplateInstance) {
        respond documentMetaInfoTemplateInstance
    }

    def create() {
        respond new DocumentMetaInfoTemplate(params)
    }

    @Transactional
    def save(DocumentMetaInfoTemplate documentMetaInfoTemplateInstance) {
        if (documentMetaInfoTemplateInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoTemplateInstance.hasErrors()) {
            respond documentMetaInfoTemplateInstance.errors, view:'create'
            return
        }

        documentMetaInfoTemplateInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'documentMetaInfoTemplate.label', default: 'DocumentMetaInfoTemplate'), documentMetaInfoTemplateInstance.id])
                redirect documentMetaInfoTemplateInstance
            }
            '*' { respond documentMetaInfoTemplateInstance, [status: CREATED] }
        }
    }

    def edit(DocumentMetaInfoTemplate documentMetaInfoTemplateInstance) {
        respond documentMetaInfoTemplateInstance
    }

    @Transactional
    def update(DocumentMetaInfoTemplate documentMetaInfoTemplateInstance) {
        if (documentMetaInfoTemplateInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoTemplateInstance.hasErrors()) {
            respond documentMetaInfoTemplateInstance.errors, view:'edit'
            return
        }

        documentMetaInfoTemplateInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'DocumentMetaInfoTemplate.label', default: 'DocumentMetaInfoTemplate'), documentMetaInfoTemplateInstance.id])
                redirect documentMetaInfoTemplateInstance
            }
            '*'{ respond documentMetaInfoTemplateInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(DocumentMetaInfoTemplate documentMetaInfoTemplateInstance) {

        if (documentMetaInfoTemplateInstance == null) {
            notFound()
            return
        }

        documentMetaInfoTemplateInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'DocumentMetaInfoTemplate.label', default: 'DocumentMetaInfoTemplate'), documentMetaInfoTemplateInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'documentMetaInfoTemplate.label', default: 'DocumentMetaInfoTemplate'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
