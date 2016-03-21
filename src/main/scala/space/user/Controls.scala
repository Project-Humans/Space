package space.user

import com.jme3.collision.CollisionResults
import com.jme3.font.{BitmapFont, BitmapText}
import com.jme3.input.controls.{ActionListener, AnalogListener}
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
                case _ =>
            }
        }
    }

    val cameraController = new AnalogListener {
        override def onAnalog(name: String, value: Float, tpf: Float): Unit = {
            cam setLocation { cam.getLocation.add { translation(name, value) } }
            User.update
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

    def clickResponse: Unit = {

        val results: CollisionResults = pointedObject

        if (results.size > 0) {
            val spatial: Spatial = getParentWithUserData("id", results.getClosestCollision.getGeometry)
            User.select(spatial)
        }
        else
            User.deselect

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

}
