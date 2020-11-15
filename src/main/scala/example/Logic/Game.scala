package example.Logic

import example.GUI.MapPainter
import scala.util.Random




class Game(mapPainter: MapPainter){
    var player1:Player = new Bot()
    var player2:Player = new Bot()
    var turn:Int = 0
    var temporaryScore = 0

    def nextTurn(isSafed:Boolean = false) = {
        if(isSafed){
            var activePlayer = if(turn%2==1)player1 else player2
            activePlayer.addScore(temporaryScore)
        }
        temporaryScore = 0
        turn+=1
    }

    def startGame() = {
        while (player1.getScore<20000 && player2.getScore<20000){
            var activePlayer = if(turn%2==1)player1 else player2
            var diceNum:Option[Int] = Some(6)
            while(diceNum.isDefined){
                val dices = Seq.fill(diceNum.get)(Random.nextInt(5)+1).toList
                mapPainter.startAnimation(2)
                mapPainter.drawBoard(dices)
                if(!isPossibleMove(dices)) diceNum = None
                (diceNum,activePlayer.makeMove(dices)) match {
                    case (None,_) => 
                        nextTurn()
                        diceNum = None
                    case (Some(value),Finish(dices)) => 
                        calculateScore(dices)
                        nextTurn(true)
                        diceNum = None
                    case (Some(value),RollAgain(dices)) => 
                        calculateScore(dices)
                        diceNum = Some(if (value-dices.size==0) 6 else value-dices.size)
                }    
            }
            
        }
    }

    def isPossibleMove(dices:List[Int]) = dices.exists(elem => elem==1 || elem==5) || 
                                                dices.foldRight(Array.fill(6)(0))((x:Int,acc:Array[Int]) => {acc(x-1)+=1;acc})
                                                .exists(elem => elem>=3)
    

    def calculateScore(takenDices:List[Int]) = {

    }




}