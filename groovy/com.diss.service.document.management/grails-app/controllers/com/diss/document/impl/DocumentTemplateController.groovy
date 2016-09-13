package com.diss.document.impl



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DocumentTemplateController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DocumentTemplate.list(params), model:[documentTemplateInstanceCount: DocumentTemplate.count()]
    }

    def show(DocumentTemplate documentTemplateInstance) {
        respond documentTemplateInstance
    }

    def create() {
        respond new DocumentTemplate(params)
    }

    @Transactional
    def save(DocumentTemplate documentTemplateInstance) {
        if (documentTemplateInstance == null) {
            notFound()
            return
        }

        if (documentTemplateInstance.hasErrors()) {
            respond documentTemplateInstance.errors, view:'create'
            return
        }

        documentTemplateInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'documentTemplate.label', default: 'DocumentTemplate'), documentTemplateInstance.id])
                redirect documentTemplateInstance
            }
            '*' { respond documentTemplateInstance, [status: CREATED] }
        }
    }

    def edit(DocumentTemplate documentTemplateInstance) {
        respond documentTemplateInstance
    }

    @Transactional
    def update(DocumentTemplate documentTemplateInstance) {
        if (documentTemplateInstance == null) {
            notFound()
            return
        }

        if (documentTemplateInstance.hasErrors()) {
            respond documentTemplateInstance.errors, view:'edit'
            return
        }

        documentTemplateInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'DocumentTemplate.label', default: 'DocumentTemplate'), documentTemplateInstance.id])
                redirect documentTemplateInstance
            }
            '*'{ respond documentTemplateInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(DocumentTemplate documentTemplateInstance) {

        if (documentTemplateInstance == null) {
            notFound()
            return
        }

        documentTemplateInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'DocumentTemplate.label', default: 'DocumentTemplate'), documentTemplateInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'documentTemplate.label', default: 'DocumentTemplate'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
