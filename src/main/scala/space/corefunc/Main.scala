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
import space.fundamental.{Normal, Resource, Planet, PlanetSystem}
import space.user.Controls


object Main extends SimpleApplication {
    def main(args: Array[String]) = start

    override def simpleInitApp(): Unit = {

        initCameraControlKeys
        initPlayerControlKeys

        flyCam.setMoveSpeed(10)
        flyCam.setEnabled(false)


        val dl: DirectionalLight = new DirectionalLight
        dl.setDirection(new Vector3f(-0.1f, 1f, -1).normalizeLocal)

        //j3oloader("sphere with texture test")

        val planetSystem: PlanetSystem = new PlanetSystem("Sol")
        planetSystem.planets = new Planet("earth", 0, List[Resource](), Normal) :: List[Planet]()
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

    def initCameraControlKeys: Unit = {
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W))
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S))
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A))
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D))
        inputManager.addMapping("Forward", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false))
        inputManager.addMapping("Back", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true))

        inputManager.addListener(Controls.cameraController, "Up", "Down", "Right", "Left", "Forward", "Back")
    }

    def initPlayerControlKeys: Unit = {
        inputManager.addMapping("Left Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT))
        inputManager.addMapping("Right Click", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT))

        inputManager.addListener(Controls.playerController, "Left Click", "Right Click")
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
