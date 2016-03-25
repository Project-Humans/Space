package space.fundamental

import space.fundamental.parameters.Parameters

sealed trait PlanetType
case object Temperate extends PlanetType
case object Acid_Rain extends PlanetType
case object Desert extends PlanetType

sealed trait PlanetSize {
    def modifier: Float
}
case object Tiny extends PlanetSize {
    def modifier = 0.25f
}
case object Small extends PlanetSize {
    def modifier = 0.5f
}
case object Normal extends PlanetSize {
    def modifier = 1f
}
case object Big extends PlanetSize {
    def modifier = 1.25f
}
case object Huge extends PlanetSize {
    def modifier = 1.5f
}


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
