package example.Logic


trait Player{
    def makeMove(dices:List[Int]): Move
    def addScore(value:Int): Unit
    def getScore:Int
}

trait Move
case class Finish(dices:List[Int]) extends Move
case class RollAgain(dices:List[Int]) extends Move

class HumanPlayer() extends Player{

    var score = 0

    var moveDices:Move = null
    var isSelected = false

    def setMove(move:Move) = moveDices = move

    def checkIfPossibleToTake(selectedDices:List[Int]):Boolean = {
        if (selectedDices.length==0)false
        else{
            var result = true
            for ((k,v) <- DicesSet.getOccurencesOfValue(selectedDices)){
                if(v<3 && !Array(1,5).contains(k))result = false
            }
            result
        }
    }

    def makeMove(dices: List[Int]): Move = {
        isSelected = false
        while(!isSelected){
            Thread.sleep(200)
        }
        moveDices
    }

    def addScore(value: Int): Unit = score += value

    def getScore: Int = score

}