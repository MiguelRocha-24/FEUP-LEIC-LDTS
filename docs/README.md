# LDTS_T02_G03 - CROSSY ROADS

## Game Description

Crossy Roads is an endless arcade hopper game where you guide a character across busy roads and rivers while avoiding various obstacles. Your goal is to travel as far as possible without getting hit by vehicles or falling into the water.
As you progress, the difficulty increases with faster-moving traffic and more challenging patterns.

This project was developed by João Barros (up202406502@edu.fe.up.pt), Miguel Rocha (up202405484@edu.fe.up.pt) and Rosa Chilengue (up202109257@edu.fe.up.pt) for LDTS 2025-26.

## Implemented Features

- **Keyboard control** - The keyboard inputs are received through the respective events and interpreted according to the current game state. 
- **Player control** - The player moves using the keyboard. (WASD)
- **Collision detection** - Collisions between player and car objects are verified.
- **Lanes** - The game has different lanes, each one with different types of entities.
- **Animations** - Several animations are incorporated in the game, like the character dying, the character changing direction (sprite) with input and the "glowing" of coins.
- **Score** - Each run will have a score, storing the highest score upon death.
- **Multiple users** - The game supports multiple users, each one with their own highest score and their coins.
- **Shop interaction and coins** - With coins earned throught the gameplay, each user is able to buy new skins for the character in the in-game shop.
- **Connected Menus** - The user has the ability to browse through the different menus; most of them are connected to the main menu, like the Shop, User Selection, and GameOver Menu.
- **Camera** - To allow for infinite gameplay, a camera was implemented, that follows the player upwards while keeping smooth movement. The camera also holds the "lower boundary" of the game, ensuring movement upwards and not downwards. 
- **Increase of difficulty** - As the game goes on, the difficulty increases -> every entity moves progressively faster. 

## Planned Features
All planned features were either implemented, or a decision was made against implementing them as justified below
## Not Implemented Features

- **Buttons** - Functional and interactive buttons. Reason: A keyboard-focused navigation system for menus proved to be easier to implement and user-friendly enough so that it was not necessary to implement buttons.
- **Mouse control** - The mouse inputs will be received through the respective events and interpreted according to the current game state. Reason: Same as above.


## Game Preview

The following images show the different menus, as well as in-game features in-depth. 
<p align="center" justify="center">
  <img src="images/mockups/UserSelectionState.png" width="1000"/>
</p>
<p align="center">
  <b><i>Fig 1. User Selection</i></b>
</p>  

<br>
<br />

<p align="center" justify="center">
  <img src="images/mockups/MainMenuState.png" width="1000"/>
</p>
<p align="center">
  <b><i>Fig 2. Main Menu</i></b>
</p>  

<br>

<br />

<p align="center" justify="center">
  <img src="images/mockups/CurrentUserStats.png" width="500"/>
</p>
<p align="center">
  <b><i>Fig 3. Current User Stats</i></b>
  <p align="center">
  <i>Located at top right of Main Menu</i>
</p>  

<br>


<br />

<p align="center" justify="center">
  <img src="images/mockups/GamePlay.png" width="1000"/>
</p>
<p align="center">
  <b><i>Fig 4. GamePlay Screenshot</i></b>
</p>  

<br>

<br />

<p align="center" justify="center">
  <img src="images/mockups/GameOverScreen.png" width="1000"/>
</p>
<p align="center">
  <b><i>Fig 5. Game Over Screen</i></b>
</p>  

<br>
<br />

<table align="center">
  <tr>
    <td><img src="images/mockups/Shop1.png" width="400"/></td>
    <td><img src="images/mockups/Shop2.png" width="400"/></td>
  </tr>
  <tr>
    <td><img src="images/mockups/Shop3.png" width="400"/></td>
    <td><img src="images/mockups/Shop4.png" width="400"/></td>
  </tr>
</table>
<p align="center">
  <b><i>Fig 6. Shop Interface (Unlocked, Selected, and Locked Skins)</i></b>
</p>

<br>
<br />

<p align="center" justify="center">
  <img src="images/mockups/DeathAnimation.gif" width="1000"/>
</p>
<p align="center">
  <b><i>GIF 1. Death Animation</i></b>
