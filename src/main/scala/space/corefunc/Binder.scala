package space.corefunc

object Binder {
    var bindings: Map[Int, Object] = Map()

    def add(id: Int, obj: Object): Unit = { bindings += (id -> obj) }

    def get(id: Int): Object = bindings(id)


}
