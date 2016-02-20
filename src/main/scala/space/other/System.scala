package space.other
//in progress...
class System {

    val star: Model = new Model("sphere2.blend")

    var planets = List[Planetoid]()

    def add(planetoid: Planetoid): Unit = {
        planets = planetoid :: planets
    }

}
