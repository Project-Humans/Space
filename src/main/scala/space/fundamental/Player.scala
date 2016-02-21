package space.fundamental
// not finished
class Player (val playerName : String, val playerRace : Race) {
  var ownedSystems = List[PlanetSystem]()
  var fleets = List[Fleet]()
  var technology = List[Technology]()
}
