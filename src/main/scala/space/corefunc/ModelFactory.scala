package space.corefunc

import com.jme3.font.{BitmapText, BitmapFont}
import com.jme3.material.Material
import com.jme3.scene.shape.Sphere
import com.jme3.scene.{Geometry, Spatial}
import space.fundamental.{Ship, PlanetSystem}

object ModelFactory {

    def apply(obj: Object): Spatial = {
        obj match {
            case sh: Ship => createSpatial("starship.j3o", "SHIPTEXTURE.png")
            case _ => createSpatial("sphere with texture test.j3o", "Chess Board Texture.png")

        }
    }

    def createSpatial(modelName: String, textureName: String): Spatial = {
        val spatial: Spatial = Main.getAssetManager.loadModel(modelName)
        val material = new Material(Main.getAssetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        material.setTexture("ColorMap", Main.getAssetManager.loadTexture(textureName))
        spatial.setMaterial(material)
        spatial
    }

    def createLabel(text: String, size: Float = 0): BitmapText = {
        val font: BitmapFont = Main.getAssetManager.loadFont("Interface/Fonts/Default.fnt")
        val label: BitmapText = new BitmapText(font)
        label.setText(text)
        label.setSize(size)
        label
    }


}
