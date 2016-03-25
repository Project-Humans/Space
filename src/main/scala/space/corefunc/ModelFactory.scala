package space.corefunc

import com.jme3.material.Material
import com.jme3.scene.shape.Sphere
import com.jme3.scene.{Geometry, Spatial}
import space.fundamental.PlanetSystem

object ModelFactory {

    def apply(obj: Object): Spatial = {
        obj match {
            case _ => {
                val spatial: Spatial = Main.getAssetManager.loadModel("sphere with texture test.j3o")
                val material = new Material(Main.getAssetManager, "Common/MatDefs/Misc/Unshaded.j3md")
                material.setTexture("ColorMap", Main.getAssetManager.loadTexture("Chess Board Texture.png"))
                spatial.setMaterial(material)
                spatial
            }
        }
    }


}
