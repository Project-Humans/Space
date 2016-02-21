import org.scalatest.FlatSpec
import space.fundamental.{Normal, Resource, Planet, PlanetSystem}

class PlanetSystemTest extends FlatSpec {
    val system = new PlanetSystem("solar")
    val emptyPlanet: Planet = new Planet("empty", 0, List[Resource](), Normal)
    emptyPlanet.population = 10

    system.planets = emptyPlanet :: emptyPlanet :: emptyPlanet :: List[Planet]()

    "System population" should "be 30" in {
        assert(system.population == 30)
    }


}
