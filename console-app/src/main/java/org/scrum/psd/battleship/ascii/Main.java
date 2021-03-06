package org.scrum.psd.battleship.ascii;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static List<Ship> myFleet;
    private static List<Ship> enemyFleet;
    private static ColoredPrinter console;
    private static List<Position> myHits;
    private static List<String> myFleetPositions = new ArrayList<String>();
    
    
    //private static List<Letter> columnLetter = Arrays.asList(new Letter("A"),"B","C","D","E","F","G","H");

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        console = new ColoredPrinter.Builder(1, false).build();

		
        console.setForegroundColor(Ansi.FColor.MAGENTA);
        console.println("                                     $|__*");
        console.println("                                     |*|");
		console.println("                                     |\\/");
        console.println("                                     ---");
        console.println("                                     / | [");
        console.println("                              !      | |||");
        console.println("                            _/|     _/|-++'");
        console.println("                        +  +--|    |--|--|_ |-");
        console.println("                     { /|__|  |/\\__|  |--- |||__/");
        console.println("                    +---------------___[}-_===_.'____                 /\\");
        console.println("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _");
        console.println(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7");
        console.println("|                        Welcome to Battleship                         BB-62/");
        console.println(" \\_________________________________________________________________________|");
        console.println("");
        console.clear();

        InitializeGame();

        StartGame();
    }

    private static void StartGame() {
        Scanner scanner = new Scanner(System.in);

        console.print("\033[2J\033[;H");
        console.println("                  __");
        console.println("                 /  \\");
        console.println("           .-.  |    |");
        console.println("   *    _.-'  \\  \\__/");
        console.println("    \\.-'       \\");
        console.println("   /          _/");
        console.println("  |      _  /\" \"");
        console.println("  |     /_\'");
        console.println("   \\    \\_/");
        console.println("    \" \"\" \"\" \"\" \"");

        do {
            console.println("");
            System.out.println(ANSI_GREEN +"Player, it's your turn" + ANSI_RESET);
            System.out.println(ANSI_GREEN +"Enter coordinates for your shot :" + ANSI_RESET);
            Position position = null;
            try {
            position = parsePosition(scanner.next());
            } catch(Exception e) {
                console.println("This position is outside the playing field ");
                continue;
            }
            boolean isHit = GameController.checkIsHit(enemyFleet, position);
            if (position.getRow() < 1  || position.getRow() > 8 ) {
                console.println("This position is outside the playing field " + position.getRow());
                continue;
            }
			console.println("you shoot in " + position.getColumn() + position.getRow());

            if (isHit) {
                beep();

                
                System.out.println(ANSI_BLUE + "                \\         .  ./");
                System.out.println("              \\      .:\" \";'.:..\" \"   /");
                System.out.println("                  (M^^.^~~:.'\" \").");
                System.out.println("            -   (/  .    . . \\ \\)  -");
                System.out.println("               ((| :. ~ ^  :. .|))");
                System.out.println("            -   (\\- |  \\ /  |  /)  -");
                System.out.println("                 -\\  \\     /  /-");
                System.out.println("                   \\  \\   /  /"+ ANSI_RESET);
            }

            System.out.println(isHit ? ANSI_RED + "Yeah ! Nice hit !" : "Miss" + ANSI_RESET);

            
                    if (myHits == null) {
            myHits = new ArrayList<Position>(64);
        }

position = getRandomPosition();
myHits.add(position);

            isHit = GameController.checkIsHit(myFleet, position);
            console.println("");
            System.out.println(ANSI_YELLOW + String.format("Computer shoot in %s%s and %s", position.getColumn(), position.getRow(), isHit ? "hit your ship !" : "miss") + ANSI_RESET);
            if (isHit) {
                beep();
             
			    
                System.out.println(ANSI_BLUE + "                \\         .  ./");
                System.out.println("              \\      .:\" \";'.:..\" \"   /");
                System.out.println("                  (M^^.^~~:.'\" \").");
                System.out.println("            -   (/  .    . . \\ \\)  -");
                System.out.println("               ((| :. ~ ^  :. .|))");
                System.out.println("            -   (\\- |  \\ /  |  /)  -");
                System.out.println("                 -\\  \\     /  /-");
                System.out.println("                   \\  \\   /  /"+ ANSI_RESET);

            }
        } while (true);
    }

    private static void beep() {
        console.print("\007");
    }

    protected static Position parsePosition(String input) {
        Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1));
        return new Position(letter, number);
    }

    private static Position getRandomPosition() {
        int rows = 8;
        int lines = 8;
        Random random = new Random();
        Letter letter = Letter.values()[random.nextInt(lines)];
        int number = random.nextInt(rows);
        Position position = new Position(letter, number);

        while(myHits.contains(position)) {
    position = getRandomPosition();
        }
        

        return position;
    }

    private static Boolean checkPositionLetter(Position pos) {
        for(Letter s : Letter.values()) {
            if(s.equals(pos.getColumn())) {
                return true;
            }
        }
        return false;
    }

    private static void InitializeGame() {
        InitializeMyFleet();

        InitializeEnemyFleet();
    }

    private static void InitializeMyFleet() {
        Scanner scanner = new Scanner(System.in);
        myFleetPositions = new ArrayList<String>();
        myFleet = GameController.initializeShips();

        console.println("Please position your fleet (Game board has size from A to H and 1 to 8) :");

        for (Ship ship : myFleet) {
            console.println("");
            console.println(String.format("Please enter the positions for the %s (size: %s)", ship.getName(), ship.getSize()));
            for (int i = 1; i <= ship.getSize(); i++) {
                console.println(String.format("Enter position %s of %s (i.e A3):", i, ship.getSize()));
                String positionInput = scanner.next();

                if (myFleetPositions.contains(positionInput)) {
                    console.println("Ships must notoverlap each other, please input the fleet again");
                    InitializeMyFleet();
                }
                else
                {
                    ship.addPosition(positionInput);
                    myFleetPositions.add(positionInput);
                }               
            }
        }
    }

    private static void InitializeEnemyFleet() {
        enemyFleet = GameController.initializeShips();

        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 4));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 5));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 6));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 7));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 8));

        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 6));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 7));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 8));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 9));

        enemyFleet.get(2).getPositions().add(new Position(Letter.A, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.B, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.C, 3));

        enemyFleet.get(3).getPositions().add(new Position(Letter.F, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.G, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.H, 8));

        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 5));
        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 6));
    }
}