</p>

## Design

### General Structure
#### Problem in Context:

Following the given recommendations, the project follows an MVC architecture, which divides the game in three parts, which store the logic and data (model), the visual effects (view) and the control of the game (controller).

#### The Pattern:
As described before, regarding the "Architectural Pattern", the project follows the Model-View-Controller style. This ensures the concerns are separate: 
- **Model**: stores the logic and data. Completely independent of user interface.
- **View**: Handles visualization. Reads from model and draws to screen using implemented GUI.
- **Controller**: Processes input and dictates the flow of the game.



#### Controller Implementation:
The Controller package is responsible for handling user input and updating the game state. It acts as the bridge between the Model and the View. The main classes of this package do the following: 
- **GameController**: The central controller for the gameplay state. It orchestrates the game loop, managing the player's movement, lane generation, collision detection, and score updates. It delegates specific tasks to specialized sub-controllers.
- **LaneController**: An interface for handling logic specific to different types of lanes.
- **PlayerController**: Dedicated to handling player-specific logic, such as movement validation and position updates based on keyboard input.
- **MenuController**: Manages navigation and interaction within the various menu screens (Main Menu, Shop, Game Over), allowing users to select options and switch states.

This structure ensures that the game logic is modular and that input handling is separated from the core game rules. 

### Main Controllers in the package
<p align="center" justify="center">
  <img src="images/UML/Controllers.png" width="1000"/>
</p>
<p align="center">
  <b><i>Fig 7. Main controllers</i></b>
</p>
<p align="center" justify="center">
  <img src="images/UML/GameController.png" width="1500"/>
</p>
<p align="center">
  <b><i>Fig 8. Main Game Controller</i></b>
</p>

#### Model Package UML Design Decisions

The Model package is split into two domains: **Game** (gameplay entities) and **Menu** (user interface data). To keep them understable / maintain relevancy, we decided to remove from the UMLs:

- **Position and Direction**: Used by almost every game entity. Including them would clutter the UML and make it less readable.
- **GameOver and RemoveUser**: These classes are just to help the state, and have no relevant relationships to other model classes. `GameOver` is standalone, and `RemoveUser` is a thin wrapper around Menu.

The **Game Model** also holds a camera, to make sure the entities are drawn in their correct locations - a helper to the viewer.


### Game Model UML
<p align="center" justify="center">
  <img src="images/UML/GameModel.png"/>
</p>
<p align="center">
  <b><i>Fig 9. Game Model</i></b>
</p>

<br>

### Menu Model UML
<p align="center" justify="center">
  <img src="images/UML/MenusModel.png"/>
</p>
<p align="center">
  <b><i>Fig 10. Menu Model</i></b>
</p>

### View Package

The View component separates the game's visual logic from the rendering library through a layered architecture:

#### GUI Abstraction
- **`GUI` Interface**: Abstracts all graphical operations (`drawText`, `clear`, `refresh`), ensuring the application doesn't depend on a specific library - following SOLID's Dependency Inversion Principle.
- **`LanternaGUI`**: Implementation of Lanterna.

#### Viewer Hierarchy
- **`Viewer<T>`**: Abstract base class holding a model reference and defining `draw(GUI gui)`. Each `State<T>` holds a corresponding `Viewer<T>`.
- **Game Viewers** (`view.game`): `GameViewer` orchestrates all gameplay rendering; `SpriteViewer<T>` provides sprite-based rendering with caching.
- **Menu Viewers** (`view.menu`): `MenuViewer`, `GameOverViewer`, `ShopViewer`, `NewUserViewer`, `RemoveUserViewer`

#### Benefits
- **Interchangeability**: Lanterna can be replaced by implementing a new `GUI`, without modifying viewers.
- **Testability**: Viewers can be tested by mocking the `GUI` interface.
- **Modularity**: New visuals are added by creating new Viewer classes.


### View Package UML
<p align="center" justify="center">
  <img src="images/UML/ViewPackage.png"/>
</p>
<p align="center">
  <b><i>Fig 11. View package</i></b>
</p>

## Design Patterns

This section details the main design patterns used throughout the project, explaining the problem each one solves and how it was implemented.

