package example.GUI

import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.GridPane
import scalafx.Includes._
import scalafx.geometry.Pos.Center
import scalafx.scene.canvas.{Canvas,GraphicsContext}
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.RowConstraints

object GamePane{

    val gameCanvas:Canvas = new Canvas(){
        layoutY=100
        layoutX=100
        height=600
        width=600
    }

    val gc:GraphicsContext = gameCanvas.graphicsContext2D

    val mapPainter:MapPainter = MapPainter(gameCanvas,gc)

    val listener: InvalidationListener = new InvalidationListener() {
        @Override
        def invalidated(o: Observable) = {
            println(s"Changed size ${gameCanvas.getWidth()}  ${gameCanvas.getHeight()}")
        }
    }

    gameCanvas.width.addListener(listener)
    gameCanvas.height.addListener(listener)


    def apply(goBack: ()=>Unit) ={
        val grid: GridPane = new GridPane()
        grid.setPadding(Insets(10, 10, 10, 10))
        grid.setVgap(10)
        grid.setHgap(10)
        val button1 = new Button("Back to menu")
        val elems = List(gameCanvas, button1)
        
        gameCanvas.width.bind(grid.widthProperty())
        gameCanvas.height.bind(grid.heightProperty()*0.9)
        button1.onAction = (e: ActionEvent) => {goBack()}
        for (elem <- elems) {grid.add(elem, 0, elems.indexOf(elem))}
        gridConstraints(grid)
        grid.setAlignment(Center)
        grid
    }

    def gridConstraints(grid: GridPane) = {
        val colConstraint = new ColumnConstraints();
        colConstraint.setPercentWidth(100)
        grid.getColumnConstraints().add(colConstraint)
        val canvasConstraint = new RowConstraints()
        canvasConstraint.setPercentHeight(90)
        val buttonsConstraint = new RowConstraints()
        buttonsConstraint.setPercentHeight(10)
        grid.getRowConstraints().addAll(canvasConstraint,buttonsConstraint)
    }
}