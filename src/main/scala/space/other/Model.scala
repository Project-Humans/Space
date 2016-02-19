package space.other

import space.corefunc.Main

//Found a way to access inaccessible, delete comment after read :)

class Model(val assetName: String) {
    val spatial = Main.getAssetManager.loadModel(assetName)
    Main.getRootNode.attachChild(spatial)

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
    def scalse_=(pos: Pos) = {
        spatial.setLocalTranslation(pos.x, pos.y, 0)
    }

}
