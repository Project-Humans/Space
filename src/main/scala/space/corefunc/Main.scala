package space.corefunc

import java.io.{IOException, FileNotFoundException, FileOutputStream, File}
import javafx.scene.input.MouseButton

import com.jme3.app.SimpleApplication
import com.jme3.export.binary.BinaryExporter
import com.jme3.input.{MouseInput, KeyInput}
import com.jme3.input.controls._
import com.jme3.light.DirectionalLight
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Spatial
import com.jme3.scene.plugins.blender.BlenderModelLoader
import space.other.Model


object Main extends SimpleApplication {
    def main(args: Array[String]) = start

    val cameraController = new AnalogListener {
        override def onAnalog(name: String, value: Float, tpf: Float): Unit = {
            cam setLocation { cam.getLocation.add { translation(name, value) } }

        }

        def translation(name: String, value: Float): Vector3f = {
            name match {
                case "Up" => Vector3f.UNIT_Y.mult(value * flyCam.getMoveSpeed)
                case "Down" => Vector3f.UNIT_Y.mult(-value * flyCam.getMoveSpeed)
                case "Right" => Vector3f.UNIT_X.mult(value * flyCam.getMoveSpeed)
                case "Left" => Vector3f.UNIT_X.mult(-value * flyCam.getMoveSpeed)
                case "Forward" => Vector3f.UNIT_Z.mult(-value)
                case "Back" => Vector3f.UNIT_Z.mult(value)
                case _ => Vector3f.ZERO
            }
        }
    }



    def j3oloader(name: String): Unit = {
        assetManager.registerLoader(classOf[BlenderModelLoader], "blend")


        val model: Spatial = assetManager.loadModel(name + ".blend")
        val exp = new BinaryExporter();
        val outName = new File(name + ".j3o")

        val out = new FileOutputStream(outName)
        try {
            outName.createNewFile();
            exp.save(model, out);
        } catch {
            case e: FileNotFoundException => e.printStackTrace()
            case e: IOException => e.printStackTrace();
        }
    }

    override def simpleInitApp(): Unit = {

        flyCam.setMoveSpeed(10)
        flyCam.setEnabled(false)

        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W))
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S))
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A))
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D))
        inputManager.addMapping("Forward", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false))
        inputManager.addMapping("Back", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true))

        inputManager.addListener(cameraController, "Up", "Down", "Right", "Left", "Forward", "Back")

        val dl: DirectionalLight = new DirectionalLight
        dl.setDirection(new Vector3f(-0.1f, 1f, -1).normalizeLocal)

        //j3oloader("sphere with texture test")

        val model = new Model("sphere with texture test.j3o", "Chess Board Texture.png")
        model attachTo rootNode
        //model.setMaterial(new Material(assetManager, "sphere with texture test.png"))

        cam.setLocation(cam.getLocation.add(0f, 0f, 3f))
        cam.lookAt(new Vector3f(0f, 0f, 0f), new Vector3f(0, 0, 0))
        inputManager.setCursorVisible(true)



        //val planet: Model = new Model("sphere with texture test.blend")
        //planet.attachTo(rootNode)
        //new Model("sphere2.blend")
        //rootNode.attachChild(planet)
        rootNode.addLight(dl)

    }
}
