import org.scalatest.FlatSpec
import space.fundamental.{Temperate, Normal, Resource, Planet, PlanetSystem}

class PlanetSystemTest extends FlatSpec {
    val system = new PlanetSystem("solar")
    val emptyPlanet: Planet = new Planet("empty", Normal , List[Resource](), Temperate)
    emptyPlanet.population = 10

    system.planets = emptyPlanet :: emptyPlanet :: emptyPlanet :: List[Planet]()

    "PlanetSystem population" should "be 30" in {
        assert(system.population == 30)
    }


}
