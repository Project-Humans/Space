package Classes

sealed trait RaceKind
case object Humanoid extends RaceKind
case object Insectoid extends RaceKind
case object Cyborg extends RaceKind

class Race(val name: String, val kind: RaceKind ) {

}
