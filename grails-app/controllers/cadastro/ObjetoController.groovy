package cadastro

import org.springframework.dao.DataIntegrityViolationException

class ObjetoController {
	def aux='';def auxNome='';def url=''
    static allowedMethods = [save: "GET", update: "POST", delete: "POST"]
    //Paulo 
    def index() {
        redirect(action: "list", params: params)
    }
	def qrcode(){ 
	    
		
		 if(params.valQrcode=="Qrcode" && request.method=="POST"){
		 
		   def processo= "cmd /c start chrome https://bpmncloud.herokuapp.com/".execute()
			
		}	
		    aux='<img align="center" src="https://chart.googleapis.com/chart?chs=150x200&amp;cht=qr&amp;chl=Qrcode" title="Download: App Android">'
	}
	
	def camera(){
	
	    //println "Valor Request...:"+request.method
	
	    if(params.valQrcode=="Qrcode"){
		  //println "Valor 2"+params.valQrcode
		  //"calc.exe".execute()
		  def processo= "cmd /c start chrome https://bpmncloud.herokuapp.com/".execute()
		  //println "${processo.text}"
		  aux='Deu certo!!'
		  //redirect(url:"http://www.google.com")
		  //response.sendRedirect("url:"http://www.google.com"")

		}else{
		  //println "Nao é a senha...:"+request.method
		}
	
	    
		   
	}

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [objetoInstanceList: Objeto.list(params), objetoInstanceTotal: Objeto.count()]
    }

    def create() {
        [objetoInstance: new Objeto(params)]
    }

    def save() {
        def objetoInstance = new Objeto(params)
		
		println "Valor..:"+request.method	
		  
        if (!objetoInstance.save(flush: true)) {
            render(view: "create", model: [objetoInstance: objetoInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'objeto.label', default: 'Objeto'), objetoInstance.id])
        redirect(action: "show", id: objetoInstance.id)
    }

    def show() {
        def objetoInstance = Objeto.get(params.id)
        if (!objetoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'objeto.label', default: 'Objeto'), params.id])
            redirect(action: "list")
            return
        }

        [objetoInstance: objetoInstance]
    }

    def edit() {
        def objetoInstance = Objeto.get(params.id)
        if (!objetoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'objeto.label', default: 'Objeto'), params.id])
            redirect(action: "list")
            return
        }

        [objetoInstance: objetoInstance]
    }

    def update() {
        def objetoInstance = Objeto.get(params.id)
        if (!objetoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'objeto.label', default: 'Objeto'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (objetoInstance.version > version) {
                objetoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'objeto.label', default: 'Objeto')] as Object[],
                          "Another user has updated this Objeto while you were editing")
                render(view: "edit", model: [objetoInstance: objetoInstance])
                return
            }
        }

        objetoInstance.properties = params

        if (!objetoInstance.save(flush: true)) {
            render(view: "edit", model: [objetoInstance: objetoInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'objeto.label', default: 'Objeto'), objetoInstance.id])
        redirect(action: "show", id: objetoInstance.id)
    }

    def delete() {
        def objetoInstance = Objeto.get(params.id)
        if (!objetoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'objeto.label', default: 'Objeto'), params.id])
            redirect(action: "list")
            return
        }

        try {
            objetoInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'objeto.label', default: 'Objeto'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'objeto.label', default: 'Objeto'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
