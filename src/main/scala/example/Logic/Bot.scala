package example.Logic

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;
import example.Logic.DicesSet

object Bot{
    val fileName = "fuzzy_dices.fcl"
    val param1 = "gathered_points"
    val param2 = "how_many_repeated_values"
    val param3 = "what_repeated_most"
    val param4 = "dices_number"
    val result = "score"
    val fis:FIS = FIS.load(Bot.fileName,false);
}

class Bot() extends Player{
    var score = 0

    def addScore(value:Int) = {score+=value}

    def getScore: Int = score

    def findMaximalMove(occurences:Map[Int,Int]):List[Int] = occurences
                                            .filter((tuple:(Int,Int)) => tuple._2>=3 || Array(1,5).contains(tuple._1))
                                            .map(elem => List.fill(elem._2)(elem._1))
                                            .foldRight(List[Int]())((x,acc) => acc++x)

    def findMinimalMove(occurences:Map[Int,Int]) = if (occurences.isDefinedAt(1)) List(1)
                                                    else if(occurences.isDefinedAt(5))List(5)
                                                    else  occurences
                                                        .filter((tuple:(Int,Int)) => tuple._2>=3)
                                                        .map(elem => List.fill(elem._2)(elem._1))
                                                        .foldRight(List[Int]())((x,acc) => acc++x)


    def makeMove(dices:List[Int]) = {
        val occurencesArr = DicesSet.getOccurencesOfValue(dices)
        val howManyRepeat = DicesSet.getHowManyValuesRepeat(occurencesArr)
        val whatRepeatMostOfTime = DicesSet.getWhatRepeatMostOfTime(occurencesArr)
        val fuzzyRuleSet:FuzzyRuleSet = Bot.fis.getFuzzyRuleSet();
        fuzzyRuleSet.setVariable(Bot.param1,score)
        fuzzyRuleSet.setVariable(Bot.param2,howManyRepeat)
        fuzzyRuleSet.setVariable(Bot.param3,whatRepeatMostOfTime)
        fuzzyRuleSet.setVariable(Bot.param4,dices.size)
        fuzzyRuleSet.evaluate()
        val result = fuzzyRuleSet.getVariable(Bot.result).defuzzify()
        fuzzyRuleSet.getVariable(Bot.result).defuzzify() match {
            case x if x <= -1  => Finish(findMaximalMove(occurencesArr))
            case x if x <= 1  => RollAgain(findMinimalMove(occurencesArr))
            case x  => RollAgain(findMaximalMove(occurencesArr))
        }
    }
}