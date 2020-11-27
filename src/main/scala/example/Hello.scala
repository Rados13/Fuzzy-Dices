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
import example.Logic.GameStatus
import example.Logic.HumanPlayer

// https://www.gry-online.pl/S024.asp?ID=1859&PART=122

object Hello extends JFXApp {
  // println(greeting)

  val widthValue: Double = 1280
  val heightValue: Double = 720
  var game: Game = null
  var player:HumanPlayer = null

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
    if(game!=null) game.status = GameStatus.Ended
    if(player!=null) player.isSelected = true
    mainScene.root = MainPane(startSimulation,startGame)
  }


  

  def startSimulation(): Unit = {
    println("Start simulation")
    game = Game.startSimulation(GamePane.mapPainter)
    val task = new Task[Unit]{ override def call(): Unit = game.startGame()}
    val btnsFunctions:Array[() => Unit] = Array(
      startMenu,
      () => {game.status = game.status match {
        case GameStatus.Run => GameStatus.Stoped
        case GameStatus.Stoped => GameStatus.Run
        case _ => GameStatus.Ended
      }}
    )
    val gamePane = GamePane(btnsFunctions)
    mainScene.root = gamePane

    val gameThread = new Thread(task)
    gameThread.start()

  }

  def startGame(): Unit = {
    println("Start game")
    val result:(Game,HumanPlayer) = Game.startGame(GamePane.mapPainter)
    game = result._1
    player = result._2
    val task = new Task[Unit]{ override def call(): Unit = game.startGame()}
    val btnsFunctions:Array[() => Unit] = Array(
      startMenu,
      () => {game.status = game.status match {
        case GameStatus.Run => GameStatus.Stoped
        case GameStatus.Stoped => GameStatus.Run
        case _ => GameStatus.Ended
      }}
    )

    val gamePane = GamePane(btnsFunctions,player)
    mainScene.root = gamePane

    val gameThread = new Thread(task)
    gameThread.start()
    
  }

  stage = new PrimaryStage {
    title = "Hello"
    scene = mainScene
    width = widthValue
    height = heightValue
  }


}

