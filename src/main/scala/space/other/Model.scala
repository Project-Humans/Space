package space.other

import com.jme3.material.Material
import com.jme3.scene.{Spatial, Node}
import space.corefunc.Main
import space.corefunc.Main._

//Found a way to access inaccessible, delete comment after read :)

class Model(val model: String, val texture: String) {
    val spatial = Main.getAssetManager.loadModel(model)
    val material = new Material(Main.getAssetManager, "Common/MatDefs/Misc/Unshaded.j3md")
    material.setTexture("ColorMap", Main.getAssetManager.loadTexture("Chess Board Texture.png"))
    spatial.setMaterial(material)

    def attachTo(node: Node): Unit = {
        node.attachChild(spatial)
    }

    def position: Pos = {
        val vec = spatial.getLocalTranslation
        Pos(vec.x, vec.y)
    }
    def position_=(pos: Pos) = {
        spatial.setLocalTranslation(pos.x, pos.y, 0)
    }

    def scale: Pos = {
        val vec = spatial.getLocalScale
        Pos(vec.x, vec.y)
    }
    def scale_=(pos: Pos): Unit = {
        spatial.setLocalTranslation(pos.x, pos.y, 0)
    }

}
