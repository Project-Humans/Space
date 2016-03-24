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

    def sizeKoef: Float = {
        if (selected.isEmpty) return 0
        val result = selected.get.getLocalScale.x / Math.abs(selected.get.getWorldTranslation.z - Controls.cam.getLocation.z)
        result
    }

    def resolutionKoef: Float = Main.getWidth / 3840f

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
                resizeLabel
            }
            case _ =>
        }
    }

    def update: Unit = {
        if (selected.isEmpty) return

        relocateLabel
        resizeLabel

    }

    def relocateLabel: Unit = {
        val position: Vector3f = labelPosition
        label.setLocalTranslation(position.x, position.y, 0)
    }

    def resizeLabel: Unit = {
        label.setSize(sizeKoef * 800 * resolutionKoef)
    }

    def labelPosition: Vector3f = {
        selected match {
            case Some(spatial) => Controls.cam.getScreenCoordinates(selected.get.getLocalTranslation.add (-0.3f * selected.get.getLocalScale.x, selected.get.getLocalScale.y * 1.5f, 0) )
            case _ => Vector3f.ZERO
        }
    }

}
