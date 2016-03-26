package space.user

import com.jme3.font.{BitmapText, BitmapFont}
import com.jme3.math.{Vector2f, Vector3f}
import com.jme3.scene.{Node, Spatial}
import space.corefunc.{ModelFactory, Main, Binder}
import space.fundamental.PlanetSystem

object Selector {

    val labelSize = 800
    val labelShift = (-0.3f, 1.5f)


    var selected: Option[Spatial] = None

    var selectionNode: Node = new Node
    Main.getGuiNode.attachChild(selectionNode)

    var label: BitmapText = null

    def cameraDistance: Float = {
        if (selected.isEmpty) return 0
        val spatial: Spatial = selected.get
        spatial.getLocalScale.x / Math.abs(spatial.getWorldTranslation.z - Controls.cam.getLocation.z)
    }

    def spatialName: String = {
        if (selected.isEmpty) return ""
        val id = selected.get.getUserData("id").asInstanceOf[Int]
        Binder.get(id) match {
            case s: PlanetSystem => s.name
            case _ => ""
        }
    }

    def deselect: Unit = {
        if (selectionNode.getChildren.size > 0) {
            selectionNode.detachChild(label)
        }
        selected = None
    }

    def select(spatial: Spatial): Unit = {
        deselect

        selected = Some(spatial)

        val id = spatial.getUserData("id").asInstanceOf[Int]
        Binder.get(id) match {
            case s: PlanetSystem => {
                label = ModelFactory.createLabel(spatialName)
                selectionNode.attachChild(label)
                relocateLabel
                resizeLabel
            }
            case _ => selected = None
        }

    }

    def refresh: Unit = {
        if (selected.isEmpty) return

        relocateLabel
        resizeLabel

    }

    def relocateLabel: Unit = {
        val position: Vector3f = labelPosition
        label.setLocalTranslation(position.x, position.y, 0)
    }

    def resizeLabel: Unit = {
        label.setSize(cameraDistance * Main.resolutionScale * labelSize)
    }

    def labelPosition: Vector3f = {
        selected.map { spatial =>
            val scale: Vector3f = spatial.getLocalScale
            Controls.cam.getScreenCoordinates(spatial.getLocalTranslation.add(scale.x * labelShift._1, scale.y * labelShift._2, 0) )
        } getOrElse Vector3f.ZERO

    }

}
