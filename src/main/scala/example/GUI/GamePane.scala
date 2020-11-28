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
import scalafx.scene.layout.HBox
import javafx.geometry.Pos
import collection.JavaConverters._
import example.Logic.HumanPlayer
import example.Logic.RollAgain
import example.Logic.Finish

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


    val btnResumeText = "Resume simulation"
    val btnStopText = "Stop simulation"

    def setUpGriPane():GridPane = {
        val grid: GridPane = new GridPane()

        grid.setPadding(Insets(10, 10, 10, 10))
        grid.setVgap(10)
        grid.setHgap(10)
        grid
    }

    def createButtonsHbox(functions:Array[()=> Unit],player:HumanPlayer):HBox = {
        val btnBack = new Button("Back to menu")
        val btnStop = new Button(btnStopText)
        var btnList:List[javafx.scene.Node] = List(btnBack,btnStop)


        btnBack.onAction = (e: ActionEvent) => {functions(0)()}
        btnStop.onAction = (e: ActionEvent) => {
            functions(1)()
            btnStop.text.set(if(btnStop.text.get()==btnResumeText) btnStopText else btnResumeText )
        }

        if(player!=null){
            mapPainter.addClickListener()
            val btnRoll = new Button("Roll next")
            val btnEnd = new Button("End rolling")
            btnRoll.onAction = (e:ActionEvent) => {
                val selectedDices = mapPainter.getSelectedDices()
                if(player.checkIfPossibleToTake(selectedDices)){
                    player.setMove(RollAgain(selectedDices))
                    player.isSelected = true
                }else mapPainter.drawAlert("Not possible move")
            }   
            btnEnd.onAction = (e:ActionEvent) => {
                val selectedDices = mapPainter.getSelectedDices()
                if(player.checkIfPossibleToTake(selectedDices)){
                    player.setMove(Finish(selectedDices))
                    player.isSelected = true
                }else mapPainter.drawAlert("Not possible move")
            }
            btnList = btnList ++ List(btnRoll,btnEnd)    
        }


        val hbox = new HBox()
        hbox.setAlignment(Pos.CENTER)
        hbox.setSpacing(50)
        hbox.getChildren().addAll(btnList.asJavaCollection)
        hbox
    }

    def apply(btnsFunctions: Array[()=>Unit],player:HumanPlayer = null) ={
        val grid = setUpGriPane()
        val hbox = createButtonsHbox(btnsFunctions,player)
        val elems = List(gameCanvas, hbox)

        gameCanvas.width.bind(grid.widthProperty())
        gameCanvas.height.bind(grid.heightProperty()*0.9)
        

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