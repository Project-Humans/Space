package space.fundamental.parameters

case class Parameters(industrial : Int = 0,
                      food : Int       = 0,
                      influence : Int  = 0,
                      science : Int    = 0,
                      credits : Int    = 0) {

   def +(target : Parameters): Parameters = Parameters(
       this.industrial + target.industrial,
       this.food + target.food,
       this.influence + target.food,
       this.science + target.science,
       this.credits + target.credits
   )


}
