package space.other

import com.jme3.scene.Spatial
import space.fundamental.{Desert, Normal, Planet}



class Planetoid(val planet: Planet) {
    val model = modelize(planet)

    def position: Pos = model.position

    def position_=(pos: Pos): Unit = {
        model.position = pos
    }

    def modelize(planet: Planet): Model = {
        planet.planetType match {
            case _ => new Model("sphere2.blend")
        }
    }
}
