package Classes

sealed trait Type
case object Normal extends Type
case object Acid_Rain extends Type
case object Desert extends Type

// colProc - colonization process
class Planet (var name: String, var colProc: Boolean, val size: Int, val resources : List[Resource], var population: Int,
                    val planetType: Type) {

  var buildings = List[Building]

}
