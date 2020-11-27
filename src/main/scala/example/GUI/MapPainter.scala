package example.GUI

import scalafx.scene.canvas.{Canvas,GraphicsContext}
import scalafx.scene.paint.Color
import javafx.animation.AnimationTimer
import scala.util.Random
import java.util.concurrent.CountDownLatch
import scalafx.scene.text.Font
import javafx.scene.text.FontWeight

object MapPainter{

    var canvas:Canvas = _

    def apply(canvas: Canvas,gc: GraphicsContext) = {
        this.canvas = canvas
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight())
        val painter = new MapPainter(gc)
        painter
    }

    def getHeight() = canvas.getHeight()
    def getWidth() = canvas.getWidth()
}

class MapPainter(gc:GraphicsContext){

    def getStartMarginH = MapPainter.getWidth/20 
    def getStartMarginV = MapPainter.getHeight/20 
    def getWidth = MapPainter.getWidth() - 2*getStartMarginH
    def getHeight = MapPainter.getHeight() - 2*getStartMarginV 

    var lastDices:List[Int] = List()
    var selectedDices:List[Int] = List()
    var centerPoints:Array[(Double,Double)] = Array()
    var isFirstPlayerTurn = true
    var scores = Array(0,0,0)
    val standardFont = Font.font("Helvetica",20)
    gc.setFont(standardFont)

    def drawText = {
            isFirstPlayerTurn match {
                case true => 
                    gc.setFill(Color.Blue)
                    gc.fillText(s"Player 1: ${scores(0)} + ${scores(2)}",10,50)
                    gc.setFill(Color.Black)
                    gc.fillText(s"Player 2: ${scores(1)}",10,80)
                case false => 
                    gc.setFill(Color.Blue)
                    gc.fillText(s"Player 2: ${scores(1)} + ${scores(2)}",10,80)
                    gc.setFill(Color.Black)
                    gc.fillText(s"Player 1: ${scores(0)}",10,50)
            }    
    }

    def clearBoard = {
        gc.clearRect(0,0,MapPainter.getWidth,MapPainter.getHeight)
        drawText
    }

