package space.user

import com.jme3.collision.CollisionResults
import com.jme3.font.{BitmapFont, BitmapText}
import com.jme3.input.{MouseInput, KeyInput}
import com.jme3.input.controls._
import com.jme3.math.{Ray, Vector2f, Vector3f}
import com.jme3.scene.Spatial
import space.corefunc.{Binder, Main}
import space.fundamental.PlanetSystem

object Controls {

    val cam = Main.getCamera
    val flyCam = Main.getFlyByCamera
    val inputManager = Main.getInputManager

    val playerController = new ActionListener {
        override def onAction(name: String, keyPressed: Boolean, tpf: Float): Unit = {
            if (!keyPressed) return
            name match {
                case "Left Click" => clickResponse
                case "Use" =>
                    for(spatial <- Selector.selected) {
                        Binder.get(spatial.getUserData("id")) match {
                            case s: PlanetSystem =>
                                deactivateCameraController
                                deactivatePlayerController
                                activateObserverController
                                Observer.view(s)
                            case _ =>
                        }
                    }

                case _ =>
            }
        }
    }

    val cameraController = new AnalogListener {
        override def onAnalog(name: String, value: Float, tpf: Float): Unit = {
            cam setLocation { cam.getLocation.add { translation(name, value) } }
            Selector.refresh
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

    val observerController = new ActionListener {
        override def onAction(name: String, keyPressed: Boolean, tpf: Float): Unit = {
            if (!keyPressed) return
            name match {
                case "Up" => Observer.viewPrevious
                case "Down" => Observer.viewNext
                case "Use" => {
                    Observer.exit
                    deactivateObserverController
                    activateCameraController
                    activatePlayerController
                }
                case _ =>
            }
        }
    }

    def clickResponse: Unit = {

        val results: CollisionResults = pointedObject

        if (results.size > 0) {
            val spatial: Spatial = getParentWithUserData("id", results.getClosestCollision.getGeometry)
            Selector.select(spatial)
        }
        else
            Selector.deselect

    }

    def getParentWithUserData(dataName: String, child: Spatial): Spatial = {
        var spatial: Spatial = child
        while (spatial.getUserData(dataName) == null) {
            spatial = spatial.getParent
        }
        spatial
    }

    def pointedObject: CollisionResults = {
        val click2d: Vector2f = inputManager.getCursorPosition
        val click3d: Vector3f = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone
        val dir: Vector3f = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal()
        val ray: Ray = new Ray(click3d, dir)

        val results: CollisionResults = new CollisionResults
        Main.getRootNode.collideWith(ray, results)
        results
    }

    def activateCameraController: Unit = inputManager.addListener(cameraController, "Up", "Down", "Right", "Left", "Forward", "Back")
    def deactivateCameraController: Unit = inputManager.removeListener(cameraController)

    def activateObserverController: Unit = inputManager.addListener(observerController, "Up", "Down", "Use")
    def deactivateObserverController: Unit = inputManager.removeListener(observerController)

    def activatePlayerController: Unit = inputManager.addListener(playerController, "Left Click", "Right Click", "Use")
    def deactivatePlayerController: Unit = inputManager.removeListener(playerController)

    def initKeys: Unit = {
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W))
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S))
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A))
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D))
        inputManager.addMapping("Forward", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false))
        inputManager.addMapping("Back", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true))

        inputManager.addMapping("Left Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT))
        inputManager.addMapping("Right Click", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT))

      //  inputManager.addMapping("Exit", new KeyTrigger(KeyInput.KEY_ESCAPE))
        inputManager.addMapping("Use", new KeyTrigger(KeyInput.KEY_E))
    }

}
