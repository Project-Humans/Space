package space.fundamental

import space.fundamental.parameters.Production

sealed trait PlanetType
case object Normal extends PlanetType
case object Acid_Rain extends PlanetType
case object Desert extends PlanetType

//Changed name from Type -> PlanetType
//Shrank Planet constructor, later will think about better shrinking ^_^
// colProc - colonization process
class Planet (var name: String, val size: Int, val resources : List[Resource], val planetType: PlanetType) {

    var colProc: Boolean = false
    var population: Int = 0
    var buildings = List[Building]()

    var parameters : Production = {
        var summary : Production = new Production()
        for (currentBuilding <- buildings){
            summary += currentBuilding.parameters
        }
        summary
    }

    def build (newBuilding : Building): Unit ={
        buildings = newBuilding :: buildings
    }
}