---

### 1. State Pattern

#### Problem in Context
The game has multiple states (Main Menu and its options, and the main Gameplay), with different behaviours and input handling. Managing these with conditionals would lead to complex, hard-to-maintain code. The fact that text would also require sprites, or become almost unreadable due to the font of the main game, we proceeded with the State Pattern, also to allow different terminals to have different GUIs.

#### The Pattern
"The **State Pattern** allows an object to alter its behavior when its internal state changes. The object will appear to change its class".

#### Implementation
- **`State<T>`**: Abstract base class that encapsulates a model, viewer, and controller (MVC architecture, as mentioned previously)
- **Concrete States**: `MenuState`, `GameState`, `ShopState`, `GameOverState`, `NewUserState`, `RemoveUserState`
- **Context**: The `Game` class holds the current `State<?>` and delegates `step()` calls to it

#### Consequences
- **Single Responsibility Principle (SRP)**: Each state encapsulates its own behavior and logic, preventing a "God Class" that handles all game states.
- **Open/Closed Principle (OCP)**: New states can be introduced without modifying the existing `Game` class or other states.
- **Simplicity**: Eliminates complex conditional logic (switch/case) in the main game loop related to state management.

### 2. Factory Method Pattern

#### Problem in Context
The game needs different GUI configurations for menus (larger font) and gameplay (smaller font for pixel graphics). Creating these directly would couple the application to specific implementation details.

#### The Pattern
"The **Factory Method** pattern defines an interface for creating objects, but lets subclasses decide which classes to instantiate".

#### Implementation
- **`GUIFactory`**: Interface defining `createMenuGUI()` and `createGameGUI()`
- **`LanternaGUIFactory`**: Concrete factory that creates `LanternaGUI` instances with appropriate font configurations


#### Consequences
- **Dependency Inversion Principle (DIP)**: The high-level `Game` class depends on the `GUIFactory` abstraction, not on the concrete `LanternaGUI` implementation.
- **Open/Closed Principle (OCP)**: Other GUI libraries can easily be implemented. 

---

### 3. Template Method Pattern

#### Problem in Context
All viewers need to follow the same drawing sequence (clear screen -> draw elements in the buffer -> refresh), but each viewer draws different elements. Duplicating this logic would generate lots of code, making it hard to maintain.

#### The Pattern
The **Template Method** defines the skeleton of an algorithm in a base class, letting subclasses override specific steps without changing the algorithm's structure.

#### Implementation
- **`Viewer<T>`**: Defines the template method `draw(GUI gui)` with the fixed sequence
- **`drawElements(GUI gui)`**: Abstract method that subclasses implement



#### Consequences
- **Code reuse**: Common algorithm defined once in base class
- **Consistency**: All viewers follow the same rendering lifecycle
- **Extensibility**: New viewers only implement `drawElements()`

---

### 4. Strategy Pattern

#### Problem in Context
Different lanes have different physics and collision handling, so it is determined at run-time which strategy to use.

#### The Pattern
"The **Strategy Pattern** defines a family of algorithms, encapsulates each one, and makes them interchangeable".

#### Implementation
- **`LaneController`**: Interface defining lane behavior (`update()`, `handleCollision()`, `isBlocked()`, `handlePhysics()`)
- **`BaseLaneController`**: Abstract base class with common functionality
- **Concrete Strategies**: `RoadLaneController`, `RiverController`, `SafeLaneController`

The `GameController` uses the appropriate controller based on lane type, allowing each lane to have its own update and collision logic.

#### Consequences
- **Separation of concerns**: Each lane type's logic is isolated
- **Open/Closed Principle**: New lane types can be added without modifying existing code

---

### 5. Composite Pattern
#### Problem in Context
The game has many related entities (Cars and Buses are Vehicles, Rivers and Roads are Lanes) that share common properties but have distinct behaviors. Without proper structuring, this leads to code duplication.

#### The Pattern
The **Composite Pattern** and inheritance hierarchies allow treating individual objects and compositions uniformly, while enabling shared behavior through base classes.

#### Implementation
**Lane Hierarchy:**
- `Lane` (abstract)
  - `RoadLane`
  - `River`
  - `SafeLane`

