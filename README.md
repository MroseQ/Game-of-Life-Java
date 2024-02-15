<h1 align="center">Game of Life</h1>
<h5 align="center">using Java <br>by Marek Krasiński</h5>
<h4 align="center"><i>A simulation of a world where different organisms live.</i></h4>
<div align="center">
  <img src="https://github.com/MroseQ/Game-of-Life-Java/assets/46853552/c4aeceec-2e68-4b06-b950-04b0f40f22e3/GameOfLife1" width="70%"><br>
</div>
<h4>Every organism on every turn performs an <i>action()</i> method and a possible <i>collision()</i> method with another organism. <br>Organisms divide into <i>Animals</i> and <i>Plants:</i></h4>
<h2>Animal</h2> 

  * on action() moves one space from the current position. 
  * on collision() with an Animal fights to determine who stays inside the cell, removing the loser from the simulation. 
  * on collision() with a Plant - the Animal eats the Plant, removing it from the simulation

<h2>Plant</h2> 

  * on action() can reproduce (create the same class object) on a neighboring cell. 
  * <i> as it cannot move </i> collision() removes the Plant.

<h3 align="center"><b>Different organisms have their mechanics overrided!</b></h3><br>

<h2>User Capabilities</h2>

  * can proceed to the next turn calling an <b>action()</b> method on every living organism. It also refreshes the UI.
  * can save the state of a world to a <i>.txt</i> file.
  * can load the state of a saved state stored in a <i>.txt</i> file.
  * can add an organism to the world <i>(by clicking on the empty cell)</i>.
  * can check the parameters of an organism <i>(by clicking on the occupied cell)</i>.
  * can change values inside <b>Settings.java</b> - Possible changes:
    + grid size
    + plants spawn rate
    + UI position values

  <div align="center">
  <img src="https://github.com/MroseQ/Game-of-Life-Java/assets/46853552/561eae6e-94d9-4220-b6ce-401767d80ff8" width="70%"><br>
  </div>

  <h2>UI Elements</h2>

  *
  
