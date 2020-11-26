package example

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color.Green
import scalafx.scene.canvas.{Canvas,GraphicsContext}
import example.GUI.MainPane
import example.GUI.GamePane
import example.Logic.Game
import javafx.concurrent.Task

// https://www.gry-online.pl/S024.asp?ID=1859&PART=122

object Hello extends JFXApp {
  // println(greeting)

  val widthValue: Double = 1280
  val heightValue: Double = 720
  var gameThread: Thread = null

  var gameCanvas:Canvas = new Canvas(){
    layoutY=0
    layoutX=0
    height=720
    width=1280
  }

  var gc:GraphicsContext = gameCanvas.graphicsContext2D

  val mainScene: Scene = new Scene{
    fill = Green
    root = MainPane(startSimulation,startGame)
  }

  def startMenu(): Unit = {
    mainScene.root = MainPane(startSimulation,startGame)
    if(gameThread!=null)gameThread.interrupt()
  }

  def startSimulation(): Unit = {
    println("Start simulation")
    val gamePane = GamePane(startMenu)
    mainScene.root = gamePane
    val task = new Task[Unit]{
       override def call(): Unit = {
          val game = new Game(GamePane.mapPainter)
          game.startGame()
      }
    }
    gameThread = new Thread(task)
    gameThread.start()

  }

  def startGame(): Unit = {
    println("Start game")
  }

  stage = new PrimaryStage {
    title = "Hello"
    scene = mainScene
    width = widthValue
    height = heightValue
  }


}

