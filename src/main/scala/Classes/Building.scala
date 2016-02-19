package Classes

sealed trait BuildingType
case object Entertainment extends BuildingType
case object Military extends BuildingType
case object Production extends BuildingType
case object Project extends BuildingType
case object Harvest extends BuildingType

class Building(val id: Int, val name: String, val kind: BuildingType ) {

}
