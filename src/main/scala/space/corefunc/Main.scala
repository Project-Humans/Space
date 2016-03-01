package space.corefunc

import com.jme3.app.SimpleApplication
import com.jme3.light.DirectionalLight
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Spatial
import space.other.{Model, Pos}


object Main extends SimpleApplication {
  def main(args: Array[String]) = start


    var planet : Model = _

     override def simpleInitApp(): Unit = {

         flyCam.setMoveSpeed(10)

         val dl: DirectionalLight = new DirectionalLight
         dl.setDirection(new Vector3f(-0.1f, 1f, -1).normalizeLocal)

            planet = new Model("sphere2.blend")

         planet.attachTo(rootNode)

         //new Model("sphere2.blend")
        // rootNode.attachChild(planet)
         rootNode.addLight(dl)


    }

    override def simpleUpdate(tpf : Float) : Unit = {
        // make the player rotate:
        val rotatePos : Pos = new Pos(0, tpf)
        planet.rotate_=(rotatePos)
    }

}
