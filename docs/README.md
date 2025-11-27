# LDTS_T02_G03 - CROSSY ROADS

## Game Description

Crossy Road is an endless arcade hopper game where you guide a character across busy roads, train tracks, and rivers while avoiding various obstacles. Your goal is to travel as far as possible without getting hit by vehicles, trains, or falling into the water.
As you progress, the difficulty increases with faster-moving traffic and more challenging patterns.

This project was developed by João Barros (up202406502@edu.fe.up.pt), Miguel Rocha (up202405484@edu.fe.up.pt) and Rosa Chilengue (up202109257@edu.fe.up.pt) for LDTS 2025-26.

## Implemented Features

At the moment there are no implemented features

## Planned Features

- **Connected Menus** - The user will have the capability of browsing through the different menus including in game ones. (Ex: Main Menu, Play, Shop and Pause).
- **Buttons** - Functional and interactive buttons.
- **Mouse and Keyboard control** - The mouse and keyboard inputs will be received through the respective events and interpreted according to the current game state.
- **Player control** - The player will move using the keyboard. (WASD)
- **Collision detection** - Collisions between different objects will be verified. (Ex: Player, Vehicles, Trains, Obstacles).
- **Score** - Each run will have a score, storing the highest score upon death.
- **Increase of difficulty** - As the game goes on, the difficulty will increase with faster-moving traffic and more challenging patterns.
- **Shop interaction and coins** - The player will be able to buy new skins for the character in the in-game shop, with coins that will be earned by playing.
- **Animations** - Several animations will be incorporated in the game, from the character walking to the movement of the vehicles and the animation of the character dying.
- **Multiple users** - The game will support multiple users, each one with their own highest score and their coins.

## Design

### General Structure
#### Problem in Context:
The first concern of our project was how the structure would look like. Since our game is dealling with a GUI and is divided by different gameStates some specific patterns came to mind in order to fullfil our needs the best way possible.

#### The Pattern:
Two main patterns were applied to the project, the **_Architectural Pattern_**, more specifically the Model-View-Controller style which is commonly used in a GUI and the **_State Pattern_** which is a behavioral design pattern that lets an object alter its behavior when its internal state changes.

#### Implementation:
Regarding the implementation, we now have classes which main purpose is to store data (model), classes that control the logic of the game (controllers) and classes that are responsible for the visual effects on the screen (viewers), these types of classes associate with each other in the following manner:

<p align="center" justify="center">
  <img src="images/UML/MVC.png"/>
</p>
<p align="center">
  <b><i>Fig 1. Model, Controller and Viewer pattern design</i></b>
</p>

As for the different states, they are divided with the same methodology as the mvc style, and permite the game to alter its behavior in a simple and efficient way.

#### Consequences:
The use of these patterns in the current design allow the following benefits:
- The several states that represent the different menus become explicit in the code, instead of relying on a series of flags.
- A well organized code acknowledging the Single Responsibility Principle.
- Easy to add new features throughout the development stage.


### Observers and listeners
#### Problem in Context:
Our game is controlled both by the mouse and the keyboard, many are the ways to receive input from these devices, for example: a thread that is running and every time it catches a signal it sends to the game itself (polling), the game being responsible for asking for input when needed, which is costly for our program since we may not send any signal to the program and unnecessary calls are made or the way we decided to implement which consists of using observers also known as listeners that are responsible for receiving the said input and redistributing it in a nicer and more usefull way to us. This takes some "weight" of the program as it will no longer need to be polling for input, as it will be properly warned when received.

#### The Pattern:
We have applied the **_Observer pattern_** which is a behavioral design pattern that lets you define a subscription mechanism to notify multiple objects about any events that happen to the object they’re observing. With this in mind the pattern allowed to solve the identified problems and apply a sustainable mechanism to receive any program input.

#### Implementation:
Implementation wise we store the observers in the main class (game class) and change its state according to the respective input processed by the available listener.
Though, it wasn't easy right from the start as our first attemp to implement this feature didn't act as expected. All listeners were always active, since when creating a Menu Button the listener would be activated by the newly created state, and it was far from being a structured and easy-to-read code.

<p align="center" justify="center">
  <img src="images/UML/ObsListener.png"/>
</p>
<p align="center">
  <b><i>Fig 2. Observers and Listeners screenshot</i></b>
</p>

#### Consequences:
Some consequences of using the stated pattern:
- Promotes the single responsibility principle.
- Clean code.
- Only the current game state is warned when an input is given.

### Battlefield builder
#### Problem in Context:
A battlefield consists in an aglomeration of elements such as walls, bombs, portals, a player, etc.
Having different battlefields in the various levels, instead of having a builder to each level, a battlefield loader that reads from a file and inserts into its super class (battlefield builder) the needed elements, was the most appealing option. This implementation makes it possible to only create specific elements and also generate battlefields in different ways.

