package space.other

import com.jme3.scene.Node
import space.corefunc.Main


class Model(val assetName: String) {
    val spatial = Main.getAssetManager.loadModel(assetName)

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

    def rotate_=(pos: Pos): Unit ={
        spatial.rotate(pos.x, pos.y, 0)
    }

}
