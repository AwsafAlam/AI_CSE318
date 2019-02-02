public class Mancala {

    public static void main(String[] args) {
        System.out.println("Starting Mancala....");

        Board b = new Board();
        b.printBoard();

        Player p1 = new Player(b,false);
        Player p2 = new Player(b,true);

        while (!b.gameOver()){
            System.out.println("\t\tPlayer 1 turn\n");
            p1.selectMove(2);
            System.out.println("\t\tPlayer 2 turn\n");
            p2.selectMove(2);
        }
        if(p1.getMyStorage() > p2.getMyStorage()){
            System.out.println("Player 1 is winner");
        }
        else{
            System.out.println("Player 2 is winner");
        }
        b.printBoard();
    }
}
