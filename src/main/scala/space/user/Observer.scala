package space.user

import com.jme3.font.{BitmapText, BitmapFont}
import com.jme3.math.{Vector2f, Vector3f}
import com.jme3.scene.{Geometry, Spatial, Node}
import space.corefunc.{ModelFactory, Main}
import space.fundamental.{Planet, PlanetSystem}
import space.other.Pos
import scala.collection.JavaConversions._

object Observer {

    val viewNode = new Node

    val baseShift = Pos(200 * Main.resolutionScale, Main.getHeight - 200 * Main.resolutionScale)
    val pictureSize = 100 * Main.resolutionScale
    val characteristicShift = Pos(pictureSize + 100 * Main.resolutionScale, pictureSize)
    val pictureDistance = 400 * Main.resolutionScale
    val labelSize = 35 * Main.resolutionScale

    var deatached3d = List[Spatial]()
    var deatachedGUI = List[Spatial]()

    def view(system: PlanetSystem): Unit = {
        deatachFromGuiNode
        deatachFromRootNode
        Main.getGuiNode.attachChild(viewNode)

        attachAllPlanetsDescription(system.planets)
    }

    def exit: Unit = {
        viewNode.detachAllChildren()
        Main.getGuiNode.detachChild(viewNode)

        reatachToRootNode
        reatachToGuiNode
    }

    def attachAllPlanetsDescription(planets: List[Planet]): Unit = {
        planets.foldLeft(Vector2f.ZERO.add(new Vector2f(baseShift.x, baseShift.y)))((pos, planet) => {
            attachPlanetDescription(planet, pos)
            pos.addLocal(0, -pictureDistance)
        })
    }

    def attachPlanetDescription(planet: Planet, position: Vector2f): Unit = {
        attachPlanetPicture(planet, position)
        attachPlanetName(planet, position)
        attachPlanetCharacteristics(planet, position)
    }

    def attachPlanetName(planet: Planet, position: Vector2f): Unit = {
        val text = ModelFactory.createLabel(planet.name, labelSize)
        text.setLocalTranslation(position.x - labelSize * 1.5f, position.y + 1.5f * labelSize + pictureSize * planet.size.modifier, 0)
        viewNode.attachChild(text)
    }

    def attachPlanetPicture(planet: Planet, position: Vector2f): Unit = {
        val spatial = ModelFactory(planet)
        spatial.setLocalTranslation(position.x, position.y, 0)
        spatial.scale(pictureSize * planet.size.modifier)
        viewNode.attachChild(spatial)
    }

    def attachPlanetCharacteristics(planet: Planet, position: Vector2f): Unit = {
        val text = ModelFactory.createLabel("population: " + planet.population, labelSize)
        text.setLocalTranslation(position.x + characteristicShift.x, position.y + characteristicShift.y * planet.size.modifier, 0)
        viewNode.attachChild(text)
    }

    def viewNext: Unit = {
        if(viewNode.getChildren.get(viewNode.getChildren.size - 1).getLocalTranslation.y + pictureDistance <= Main.getHeight)
          viewNode.getChildren.foreach(x => x.setLocalTranslation(x.getLocalTranslation.add(0, pictureDistance, 0)))
    }

    def viewPrevious: Unit = {
        if(viewNode.getChildren.get(0).getLocalTranslation.y - pictureDistance >= Main.getHeight - pictureDistance)
         viewNode.getChildren.foreach(x => x.setLocalTranslation(x.getLocalTranslation.add(0, -pictureDistance, 0)))
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