**Vehicle Hierarchy:**
- `Vehicle` (abstract)
  - `Car`
  - `Bus`

**Viewer Hierarchy:**
- `SpriteViewer<T>` (abstract, implements `ElementViewer<T>`)
  - `PlayerViewer`
  - `CarViewer`
  - `BusViewer`
  - `LogViewer`
  - `TreeViewer`
  - `CoinViewer`
  - `RoadViewer`
  - `RiverViewer`
  - `SafeLaneViewer`


#### Consequences
- **Code reuse**: Common attributes (position, size) defined in base classes
- **Polymorphism**; 
- **Extensibility**;

---

### 6. Update Method Pattern

#### Problem in Context
The game has many entities (player, vehicles, logs, camera) that need to update their state each frame. Without a unified approach, the game loop would need to know the specific update logic for each entity type, leading to tightly coupled and hard-to-maintain code.

#### The Pattern
"The **Update Method** pattern simulates a collection of independent objects by telling each to process one frame of behavior at a time".

#### Implementation
- **`GameController.update()`**: Calls `update()` on all entities

#### Consequences
- **Encapsulation**: Each entity manages its own per-frame behavior
- **Decoupling**: The game loop doesn't need to know entity-specific update logic
- **Open/Closed**: New entity types can easily be added

---

### 7. Flyweight Pattern

#### Problem in Context
Lanterna drawing isn't very efficient, and there are many sprites in the game, leading to a laggy gameplay.

#### The Pattern
"The **Flyweight Pattern** uses sharing to support large numbers of fine-grained objects efficiently". 

#### Implementation
- **`SpriteViewer.cache`**: A static `Map<String, GUIImage>` that stores loaded sprites by their file path
- **`getSprite(GUI gui, String path)`**: Checks the cache before loading; returns cached sprite if available
- **`LanternaGUI.colorCache`**: A `Map<String, TextColor>` that caches parsed colors

The sprite cache is shared across all viewer instances, ensuring each image is loaded only once regardless of how many entities use it, improving performance.

#### Consequences
- **Memory efficiency**: Sprites are loaded once and shared across all instances
- **Performance**: Eliminates redundant file I/O and image processing
---

## Known-code smells

Throughout the development of the game, lots of value were hardcoded, such as the tilesize, player speed, camera speed, spawn chances, lenghts of vehicles, etc. This is something that was achieved throughout testing and a feel for gameplay, and although are not the best practice, they allowed for a faster development process and a better feel for the player of the game.

Our Game.java class also has a lot of instanceof checks that determine whether the state is of game type or menu type. We know it is not the best practice, but was the only way we found during development window to allow not to recreate the GUI every time the state changes.

There are also some "god" methods spread throughout the code, such as input handling in various controllers, that spread dozens of lines inside a switch case, but we found no other way to handle the multiple possible inputs from user. Same for the ShopViewer drawElements method, which is also a "god" method. 

Input handling could have been implemented through the command pattern, but we found it overcomplicated for the goal. Also, its main benefits of "undoing" and "redoing" actions are not needed for the game.

There's also duplicate caches for the skins between shop and SpriteViewer, but we don't consider it important enough to justify adding the dependency between them, as currently the shop only has 4 skins, and at most one of them will be stored by the main game cache. 

We are also aware that the error handling is not the best, as we only catch IOExceptions and print the stack trace, but we don't really expect any other exceptions to be thrown, due to the testing.

NewUserController and NewUserViewer are also tightly coupled, and we are aware of this Code-Smell. We decided to accept this trade-off, as NewUserViewer is very simple, and the benefits of decoupling are not worth the added complexity, at least considering our specific use case. 
## Testing


### Screenshot of coverage report
<p align="center" justify="center">
  <img src="images/testCoverage/jacoco.png"/>
</p>
<p align="center">
  <b><i>Fig 12. Code coverage screenshot</i></b>
</p>


## Self-evaluation

One of the members almost did not participate in the development. In the first delivery, we looked aside as motivation was shown. No contact since. 
Therefore, we consider the rest of the work to have been like:

- João Barros: 49%
- Miguel Rocha: 49%
- Rosa Chilengue: 2%