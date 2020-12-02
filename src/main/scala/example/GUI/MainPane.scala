package example.GUI

import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.GridPane
import scalafx.Includes._
import scalafx.geometry.Pos.Center


object MainPane {
  val basicLabel = "Main menu"

  def apply(goToSimulation: ()=>Unit, startGame: ()=>Unit, help: ()=>Unit ) = {
    val grid: GridPane = new GridPane()
    grid.setPadding(Insets(10, 10, 10, 10))
    grid.setVgap(10)
    grid.setHgap(10)
    val label = new Label(basicLabel)
    val btnSimulation = new Button("Start simulation")
    val btnGame = new Button("Play with bot")
    val btnHelp = new Button("Help section")
    val elems = List(label, btnSimulation,btnGame,btnHelp)
    btnSimulation.onAction = (e: ActionEvent) => {goToSimulation()}
    btnGame.onAction = (e: ActionEvent) => {startGame()}
    btnHelp.onAction = (e: ActionEvent) => {help()}
    for (elem <- elems) {
      grid.add(elem, 0, elems.indexOf(elem))
    }
    grid.setAlignment(Center)
    grid
  }
}