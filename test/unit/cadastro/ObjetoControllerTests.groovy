package cadastro



import org.junit.*
import grails.test.mixin.*

@TestFor(ObjetoController)
@Mock(Objeto)
class ObjetoControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/objeto/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.objetoInstanceList.size() == 0
        assert model.objetoInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.objetoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.objetoInstance != null
        assert view == '/objeto/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/objeto/show/1'
        assert controller.flash.message != null
        assert Objeto.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/objeto/list'


        populateValidParams(params)
        def objeto = new Objeto(params)

        assert objeto.save() != null

        params.id = objeto.id

        def model = controller.show()

        assert model.objetoInstance == objeto
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/objeto/list'


        populateValidParams(params)
        def objeto = new Objeto(params)

        assert objeto.save() != null

        params.id = objeto.id

        def model = controller.edit()

        assert model.objetoInstance == objeto
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/objeto/list'

        response.reset()


        populateValidParams(params)
        def objeto = new Objeto(params)

        assert objeto.save() != null

        // test invalid parameters in update
        params.id = objeto.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/objeto/edit"
        assert model.objetoInstance != null

        objeto.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/objeto/show/$objeto.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        objeto.clearErrors()

        populateValidParams(params)
        params.id = objeto.id
        params.version = -1
        controller.update()

        assert view == "/objeto/edit"
        assert model.objetoInstance != null
        assert model.objetoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/objeto/list'

        response.reset()

        populateValidParams(params)
        def objeto = new Objeto(params)

        assert objeto.save() != null
        assert Objeto.count() == 1

        params.id = objeto.id

        controller.delete()

        assert Objeto.count() == 0
        assert Objeto.get(objeto.id) == null
        assert response.redirectedUrl == '/objeto/list'
    }
}
