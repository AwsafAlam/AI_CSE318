public class Mancala {

    public static void main(String[] args) {
        System.out.println("Starting Mancala....");

        Board b = new Board();
        b.printBoard();

        Player p1 = new Player(b,false);
        Player p2 = new Player(b,true);

        while (!b.gameOver()){
            //for (int i = 0; i < 13; i++) {
            //System.out.println("Player 1: turn");
            p1.selectMove();
            //System.out.println("Player 2: turn");
            p2.selectMove();
            //b.printBoard();
        }
        if(p1.getMyStorage() > p2.getMyStorage()){
            System.out.println("Player 1 is winner");
        }
        else{
            System.out.println("Player 2 is winner");
        }
    }
}
