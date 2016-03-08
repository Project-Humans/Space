package space.other

import com.jme3.scene.Node
import space.fundamental.PlanetSystem

//in progress...
class System(system: PlanetSystem) {

    val center: Node = new Node()
    val star: Model = new Model("sphere with texture.j3o", "Chess Board Texture.png")
    star attachTo center

    var planets: List[Planetoid] = system.planets.map(new Planetoid(_))
   // planets.foreach(_.model.attachTo(center))

    //var planets = List[Planetoid]()

    def add(planetoid: Planetoid): Unit = {
        planets = planetoid :: planets
        planetoid.model.attachTo(center)
    }

}
