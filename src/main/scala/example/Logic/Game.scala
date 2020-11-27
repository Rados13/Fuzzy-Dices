package example.Logic

import example.GUI.MapPainter
import scala.util.Random
import java.util.concurrent.CountDownLatch

object GameStatus extends Enumeration{
    type GameStatus = Value
    val Stoped, Run, Ended = Value
}

object Game{
    def startSimulation(mapPainter:MapPainter)= new Game(mapPainter,new Bot(),new Bot())

    def startGame(mapPainter:MapPainter):(Game,HumanPlayer) = {
        val humanPlayer = new HumanPlayer()
        (new Game(mapPainter,humanPlayer,new Bot()),humanPlayer)
    }

}

class Game(mapPainter: MapPainter,player1:Player,player2:Player){
    var turn:Int = 1
    var temporaryScore = 0
    var status = GameStatus.Run

    def nextTurn(isSafed:Boolean = false) = {
        if(isSafed){
            var activePlayer = if(turn%2==1)player1 else player2
            activePlayer.addScore(temporaryScore)
        }
        temporaryScore = 0
        turn+=1
    }

    def updateData = {
        mapPainter.isFirstPlayerTurn = turn%2==1
        mapPainter.scores = Array(player1.getScore,player2.getScore,temporaryScore)
    }

    def startGame() = {
        while (player1.getScore<20000 && player2.getScore<20000 && status!=GameStatus.Ended){
            var activePlayer = if(turn%2==1)player1 else player2

            var diceNum:Option[Int] = Some(6)
            while(diceNum.isDefined){
                // println(s"Turn $turn:  $status")
                status match {
                    case GameStatus.Run =>
                        val dices = Seq.fill(diceNum.get)(Random.nextInt(5)+1).toList
                        // println(s"Dices randomed ${dices.mkString(" ")}")
                        val latch = new CountDownLatch(1)
                        updateData
                        mapPainter.startAnimation(2,dices.length,latch)
                        latch.await()
                        mapPainter.drawBoard(dices)
                        Thread.sleep(2000)
                        if(!isPossibleMove(dices)) diceNum = None
                        (diceNum,activePlayer.makeMove(dices)) match {
                            case (_,null) =>
                                diceNum = None
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
                    case GameStatus.Ended => diceNum = None
                    case _ => 
                }
            }
        }
        println("End of simulation")
    }

    def isPossibleMove(dices:List[Int]) = dices.exists(elem => elem==1 || elem==5) || 
                                                dices.foldRight(Array.fill(6)(0))((x:Int,acc:Array[Int]) => {acc(x-1)+=1;acc})
                                                .exists(elem => elem>=3)
    

    def calculateScore(takenDices:List[Int]) = {
        println(s"Taken ${takenDices.mkString(" ")}")
        val occurences = collection.mutable.Map[Int,Int]()
        for(elem <- takenDices) occurences.updateWith(elem)({
            case Some(count) => Some(count+1)
            case None => Some(1)
        })
        for((k,v) <- occurences){
            val diceValue = if(k==1) 10 else k
            if(v>=3)temporaryScore+=(v-2)*diceValue*100
            else if(Array(1,5).contains(k))temporaryScore+=v*diceValue*10
        }
    }




}