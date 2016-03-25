package space.user

import com.jme3.font.{BitmapText, BitmapFont}
import com.jme3.math.{Vector2f, Vector3f}
import com.jme3.scene.{Geometry, Spatial, Node}
import space.corefunc.{ModelFactory, Main}
import space.fundamental.{Planet, PlanetSystem}
import scala.collection.JavaConversions._

object Observer {

    val viewNode = new Node

    var deatached3d = List[Spatial]()
    var deatachedGUI = List[Spatial]()

    def view(system: PlanetSystem): Unit = {
        deatachFromGuiNode
        deatachFromRootNode
        Main.getGuiNode.attachChild(viewNode)
        viewNode.setLocalTranslation(0, 0, 0)

        val planets = system.planets
        attachAllPlanetsDescription(planets)
    }

    def exit: Unit = {
        viewNode.detachAllChildren()
        Main.getGuiNode.detachChild(viewNode)
        reatachToRootNode
        reatachToGuiNode
    }

    def attachAllPlanetsDescription(planets: List[Planet]): Unit = {
        planets.foldLeft(Vector2f.ZERO.add(new Vector2f(200, Main.getHeight - 200)))((pos, planet) => {
            println(pos)
            attachPlanetDescription(planet, pos)
            pos.addLocal(0, -400)
        })
    }

    def attachPlanetDescription(planet: Planet, position: Vector2f): Unit = {
        attachPlanetPicture(planet, position)
        attachPlanetName(planet, position)
        attachPlanetCharacteristics(planet, position)
    }

    def attachPlanetName(planet: Planet, position: Vector2f): Unit = {
        val font: BitmapFont = Main.getAssetManager.loadFont("Interface/Fonts/Default.fnt")
        val text: BitmapText = new BitmapText(font)
        text.setText(planet.name)
        text.setSize(35)
        text.setLocalTranslation(position.x - 50, position.y + 50 + 100 * planet.size.modifier, 0)
        viewNode.attachChild(text)
    }

    def attachPlanetPicture(planet: Planet, position: Vector2f): Unit = {
        val spatial = ModelFactory(planet)
        spatial.setLocalTranslation(position.x, position.y, 0)
        spatial.scale(100 * planet.size.modifier)
        viewNode.attachChild(spatial)
    }

    def attachPlanetCharacteristics(planet: Planet, position: Vector2f): Unit = {
        val font: BitmapFont = Main.getAssetManager.loadFont("Interface/Fonts/Default.fnt")
        val text: BitmapText = new BitmapText(font)
        text.setText("population: " + planet.population)
        text.setSize(35)
        text.setLocalTranslation(position.x + 200, position.y + 100, 0)
        viewNode.attachChild(text)
    }

    def viewNext: Unit = {
        if(viewNode.getChildren.get(viewNode.getChildren.size - 1).getLocalTranslation.y + 400 < Main.getHeight)
          viewNode.getChildren.foreach(x => x.setLocalTranslation(x.getLocalTranslation.add(0, 400, 0)))
    }

    def viewPrevious: Unit = {
        if(viewNode.getChildren.get(0).getLocalTranslation.y - 400 > Main.getHeight - 400)
         viewNode.getChildren.foreach(x => x.setLocalTranslation(x.getLocalTranslation.add(0, -400, 0)))
    }

    def deatachFromRootNode: Unit = {
        deatached3d = Main.getRootNode.getChildren.toList
        Main.getRootNode.detachAllChildren()
    }

    def reatachToRootNode: Unit = deatached3d.foreach(Main.getRootNode.attachChild)

    def deatachFromGuiNode: Unit = {
        deatachedGUI = Main.getGuiNode.getChildren.toList
        Main.getGuiNode.detachAllChildren()
    }

    def reatachToGuiNode: Unit = deatachedGUI.foreach(Main.getGuiNode.attachChild)
}
