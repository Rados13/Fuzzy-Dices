package example.GUI



import scalafx.scene.layout.GridPane
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.RowConstraints
import javafx.event.ActionEvent
import scalafx.scene.control.Button
import javafx.scene.text.Text
import scalafx.scene.layout.HBox
import javafx.geometry.Pos
import collection.JavaConverters._
import scalafx.geometry.Pos.Center
import example.Logic.Bot
import javafx.scene.control.Hyperlink;
import javafx.scene.input.Clipboard
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import scalafx.beans.property.IntegerProperty
import javafx.util.converter.IntegerStringConverter
import javafx.util.converter.NumberStringConverter
import org.jfree.chart.ChartPanel
import javax.swing.JFrame
import javax.swing.WindowConstants
import javafx.application.Platform
import scalafx.scene.control.Label

object HelpPane{

    val clipboard: Clipboard  = Clipboard.getSystemClipboard();
   

    var chartFrame:ChartFrame = new ChartFrame()


    private def btnsHbox(backToMenu: ()=>Unit) = {
        val btnBack = new Button("Back to menu")
        btnBack.onAction = (e:ActionEvent) => {backToMenu()}

        val btnCharts = new Button("See bot charts")
        btnCharts.onAction = (e: ActionEvent) => {
            chartFrame.start()
            Platform.exit()
        }

        val hbox = new HBox()
        hbox.setAlignment(Pos.CENTER)
        hbox.setSpacing(50)
        var btnList:List[javafx.scene.Node] = List(btnBack,btnCharts)
        hbox.getChildren().addAll(btnList.asJavaCollection)
        hbox
    }
    private def textFieldsHbox() = {
        val hbox = new HBox()
        hbox.setAlignment(Pos.CENTER)
        hbox.setSpacing(50)
        
        val labelScore = new Label("Score:");
        val txFieldScore = new TextField(chartFrame.scoreProperty.toString())
        txFieldScore.textProperty().bindBidirectional(chartFrame.scoreProperty,new NumberStringConverter())
        val hbScore = new HBox()
        val scoreList:List[javafx.scene.Node] = List(labelScore,txFieldScore)
        hbScore.getChildren().addAll(scoreList.asJavaCollection)

        
        val labelRepeated = new Label("Repeated:")
        val txFieldRepeated = new TextField(chartFrame.repeatedProperty.toString())
        txFieldRepeated.textProperty().bindBidirectional(chartFrame.repeatedProperty,new NumberStringConverter())
        val hbRepeated = new HBox()
        val repeatedList:List[javafx.scene.Node] = List(labelRepeated,txFieldRepeated)
        hbRepeated.getChildren().addAll(repeatedList.asJavaCollection)


        val labelWhich = new Label("Which repeated most:")
        val txFieldWhich = new TextField(chartFrame.whichDiceRepeatedMostProperty.toString())
        txFieldWhich.textProperty().bindBidirectional(chartFrame.whichDiceRepeatedMostProperty,new NumberStringConverter())
        val hbWhich = new HBox()
        val whichList:List[javafx.scene.Node] = List(labelWhich,txFieldWhich)
        hbWhich.getChildren().addAll(whichList.asJavaCollection)


        val labelDices = new Label("Dices num:")
        val txFieldDices = new TextField(chartFrame.dicesNumProperty.toString())
        txFieldDices.textProperty().bindBidirectional(chartFrame.dicesNumProperty,new NumberStringConverter())
        val hbDices = new HBox()
        val dicesList:List[javafx.scene.Node] = List(labelDices,txFieldDices)
        hbDices.getChildren().addAll(dicesList.asJavaCollection)

        var txFieldList:List[javafx.scene.Node] = List(hbScore,hbWhich,hbRepeated,hbDices)
        hbox.getChildren().addAll(txFieldList.asJavaCollection)
        hbox
    }

    def apply(backToMenu: ()=> Unit) = {
        val grid = GamePane.setUpGriPane()
        val btnHbox = btnsHbox(backToMenu)
        val txFieldHbox = textFieldsHbox()

        val txtArea = new Text(textIntro)
        val txtField = new TextField(link)
        txtField.setEditable(false)
        txtField.setPrefWidth(400)
        txtField.setMaxWidth(400)
        val txtPos = new Text(textPos)

        val vbox = new VBox(txtArea,txtField,txtPos)

        val elems:List[javafx.scene.Node] = List(vbox, txFieldHbox,btnHbox)
        for (elem <- elems) {grid.add(elem, 0, elems.indexOf(elem))}
        grid.setAlignment(Center)
        grid
    }

    val textIntro = 
    """
    This app was created as project for Fundamentals of Artificial Intelligence.
    The goal of project was to create some type of simulation with usage of Fuzzy Logic.
    I decided to use it as the decision making for Bot, which will play the mini-game (game in dices) from Kingdom Come Deliverance.
    Link to bigger description of mini game:
    """
    
    val link = "https://kingdom-come-deliverance.fandom.com/wiki/Dice"
    
    val textPos=
    """
    Now in this app are two possibilites you can:
    - start the simulation of playing two bots against each other,
    - play against bot
    The file with declared Fuzzy Logic is in main directory of project and it's name is "fuzzy_dices.fcl".
    Here you can also add draw chart for diffrent values of this input parameters. Poorly due to implementation in JFuzzyLogic 
    the showing of charts will end up application.
    
    """
}