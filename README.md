<h1 align="center">Game of Life</h1>
<h4 align="center">using Java <br>by Marek Krasiński</h4>
<h4 align="center"><i>A simulation of a world where different organisms live.</i></h4>
<div align="center">
  <img src="https://github.com/MroseQ/Game-of-Life-Java/assets/46853552/c4aeceec-2e68-4b06-b950-04b0f40f22e3/GameOfLife1" width="70%"><br>
</div>
<h2>Organisms</h2>
<h4><i>Organisms</i> are class objects that have parameters like: <i>strength, initiative</i>. For full parameters list check the end of the document.</h4>
<h4>Every <i>Organism</i> on every turn performs an <i>action()</i> method and a possible <i>collision()</i> method with another organism on the same cell. <be><i>Organisms</i> divide into <i>Animals</i> and <i>Plants:</i></h4>
<h3>Animal</h3> 

  * on <b>action()</b> moves one space from the current position. 
  * on <b>collision()</b> with an Animal of different species fights to determine who stays inside the cell, removing the loser from the simulation.
  * on <b>collision()</b> with an Animal of the same species they reproduce, adding a new organism of the same species next to two parents.
  * on <b>collision()</b> with a Plant - the Animal eats the Plant, removing it from the simulation

<h3>Plant</h3> 

  * on <b>action()</b> can reproduce (create the same class object) on a neighboring cell. 
  * <i> as it cannot move </i> <b>collision()</b> removes the Plant.

<h3 align="center"><b>Different organisms have their mechanics overrided!</b></h3>
<h5 align="center"><i>e.g. Guarana - adds 3 to the Animal that has eaten the Guarana</i></h5><br>

<h2>User Capabilities</h2>

  * can proceed to the next turn calling an <b>action()</b> method on every living organism. It also refreshes the UI.
  * can save the state of a world to a <i>.txt</i> file.
  * can load the state of a saved state stored in a <i>.txt</i> file.
  * can add an organism to the world <i>(by clicking on the empty cell)</i>.
  * can check the parameters of an organism <i>(by clicking on the occupied cell)</i>.
  * can change values inside <b>Settings.java</b> - Possible changes:
    + grid size
    + plants spawn chance
    + UI position values

  <div align="center">
  <img src="https://github.com/MroseQ/Game-of-Life-Java/assets/46853552/561eae6e-94d9-4220-b6ce-401767d80ff8" width="70%"><br>
  </div>

  <h2>UI Elements</h2>

  * <b>Content</b> - visualization of a world, the whereabouts of all organisms, and the turn indicator.
  * <b>Legend</b> - color connections with every species to differentiate organisms still living inside the world.
  * <b>Events</b> - notifications of actions performed by all organisms.
  * <b>Footer</b> - user control.
  
  <h2>Organism Parameters List</h2>

  * strength - integer value used in animal fights (higher strength wins)
  * initiative - integer value defining the order <i>Organisms</i> perform their turns.
  * isDead - boolean used to delete objects after the turn ends, not allowing an organism to perform their <i>action()</i> or <i>collision()</i>.
  * aliveSince - integer value defining since which turn this organism is living.
  * isAfterAction - some actions (e.g. animal reproducing) are blocking the organism from performing their actions on the next turn.
  * id - String value used to differentiate every <i>Organism</i>.
  * position - Position class object containing 2D vector with the exact position of the <i>Organism</i> in the world.
  * previousPosition - Position class object containing 2D vector with the position that the <i>Organism</i> had previously in the world.
  * color - Color value that is unique for every species.
  * stun - int value related to the Hedgehog class (after the Hedgehog dies in a fight, the winner is stunned for 2 turns and cannot perform their turns).
  
