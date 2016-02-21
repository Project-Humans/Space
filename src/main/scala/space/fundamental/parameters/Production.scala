package space.fundamental.parameters

class Production( val industrial : Int = 0,
                  val food : Int       = 0,
                  val influence : Int  = 0,
                  val science : Int    = 0,
                  val credits : Int    = 0) {

  def + (target : Production): Production ={
    new Production(
      this.industrial + target.industrial,
      this.food + target.food,
      this.influence + target.food,
      this.science + target.science,
      this.credits + target.credits
    )
  }

}
