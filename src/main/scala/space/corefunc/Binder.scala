package space.corefunc

object Binder {
    var bindings: Map[Int, Object] = Map()

    def addBinding(id: Int, obj: Object): Unit = {
        bindings += (id -> obj)
    }

}