#### The Pattern:
The **_Factory Method_** and **_Builder_** are two creational design patterns, the first one provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created. The second, allows you to construct complex objects step by step making a simpler code.

#### Implementation:
A factory is resposible for constructing the whole but the workers are the ones that actually execute the job. Analogously, our BattlefieldBuilder is a factory and its subclasses represent the workers.
As for the implmentation, the BattlefieldBuilder is an abstract class which knows how to construct a battlefield, however only its subclasses supply the necessary components of the battlefield. The BattlefieldLoader is one of referred subclasses that consists in a worker capable of reading different levels from different files. Another possible implementation would be a random loader that generates random components for the battlefield.
The builder pattern is implemented in all of the above classes by dividing the constructing in smaller steps.

<p align="center" justify="center">
  <img src="images/UML/BuilderLoader.png"/>
</p>
<p align="center">
  <b><i>Fig 3. Battlefield builder and loader</i></b>
</p>

These classes can be found in the following files:
- [BattlefieldBuilder](../src/main/java/com/g57/model/battlefield/BattlefieldBuilder.java)
- [BattlefieldLoader](../src/main/java/com/g57/model/battlefield/BattlefieldLoader.java)

#### Consequences:
Benefits of applying the above pattern:
- You avoid tight coupling between the creator and the concrete products.
- Open/Closed Principle. You can introduce new types of products into the program without breaking existing client code.
- You can construct objects step-by-step, defer construction steps or run steps recursively.


### Different types of commands
#### **Problem in Context:**
In an initial and more simplified version of the current game, the diversity in between buttons was not significant. However, in the course of the development of the project, the number of buttons increased exponentially and the need to generalise the button element became more evident. That said and knowing that good software design is often based on the principle of separation of concerns, a major refactor had to be done.

#### The Pattern:
We have applied the **_Command_** also know as Action pattern. This pattern turns a request into a stand-alone object that contains all information about the request.

#### Implementation:
Regarding the implemetation process, all the Button classes were deleted and transformed into a single **Button** with a command attribute. These **commands**  implement the same interface having an execution method that takes no parameters. This interface lets you use various commands with the same request sender, without coupling it to concrete classes of commands. As a bonus, now you can switch command objects linked to the sender, effectively changing the sender’s behavior at runtime.

<p align="center" justify="center">
  <img src="images/UML/ButtonCommand.png"/>
</p>
<p align="center">
  <b><i>Fig 4. Buttons and commands</i></b>
</p>

These classes can be found in the following files:
- [Button](../src/main/java/com/g57/model/element/button/Button.java)
- [Command](../src/main/java/com/g57/model/item/command/Command.java)

#### Consequences:
The Command Pattern allows the following consequences:
- You can decouple classes that invoke operations from classes that perform these operations (SRP).
- You can implement undo/redo.
- You can assemble a set of simple commands into a complex one.
- The code may become more complicated since you’re introducing a whole new layer between senders and receivers.


### GUI
#### Problem in Context:
Aiming for a structured and unstable (easy to change) code, we tried to make it as general as possible. The lanterna library contains various functions that aren't useful to our program, Interface Segregation Principle violation, and lacks some other functions that our interface needs. Also, if using the raw library, our game (high level module) would be directly depending on a low level module. This is a violation of the Dependency Inversion Principle (DIP). A need to implement an interface that solves these problems was born.

#### The Pattern:
We have applied the **_Facade_** pattern. A facade provides a simple interface to a complex subsystem which contains lots of moving parts, allowing us to only include the features that really matter.

#### Implementation:
<p align="center" justify="center">
  <img src="images/UML/GUI.png"/>
</p>
<p align="center">
  <b><i>Fig 5. Observers and Listeners screenshot</i></b>
</p>

These classes can be found in the following files:
- [Game](../src/main/java/com/g57/Game.java)
- [GUI](../src/main/java/com/g57/gui/GUI.java)
- [LanternaGUI](../src/main/java/com/g57/gui/LanternaGUI.java)

#### Consequences:
The use of the Facade Pattern in the current design allows the following benefits:
- Isolate code from the complexity of a subsystem.
- Promotes testability and replaceability.
- Expand lanterna functionalities as well as respecting the Interface Segregation Principle.

## Known-code smells

We have fixed all the errors reported by error-prone. No other major code smells identified.

## Testing

### Screenshot of coverage report
<p align="center" justify="center">
  <img src="images/screenshots/codeCoverage"/>
</p>
<p align="center">
  <b><i>Fig 6. Code coverage screenshot</i></b>
</p>

### Link to mutation testing report
[Mutation tests](../build/reports/pitest/202105302045/index.html)

## Self-evaluation

The work was divided in a mutual way and we all contributed with our best. It helped us to enrich our java and principle/pattern knwoledge, as well as our team work.

- Donal Knuth: 33.3%
- Timothy J. Berners-Lee: 33.3%
- Vinton G. Cerf: 33.3%
