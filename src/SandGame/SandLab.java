package SandGame;
import java.awt.*;
import java.util.*;

public class SandLab
{
  //Step 4,6
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int WATER = 2;
  public static final int DIRT = 3;
  
  //do not add any more fields below
  private int[][] grid;
  private SandDisplay display;
  
  
  /**
   * Constructor for SandLab
   * @param numRows The number of rows to start with
   * @param numCols The number of columns to start with;
   */
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    // Change this value to add more buttons
    //Step 4,6
    names = new String[4];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[WATER] = "Liquid Oxygen";
    names[DIRT] = "Dirt";
    
    //1. Add code to initialize the data member grid with same dimensions
    grid = new int [numRows][numCols];
    
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //2. Assign the values associated with the parameters to the grid
   grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
      //Step 3
   //Hint - use a nested for loop
	 for(int i = 0; i < grid.length; i++) {
		 for (int j = 0; j < grid[0].length; j++) {
			if(grid[i][j] == METAL) {
				
				display.setColor(i, j, Color.GRAY);
				
			}else if(grid[i][j] == EMPTY) {
				
				display.setColor(i,j,Color.RED);
				
			}else if(grid[i][j] == WATER) {
				
				display.setColor(i,j,Color.BLUE);
			
			}else if (grid[i][j] == DIRT) {
			
				display.setColor(i,j,Color.BLACK);
			
			}else {
			
				display.setColor(i, j, Color.RED);
			
			}
		 }
	 }
  }

  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void step()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    int someRandomRow = (int) (Math.random() * (grid.length));
    int someRandomCol = (int) (Math.random() * (grid[0].length));
    int randomChoice = (int) (Math.random() * 3);
    //remember that you need to watch for the edges of the array
    if(grid[someRandomRow][someRandomCol] == WATER) {
    	int nContain;
    	if((someRandomRow + 1 < grid.length) && (grid[someRandomRow + 1][someRandomCol] == EMPTY
    									  && grid[someRandomRow + 1][someRandomCol] != DIRT
										  && grid[someRandomRow + 1][someRandomCol] != METAL)){
    		nContain = grid[someRandomRow + 1][someRandomCol];
    		grid[someRandomRow][someRandomCol] = nContain;
    		grid[someRandomRow + 1][someRandomCol] = WATER;
    	}else if((someRandomCol - 1 >= 0) && (grid[someRandomRow][someRandomCol - 1] == EMPTY
    													&& grid[someRandomRow][someRandomCol - 1] != DIRT
    													&& grid[someRandomRow][someRandomCol - 1] != METAL)) {
    		nContain = grid[someRandomRow][someRandomCol - 1];
    		grid[someRandomRow][someRandomCol] = nContain;
    		grid[someRandomRow][someRandomCol] = WATER;
    		
    	}
    }
    	
    if(grid[someRandomRow][someRandomCol] == DIRT) {
    	int temp;
    	if((someRandomRow + 1 < grid.length) && (grid[someRandomRow + 1][someRandomCol] == EMPTY
				  									|| grid[someRandomRow + 1][someRandomCol] == WATER)){
    				temp = grid[someRandomRow + 1][someRandomCol];
    				grid[someRandomRow][someRandomCol] = temp;
    				grid[someRandomRow + 1][someRandomCol] = DIRT;
    	}else if((someRandomCol - 1 >= 0) && (grid[someRandomRow][someRandomCol - 1] == EMPTY
																&& grid[someRandomRow][someRandomCol - 1] != WATER)) {
    			temp = grid[someRandomRow][someRandomCol - 1];
    			grid[someRandomRow][someRandomCol] = temp;
    			grid[someRandomRow][someRandomCol - 1] = DIRT;
    	}else {
    		
    	}
    }
 }
  
  //do not modify this method!
  public void run()
  {
    while (true) // infinite loop
    {
      for (int i = 0; i < display.getSpeed(); i++)
      {
        step();
      }
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
      {
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
      }
    }
  }
}
