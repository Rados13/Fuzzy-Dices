package example.GUI

import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.GridPane
import scalafx.Includes._
import scalafx.geometry.Pos.Center


object MainPane {
  val basicLabel = "Main menu"

  def apply(goToSimulation: ()=>Unit, startGame: ()=>Unit ) = {
    val grid: GridPane = new GridPane()
    grid.setPadding(Insets(10, 10, 10, 10))
    grid.setVgap(10)
    grid.setHgap(10)
    val label = new Label(basicLabel)
    val button1 = new Button("Start simulation")
    val button2 = new Button("Play with bot")
    val elems = List(label, button1,button2)
    button1.onAction = (e: ActionEvent) => {goToSimulation()}
    button2.onAction = (e: ActionEvent) => {startGame()}
    for (elem <- elems) {
      grid.add(elem, 0, elems.indexOf(elem))
    }
    grid.setAlignment(Center)
    grid
  }
}