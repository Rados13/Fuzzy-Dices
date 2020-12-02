package example.GUI

import example.Logic.Bot
import javax.swing.JFrame
import org.jfree.chart.ChartPanel
import javax.swing.WindowConstants
import scalafx.beans.property.IntegerProperty
import java.awt.event.WindowListener
import java.awt.event.WindowEvent
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;




class ChartFrame extends Thread{
    val scoreProperty = IntegerProperty(500)
    val repeatedProperty = IntegerProperty(2)
    val whichDiceRepeatedMostProperty = IntegerProperty(1)
    val dicesNumProperty = IntegerProperty(5)


    private def drawBotChart = {
        val fuzzyRuleSet = Bot.fis.getFuzzyRuleSet()
        fuzzyRuleSet.setVariable(Bot.param1,scoreProperty.get())
        fuzzyRuleSet.setVariable(Bot.param2,repeatedProperty.get())
        fuzzyRuleSet.setVariable(Bot.param3,whichDiceRepeatedMostProperty.get())
        fuzzyRuleSet.setVariable(Bot.param4,dicesNumProperty.get())
        fuzzyRuleSet.chart()
        fuzzyRuleSet.evaluate()
        fuzzyRuleSet.getVariable(Bot.result).defuzzify()
        fuzzyRuleSet.getVariable("wynik").chartDefuzzifier(true)
    }

    override def run(): Unit = {
        drawBotChart
    }
}