package example.GUI

import scalafx.scene.canvas.{Canvas,GraphicsContext}
import scalafx.scene.paint.Color
import javafx.animation.AnimationTimer
import scala.util.Random
import java.util.concurrent.CountDownLatch

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

    def drawBoard(nums:List[Int]) = {
        gc.clearRect(getStartMarginH,getStartMarginV,getWidth,getHeight)
        val startX = getStartMarginH
        val startY = getStartMarginV
        val width = getWidth
        val height = getHeight


        nums.length match {
            case 1 =>
                val size = Math.min(width,height)/2
                drawDice(startX+width/2-size/2,startY+height/2-size/2,size,nums(0))
            case 2 =>
                val size = Math.min(width,height)/3
                drawDice(startX+width/4-size/2,startY+height/2-size/2,size,nums(0))
                drawDice(startX+width*3/4-size/2,startY+height/2-size/2,size,nums(1))
            case 3 =>
                val size = Math.min(width,height)/3
                drawDice(startX+width/6-size/2,startY+height/2-size/2,size,nums(0))
                drawDice(startX+width*3/6-size/2,startY+height/2-size/2,size,nums(1))
                drawDice(startX+width*5/6-size/2,startY+height/2-size/2,size,nums(2))
            case 4 =>
                val size = Math.min(width,height)/3
                drawDice(startX+width/4-size/2,startY+height/4-size/2,size,nums(0))
                drawDice(startX+width*3/4-size/2,startY+height/4-size/2,size,nums(1))
                drawDice(startX+width/4-size/2,startY+height*3/4-size/2,size,nums(2))
                drawDice(startX+width*3/4-size/2,startY+height*3/4-size/2,size,nums(3))
            case 5 =>
                val size = Math.min(width,height)/3
                drawDice(startX+width/4-size/2,startY+height/4-size/2,size,nums(0))
                drawDice(startX+width*3/4-size/2,startY+height/4-size/2,size,nums(1))
                drawDice(startX+width/2-size/2,startY+height/2-size/2,size,nums(2))
                drawDice(startX+width/4-size/2,startY+height*3/4-size/2,size,nums(3))
                drawDice(startX+width*3/4-size/2,startY+height*3/4-size/2,size,nums(4))
            case 6 =>
                val size = Math.min(width,height)/3
                drawDice(startX+width/6-size/2,startY+height/4-size/2,size,nums(0))
                drawDice(startX+width*3/6-size/2,startY+height/4-size/2,size,nums(1))
                drawDice(startX+width*5/6-size/2,startY+height/4-size/2,size,nums(2))
                drawDice(startX+width/6-size/2,startY+height*3/4-size/2,size,nums(3))
                drawDice(startX+width*3/6-size/2,startY+height*3/4-size/2,size,nums(4))
                drawDice(startX+width*5/6-size/2,startY+height*3/4-size/2,size,nums(5))
            case _ =>                
        }
    }

    def startAnimation(howLongSeconds:Long,length:Int,latch:CountDownLatch) = {
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

    

}