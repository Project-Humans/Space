package space.fundamental

import space.fundamental.parameters.Production

class PlanetSystem (val name : String) {
  var planets = List[Planet]()
  //var owner : Player = @no owner@

  def population () : Int ={
    var sum : Int = 0
    for (currentPlanet <- planets){
      sum += currentPlanet.population
    }
    sum
  }

  var parameters : Production = {
    var summary : Production = new Production()
    for(currentPlanet <- planets){
      summary += currentPlanet.parameters
    }
    summary
  }




}
