Team-4
======

Initial version of Bomberman

The prototype as of November 2nd, 2014.

- Grid is composed of concrete blocks and brick walls.
- Brick wall locations are randomly decided.
- Game can be paused by pressing the space bar.
- Player can move using the arrow keys. Camera will follow if at the center of the screen.
- Bombs can be placed by pressing the x key (preliminary prototype).

The prototype as of November 10th, 2014.

- Bombs can be placed. They explode and demolish nearby walls.
- Collision has been updated to include bombs.
- All powerups have been implemented, including the detonator.
- Player controls have been tweaked for the best user experience.
- A Sqlite database has been setup for the menu systems.
    - The login systems is fully implemented.
    - The account creation system is fully implemented.

Note: In order for the database system to run, you will have to add the sqlite-jdbc.jar file to the classpath of the application
      This was not done because it depends on which IDE you may be using, professor.