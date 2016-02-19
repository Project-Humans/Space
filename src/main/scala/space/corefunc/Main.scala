package space.corefunc

import com.jme3.app.SimpleApplication
import com.jme3.light.DirectionalLight
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Spatial
import space.other.Model


object Main extends SimpleApplication {
  def main(args: Array[String]) = start


     override def simpleInitApp(): Unit = {

         flyCam.setMoveSpeed(10)

         val dl: DirectionalLight = new DirectionalLight
         dl.setDirection(new Vector3f(-0.1f, 1f, -1).normalizeLocal)

         val planet: Spatial = assetManager.loadModel("sphere2.blend")
         planet.setMaterial(new Material(assetManager, "BbLjlGg.png"))

         //new Model("sphere2.blend")
         rootNode.attachChild(planet)
         rootNode.addLight(dl)
    }
}
