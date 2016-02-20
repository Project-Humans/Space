package space.other

import com.jme3.scene.Spatial
import space.fundamental.Planet

//My addition, erase this comment after you read it


class Planetoid(val planet: Planet) {
    val model = new Model("sphere2.blend")

    def position: Pos = model.position

    def position_=(pos: Pos): Unit = {
        model.position = pos
    }
}
