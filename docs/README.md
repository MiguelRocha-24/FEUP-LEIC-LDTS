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

Following the given recommendations, the project will follow an MVC architecture, which divided the game in three parts, which store the logic and data (model), the visual effects (view) and the control of the game (controller).

#### The Pattern:
As described before, regarding the "Architectural Pattern", the project will follow the Model-View-Controller style. This ensures the concerns are separate: 
- Model: stores the logic and data. Completely independent of user interface.
- View: Handles visalization. Reads from model and draws to screen using implemented GUI.
- Controller: Processes input and dictates the flow of the game.

Our project will also have a "State" pattern, which will allow us to separate the main menu (with options to start gameplay, open shop, switch "skin" and switcv user) from the actual Gameplay state.

#### Implementation:
Our specific implementation will have (we hope) the view separated in 2 main packages. One (the GUI) which will dictate how objects should be drawn, and the other (LanternaGUI) which will hold the logic for the specific GUI.
#### Consequences:
Using these design patterns has the following benefits:
- Ease of implementation of new features.
- Clear separation of concerns.
- Ease of changing the GUI interface. 







## Known-code smells

Currently view isnt properly implemented / separated from the model. We are aware of this and plan to fix it in the near future. 
Lane also is wrongly implemented, currently just built to allow for the game to run. It will be split into multiple classes in the near future.

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

The work was divided in a way that felt fair to every member.
Therefore:
Rosa Chilengue: 33.3%
João Barros: 33.3%
Miguel Rocha: 33.3%