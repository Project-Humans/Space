package space.corefunc

import java.io.{IOException, FileNotFoundException, FileOutputStream, File}

import com.jme3.app.SimpleApplication
import com.jme3.collision.CollisionResults
import com.jme3.export.binary.BinaryExporter
import com.jme3.font.{BitmapFont, BitmapText}
import com.jme3.input.{MouseInput, KeyInput}
import com.jme3.input.controls._
import com.jme3.light.DirectionalLight
import com.jme3.math.{Ray, Vector2f, Vector3f}
import com.jme3.scene.Spatial
import com.jme3.scene.plugins.blender.BlenderModelLoader
import space.fundamental._
import space.user.{Observer, Controls}


object Main extends SimpleApplication {
    def main(args: Array[String]) = start

    def getWidth = settings.getWidth
    def getHeight = settings.getHeight

    override def simpleInitApp(): Unit = {

        Controls.initKeys
        Controls.activateCameraController
        Controls.activatePlayerController

        flyCam.setMoveSpeed(10)
        flyCam.setEnabled(false)


        val dl: DirectionalLight = new DirectionalLight
        dl.setDirection(new Vector3f(-0.1f, 1f, -1).normalizeLocal)

        //j3oloader("sphere with texture test")

        val planetSystem: PlanetSystem = new PlanetSystem("Sol")
        planetSystem.planets = new Planet("earth", Tiny, List[Resource](), Temperate) :: new Planet("mars", Normal, List[Resource](), Temperate) :: new Planet("jupiter", Huge, List[Resource](), Temperate) :: List[Planet]()

      //  val system: System = new System(planetSystem)

        val spatial: Spatial = ModelFactory(planetSystem)
        Binder.add(1, planetSystem)
        spatial.setUserData("id", 1)
        rootNode.attachChild(spatial)

        cam.setLocation(cam.getLocation.add(0f, 0f, 3f))
        cam.lookAt(new Vector3f(0f, 0f, 0f), new Vector3f(0, 0, 0))
        inputManager.setCursorVisible(true)

        rootNode.addLight(dl)



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

}