    def drawDice(x:Double,y:Double,size:Double,num:Int) = {
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x,y,size,size)
        num match {
            case 1 =>
                gc.fillOval(x+size/4,y+size/4,size/2,size/2)
            case 2 => 
                gc.fillOval(x+size/3,y+size/8,size/3,size/3)
                gc.fillOval(x+size/3,y+size/2,size/3,size/3)
            case 3 => 
                gc.fillOval(x+size*1/8,y+size*1/8,size/4,size/4)
                gc.fillOval(x+size*3/8,y+size*3/8,size/4,size/4)
                gc.fillOval(x+size*5/8,y+size*5/8,size/4,size/4)
            case 4 => 
                gc.fillOval(x+size*1/9,y+size*1/9,size/3,size/3)
                gc.fillOval(x+size*5/9,y+size*1/9,size/3,size/3)
                gc.fillOval(x+size*1/9,y+size*5/9,size/3,size/3)
                gc.fillOval(x+size*5/9,y+size*5/9,size/3,size/3)
            case 5 => 
                gc.fillOval(x+size*1/12,y+size*1/12,size/4,size/4)
                gc.fillOval(x+size*8/12,y+size*1/12,size/4,size/4)
                gc.fillOval(x+size*1/12,y+size*8/12,size/4,size/4)
                gc.fillOval(x+size*8/12,y+size*8/12,size/4,size/4)
                gc.fillOval(x+size*9/24,y+size*9/24,size/4,size/4)                
            case 6 => 
                gc.fillOval(x+size*4/24,y+size*2/24,size/4,size/4)
                gc.fillOval(x+size*4/24,y+size*9/24,size/4,size/4)
                gc.fillOval(x+size*4/24,y+size*16/24,size/4,size/4)
                gc.fillOval(x+size*14/24,y+size*2/24,size/4,size/4)
                gc.fillOval(x+size*14/24,y+size*9/24,size/4,size/4)
                gc.fillOval(x+size*14/24,y+size*16/24,size/4,size/4)
            case _ => 
        }
    }

    def getcenterPoints(length:Int):Array[(Double,Double)] = {

        val startX = getStartMarginH
        val startY = getStartMarginV
        val width = getWidth
        val height = getHeight
        val size = Math.min(width,height)/3

        length match {
            case 1 => 
                val point = (startX+width/2-size/2,startY+height/2-size/2)
                Array(point)
            case 2 =>
                val point1 = (startX+width/4-size/2,startY+height/2-size/2)
                val point2 = (startX+width*3/4-size/2,startY+height/2-size/2)
                Array(point1,point2)
            case 3 =>
                val point1 = (startX+width*1/6-size/2,startY+height/2-size/2)
                val point2 = (startX+width*3/6-size/2,startY+height/2-size/2)
                val point3 = (startX+width*5/6-size/2,startY+height/2-size/2)
                Array(point1,point2,point3)
            case 4 =>
                val point1 = (startX+width/4-size/2,startY+height/4-size/2)
                val point2 = (startX+width*3/4-size/2,startY+height/4-size/2)
                val point3 = (startX+width/4-size/2,startY+height*3/4-size/2)
                val point4 = (startX+width*3/4-size/2,startY+height*3/4-size/2)
                Array(point1,point2,point3,point4)
            case 5 =>
                val point1 = (startX+width/4-size/2,startY+height/4-size/2)
                val point2 = (startX+width*3/4-size/2,startY+height/4-size/2)
                val point3 = (startX+width/2-size/2,startY+height/2-size/2)
                val point4 = (startX+width/4-size/2,startY+height*3/4-size/2)
                val point5 = (startX+width*3/4-size/2,startY+height*3/4-size/2)
                Array(point1,point2,point3,point4,point5)
            case 6 =>
                val point1 = (startX+width*1/6-size/2,startY+height/4-size/2)
                val point2 = (startX+width*3/6-size/2,startY+height/4-size/2)
                val point3 = (startX+width*5/6-size/2,startY+height/4-size/2)
                val point4 = (startX+width*1/6-size/2,startY+height*3/4-size/2)
                val point5 = (startX+width*3/6-size/2,startY+height*3/4-size/2)
                val point6 = (startX+width*5/6-size/2,startY+height*3/4-size/2)
                Array(point1,point2,point3,point4,point5,point6)
        }
    }

    def drawBoard(nums:List[Int]) = {
        clearBoard
        markSelected()

        val width = getWidth
        val height = getHeight
        val size = Math.min(width,height)/3

        lastDices = nums
        centerPoints = getcenterPoints(nums.length)

        nums.length match {
            case 1 =>
                drawDice(centerPoints(0)._1,centerPoints(0)._2,size,nums(0))
            case 2 =>
                drawDice(centerPoints(0)._1,centerPoints(0)._2,size,nums(0))
                drawDice(centerPoints(1)._1,centerPoints(1)._2,size,nums(1))
            case 3 =>
                drawDice(centerPoints(0)._1,centerPoints(0)._2,size,nums(0))
                drawDice(centerPoints(1)._1,centerPoints(1)._2,size,nums(1))
                drawDice(centerPoints(2)._1,centerPoints(2)._2,size,nums(2))
            case 4 =>
                drawDice(centerPoints(0)._1,centerPoints(0)._2,size,nums(0))
                drawDice(centerPoints(1)._1,centerPoints(1)._2,size,nums(1))
                drawDice(centerPoints(2)._1,centerPoints(2)._2,size,nums(2))
                drawDice(centerPoints(3)._1,centerPoints(3)._2,size,nums(3))
            case 5 =>
                drawDice(centerPoints(0)._1,centerPoints(0)._2,size,nums(0))
                drawDice(centerPoints(1)._1,centerPoints(1)._2,size,nums(1))
                drawDice(centerPoints(2)._1,centerPoints(2)._2,size,nums(2))
                drawDice(centerPoints(3)._1,centerPoints(3)._2,size,nums(3))
                drawDice(centerPoints(4)._1,centerPoints(4)._2,size,nums(4))
            case 6 =>
                drawDice(centerPoints(0)._1,centerPoints(0)._2,size,nums(0))
                drawDice(centerPoints(1)._1,centerPoints(1)._2,size,nums(1))
                drawDice(centerPoints(2)._1,centerPoints(2)._2,size,nums(2))
                drawDice(centerPoints(3)._1,centerPoints(3)._2,size,nums(3))
                drawDice(centerPoints(4)._1,centerPoints(4)._2,size,nums(4))
                drawDice(centerPoints(5)._1,centerPoints(5)._2,size,nums(5))
            case _ =>                
        }
    }

    def startAnimation(howLongSeconds:Long,length:Int,latch:CountDownLatch) = {
        selectedDices = List()
        new AnimationTimer{
            var startTime: Long = 0
            var lastUpdate: Long = 0
            @Override
            def handle(now: Long){
                if(startTime==0)startTime=now
                if(now-startTime>=howLongSeconds*1_000_000_000) {
                    stop()
                    latch.countDown()
                }
                else if(now - lastUpdate >= 50_000_000){
                    drawBoard(Seq.fill(length)(Random.nextInt(5)+1).toList)
                    lastUpdate = now
                }
            }
        }.start()
    }

    def markSelected() ={
        selectedDices.foreach{
            case x:Int =>
                val point = centerPoints(x)
                gc.setFill(Color.Blue)
                val size = Math.min(getWidth,getHeight)/3
                val sqrtTwo = Math.sqrt(2)
                gc.fillOval(point._1-size/5,point._2-size/5,size*sqrtTwo,size*sqrtTwo)
                gc.setFill(Color.Black)
        }
    } 

    def getSelectedDices():List[Int] = selectedDices.map(idx => lastDices(idx))

    def addClickListener() = {
        MapPainter.canvas.setOnMouseClicked( event => {
            val x = event.getX()
            val y = event.getY()
            val startX = getStartMarginH
            val startY = getStartMarginV
            val width = getWidth
            val height = getHeight


            val size = Math.min(width,height) / 3
            // println(s"Clicked: $x  $y \t Size: $size")
            centerPoints.zipWithIndex.foreach{ 
                case pointIdx:((Double,Double),Int) => {
                    val point = pointIdx._1
                    val idx = pointIdx._2
                    // println(s"Point $idx: $point ${(point._1-x).abs}  ${(point._2-y).abs}")
                    val diffX = x-point._1
                    val diffY = y-point._2
                    if(diffX>= 0 && diffX <= size && diffY>=0 && diffY <= size) {
                        println(s"Point $idx")
                        if(selectedDices.contains(idx)) selectedDices = selectedDices.filter(elem => elem!=idx)
                        else selectedDices = selectedDices :+ idx
                        drawBoard(lastDices) 
                    } 
                }
            }            

        })
    }

    

}