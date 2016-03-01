package space.fundamental

import space.fundamental.parameters.Parameters

sealed trait PlanetType
case object Temperate extends PlanetType
case object Acid_Rain extends PlanetType
case object Desert extends PlanetType

sealed trait PlanetSize
case object Tiny extends PlanetSize
case object Small extends PlanetSize
case object Normal extends PlanetSize
case object Big extends PlanetSize
case object Huge extends PlanetSize


// colProc - colonization process
class Planet (var name: String, val size: PlanetSize = Normal, val resources : List[Resource], val planetType: PlanetType) {

    var colProc: Boolean = false
    var population: Int = 0
    var buildings = List[Building]()

    var parameters : Parameters = {
        buildings.foldLeft(Parameters())((sum, building) => sum + building.parameters)
    }

    def build (newBuilding : Building): Unit ={
        buildings = newBuilding :: buildings
    }
}
