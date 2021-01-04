# Fuzzy-Dices

## Introduction 

This project was made for course of "Basics of Artificial Intelligence". It is write with use of Scala and ScalaFX.

## Documentation

### Files short description

The source code is in src folder.

* [GUI](src/main/scala/example/GUI) - folder with GUI files
  * [ChartFrame](src/main/scala/example/GUI/ChartFrame.scala) - ChartFrame class responsible for creating thread where defuzzified variables chart will be displayed
  * [GamePane](src/main/scala/example/GUI/GamePane.scala) - GamePane object responsible for creating GridPane where game interface is displayed
  * [HelpPane](src/main/scala/example/GUI/HelpPane.scala) - HelpPane object responsible for creating GridPane where help menu is displayed
  * [MainPane](src/main/scala/example/GUI/MainPane.scala) - MainPane object responsible for creating GridPane where main menu is displayed
  * [MapPainter](src/main/scala/example/GUI/MapPainter.scala) - MapPainter class and object responsible for drawing animation during game  
* [Logic](src/main/scala/example/Logic) - folder with simulation logic files
  * [Bot](src/main/scala/example/Logic/Bot.scala) - Bot class and object responsible for all actions made by bot
  * [DicesSet](src/main/scala/example/Logic/DicesSet.scala) - DicesSet object responsible for additional function which are made on the set of dices (Int)
  * [Game](src/main/scala/example/Logic/Game.scala) - Game class and object responsible for handling events during the game/simulation
  * [Player](src/main/scala/example/Logic/Player.scala) - Player Trait and HumanPlayer class responsible for handling action made by player during the game/simulation  
* [App](src/main/scala/example/App.scala) - Object where ScalaFX app is initialized and all required panes
* [Fuzzy Dices Rules](fuzzy_dices.fcl) - JFuzzyLogic file with definied rules what decision the bot should make.

  
### Run it local

Run command
```
sbt run
```






