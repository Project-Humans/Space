package space.user

import com.jme3.font.{BitmapText, BitmapFont}
import com.jme3.math.{Vector2f, Vector3f}
import com.jme3.scene.{Node, Spatial}
import space.corefunc.{Main, Binder}
import space.fundamental.PlanetSystem

object User {
    var selected: Option[Spatial] = None

    var selectionNode: Node = new Node
    Main.getGuiNode.attachChild(selectionNode)

    var label: BitmapText = null

    def spatialName: String = {
        if (selected.isEmpty) return ""
        val id = selected.get.getUserData("id").asInstanceOf[Int]
        Binder.get(id) match {
            case s: PlanetSystem => s.name
            case _ => ""
        }
    }

    def makeLabel: BitmapText = {
        val font: BitmapFont = Main.getAssetManager.loadFont("Interface/Fonts/Default.fnt")
        val text: BitmapText = new BitmapText(font)
        text.setText(spatialName)
        text.setSize(50)
        text
    }

    def deselect: Unit = {
        if (selectionNode.getChildren.size > 0) {
            selectionNode.detachChildAt(0)
        }
        selected = None
    }

    def select(spatial: Spatial): Unit = {
        deselect

        selected = Some(spatial)
        val id = spatial.getUserData("id").asInstanceOf[Int]

        Binder.get(id) match {
            case s: PlanetSystem => {
                label = makeLabel
                selectionNode.attachChild(label)
                relocateLabel
            }
            case _ =>
        }
    }

    def update: Unit = {
        if (selected.isDefined)
            relocateLabel
    }

    def relocateLabel: Unit = {
        val position: Vector3f = labelPosition
        label.setLocalTranslation(position.x, position.y, 0)
    }

    def labelPosition: Vector3f = {
        selected match {
            case Some(spatial) => Controls.cam.getScreenCoordinates(selected.get.getLocalTranslation.add (- 0.4f, selected.get.getLocalScale.y + 0.7f, 0) )
            case _ => Vector3f.ZERO
        }
    }

}
