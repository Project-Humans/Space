package space.fundamental

import space.fundamental.parameters.Parameters

//Changed your realization for better suited in scala ^_^

class PlanetSystem (val name : String) {
     var planets = List[Planet]()
  //var owner : Player = @no owner@

     def population () : Int = planets.foldLeft(0)((sum, planet) => sum + planet.population)

     var parameters : Parameters = planets.foldLeft(Parameters())((summary, planet) => summary + planet.parameters)




}
