package com.diss.document.impl.metainfo



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DocumentMetaInfoItemValueController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DocumentMetaInfoItemValue.list(params), model:[documentMetaInfoItemValueInstanceCount: DocumentMetaInfoItemValue.count()]
    }

    def show(DocumentMetaInfoItemValue documentMetaInfoItemValueInstance) {
        respond documentMetaInfoItemValueInstance
    }

    def create() {
        respond new DocumentMetaInfoItemValue(params)
    }

    @Transactional
    def save(DocumentMetaInfoItemValue documentMetaInfoItemValueInstance) {
        if (documentMetaInfoItemValueInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoItemValueInstance.hasErrors()) {
            respond documentMetaInfoItemValueInstance.errors, view:'create'
            return
        }

        documentMetaInfoItemValueInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'documentMetaInfoItemValue.label', default: 'DocumentMetaInfoItemValue'), documentMetaInfoItemValueInstance.id])
                redirect documentMetaInfoItemValueInstance
            }
            '*' { respond documentMetaInfoItemValueInstance, [status: CREATED] }
        }
    }

    def edit(DocumentMetaInfoItemValue documentMetaInfoItemValueInstance) {
        respond documentMetaInfoItemValueInstance
    }

    @Transactional
    def update(DocumentMetaInfoItemValue documentMetaInfoItemValueInstance) {
        if (documentMetaInfoItemValueInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoItemValueInstance.hasErrors()) {
            respond documentMetaInfoItemValueInstance.errors, view:'edit'
            return
        }

        documentMetaInfoItemValueInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'DocumentMetaInfoItemValue.label', default: 'DocumentMetaInfoItemValue'), documentMetaInfoItemValueInstance.id])
                redirect documentMetaInfoItemValueInstance
            }
            '*'{ respond documentMetaInfoItemValueInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(DocumentMetaInfoItemValue documentMetaInfoItemValueInstance) {

        if (documentMetaInfoItemValueInstance == null) {
            notFound()
            return
        }

        documentMetaInfoItemValueInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'DocumentMetaInfoItemValue.label', default: 'DocumentMetaInfoItemValue'), documentMetaInfoItemValueInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'documentMetaInfoItemValue.label', default: 'DocumentMetaInfoItemValue'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
