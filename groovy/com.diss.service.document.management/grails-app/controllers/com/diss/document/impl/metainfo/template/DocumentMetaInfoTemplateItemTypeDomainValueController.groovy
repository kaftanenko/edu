package com.diss.document.impl.metainfo.template



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DocumentMetaInfoTemplateItemTypeDomainValueController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DocumentMetaInfoTemplateItemTypeDomainValue.list(params), model:[documentMetaInfoTemplateItemTypeDomainValueInstanceCount: DocumentMetaInfoTemplateItemTypeDomainValue.count()]
    }

    def show(DocumentMetaInfoTemplateItemTypeDomainValue documentMetaInfoTemplateItemTypeDomainValueInstance) {
        respond documentMetaInfoTemplateItemTypeDomainValueInstance
    }

    def create() {
        respond new DocumentMetaInfoTemplateItemTypeDomainValue(params)
    }

    @Transactional
    def save(DocumentMetaInfoTemplateItemTypeDomainValue documentMetaInfoTemplateItemTypeDomainValueInstance) {
        if (documentMetaInfoTemplateItemTypeDomainValueInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoTemplateItemTypeDomainValueInstance.hasErrors()) {
            respond documentMetaInfoTemplateItemTypeDomainValueInstance.errors, view:'create'
            return
        }

        documentMetaInfoTemplateItemTypeDomainValueInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'documentMetaInfoTemplateItemTypeDomainValue.label', default: 'DocumentMetaInfoTemplateItemTypeDomainValue'), documentMetaInfoTemplateItemTypeDomainValueInstance.id])
                redirect documentMetaInfoTemplateItemTypeDomainValueInstance
            }
            '*' { respond documentMetaInfoTemplateItemTypeDomainValueInstance, [status: CREATED] }
        }
    }

    def edit(DocumentMetaInfoTemplateItemTypeDomainValue documentMetaInfoTemplateItemTypeDomainValueInstance) {
        respond documentMetaInfoTemplateItemTypeDomainValueInstance
    }

    @Transactional
    def update(DocumentMetaInfoTemplateItemTypeDomainValue documentMetaInfoTemplateItemTypeDomainValueInstance) {
        if (documentMetaInfoTemplateItemTypeDomainValueInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoTemplateItemTypeDomainValueInstance.hasErrors()) {
            respond documentMetaInfoTemplateItemTypeDomainValueInstance.errors, view:'edit'
            return
        }

        documentMetaInfoTemplateItemTypeDomainValueInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'DocumentMetaInfoTemplateItemTypeDomainValue.label', default: 'DocumentMetaInfoTemplateItemTypeDomainValue'), documentMetaInfoTemplateItemTypeDomainValueInstance.id])
                redirect documentMetaInfoTemplateItemTypeDomainValueInstance
            }
            '*'{ respond documentMetaInfoTemplateItemTypeDomainValueInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(DocumentMetaInfoTemplateItemTypeDomainValue documentMetaInfoTemplateItemTypeDomainValueInstance) {

        if (documentMetaInfoTemplateItemTypeDomainValueInstance == null) {
            notFound()
            return
        }

        documentMetaInfoTemplateItemTypeDomainValueInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'DocumentMetaInfoTemplateItemTypeDomainValue.label', default: 'DocumentMetaInfoTemplateItemTypeDomainValue'), documentMetaInfoTemplateItemTypeDomainValueInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'documentMetaInfoTemplateItemTypeDomainValue.label', default: 'DocumentMetaInfoTemplateItemTypeDomainValue'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
