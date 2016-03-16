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
import space.other.System


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

    val playerController = new ActionListener {
        override def onAction(name: String, keyPressed: Boolean, tpf: Float): Unit = {
            name match {
                case _ => clickResponse
            }
        }
    }

    def clickResponse: Unit = {
        val results: CollisionResults = new CollisionResults
        val click2d: Vector2f = inputManager.getCursorPosition
        val click3d: Vector3f = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone
        val dir: Vector3f = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal()
        val ray: Ray = new Ray(click3d, dir)
        rootNode.collideWith(ray, results)

        if (results.size > 0) {
            var spatial: Spatial = results.getClosestCollision.getGeometry
            while(spatial.getUserData("id") == null ) {
                spatial = spatial.getParent
            }
            val id = spatial.getUserData("id").asInstanceOf[Int]
            Binder.get(id) match {
                case s: PlanetSystem => {
                    val font: BitmapFont = assetManager.loadFont("Interface/Fonts/Default.fnt")
                    val text: BitmapText = new BitmapText(font)
                    text.setText(s.name)
                    text.setSize(50)
                    guiNode.attachChild(text)
                    text.setLocalTranslation(click2d.x, click2d.y, 0)

                }
                case _ => Unit
            }
        }

    }

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

        //val model = new Model("sphere with texture test.j3o", "Chess Board Texture.png")
        //model attachTo rootNode
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


    def initCameraControlKeys: Unit = {
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W))
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S))
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A))
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D))
        inputManager.addMapping("Forward", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false))
        inputManager.addMapping("Back", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true))

        inputManager.addListener(cameraController, "Up", "Down", "Right", "Left", "Forward", "Back")
    }

    def initPlayerControlKeys: Unit = {
        inputManager.addMapping("Left Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT))
        inputManager.addMapping("Right Click", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT))

        inputManager.addListener(playerController, "Left Click", "Right Click")
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
