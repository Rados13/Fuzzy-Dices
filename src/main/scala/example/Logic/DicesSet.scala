package example.Logic

object DicesSet{
    def getOccurencesOfValue(dices:List[Int]):Map[Int,Int] = {
        val occurences = collection.mutable.Map[Int,Int]()
        for(elem <- dices) occurences.updateWith(elem)({
            case Some(count) => Some(count+1)
            case None => Some(1)
        })
        occurences.toMap
    }
    val special = Array(1,5)
    def getHowManyValuesRepeat(occurences:Map[Int,Int]): Int = occurences.filter(elem => elem._2>2).map(elem => elem._2).sum
    def getWhatRepeatMostOfTime(occurences:Map[Int,Int]): Int = occurences.foldRight((0,0))(
        (x:(Int,Int),acc:(Int,Int)) => if (x._2>acc._2 || (x._1==5 && x._2==acc._2)) x else acc)._1
}