package example.Logic

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

trait Player{
    def makeMove(dices:List[Int]): Move
    def addScore(value:Int): Unit
    def getScore:Int
}

trait Move
case class Finish(dices:List[Int]) extends Move
case class RollAgain(dices:List[Int]) extends Move

object Bot{
    val fileName = "fuzzy_dices.fcl"
    val param1 = "zebrane_juz_punkty"
    val param2 = "ile_razy_wartosci_sie_powtarzaja"
    val param3 = "jaka_wartosc_sie_powtarza_najczesciej"
    val param4 = "liczba_kosci"
    val result = "wynik"
}

object ParamsForFuzzyRuleSet{
    def getOccurencesOfValue(dices:List[Int]) = {
        var occurences = Array.fill[Int](6)(0)
        dices.foldRight(occurences)((x,acc) => {
            acc(x-1)+=1
            acc
        })
    }
    val special = Array(1,5)
    def getHowManyValuesRepeat(occurences:Array[Int]): Int = occurences.filter(elem => elem>2).sum
    def getWhatRepeatMostOfTime(occurences:Array[Int]): Int = occurences.zipWithIndex.foldRight((0,0))(
        (x:(Int,Int),acc:(Int,Int)) => if (x._2>acc._2 || (x._1==4 && x._2==acc._2)) x else acc)._1+1
}

class Bot() extends Player{
    var score = 0

    def addScore(value:Int) = {score+=value}

    def getScore: Int = score

    def findMaximalMove(occurences:Array[Int]) = occurences
                                            .zipWithIndex
                                            .filter((tuple:(Int,Int)) => tuple._2>=3 || Array(1,5).contains(tuple._1+1))
                                            .map(elem => elem._1+1).toList

    def findMinimalMove(occurences:Array[Int]) = if (occurences(0) != 0) List(1)
                                                    else if(occurences(4) != 0)List(5)
                                                    else  occurences
                                                        .zipWithIndex
                                                        .filter((tuple:(Int,Int)) => tuple._2>=3)
                                                        .map(elem => elem._1+1).toList


    def makeMove(dices:List[Int]) = {
        val fis:FIS = FIS.load(Bot.fileName,false);
        val fuzzyRuleSet:FuzzyRuleSet = fis.getFuzzyRuleSet();
        val occurencesArr = ParamsForFuzzyRuleSet.getOccurencesOfValue(dices)
        val howManyRepeat = ParamsForFuzzyRuleSet.getHowManyValuesRepeat(occurencesArr)
        val whatRepeatMostOfTime = ParamsForFuzzyRuleSet.getWhatRepeatMostOfTime(occurencesArr)
        fuzzyRuleSet.setVariable(Bot.param1,score)
        fuzzyRuleSet.setVariable(Bot.param2,howManyRepeat)
        fuzzyRuleSet.setVariable(Bot.param3,whatRepeatMostOfTime)
        fuzzyRuleSet.setVariable(Bot.param4,dices.size)
        fuzzyRuleSet.evaluate()
        fuzzyRuleSet.getVariable(Bot.result).defuzzify() match {
            case x if x <= -10  => Finish(findMinimalMove(occurencesArr))
            case x if x <= 0  => Finish(findMinimalMove(occurencesArr))
            case x if x <= 10  => RollAgain(findMinimalMove(occurencesArr))
            case x  => RollAgain(findMaximalMove(occurencesArr))
        }
    }
}