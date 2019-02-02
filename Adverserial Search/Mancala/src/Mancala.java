public class Mancala {

    public static void main(String[] args) {
        System.out.println("Starting Mancala....");

        Board b = new Board();
        b.printBoard();

        Player p1 = new Player(b,false);
        Player p2 = new Player(b,true);

        int p1_win=0,p2_win=0;

        //for (int i = 0; i < 100; i++) {
            while (!b.gameOver()){
                System.out.println("\t\tPlayer 1 turn\n");
                p1.selectMove(2);
                System.out.println("\t\tPlayer 2 (Opponent) turn\n");
                p2.selectMove(2);
            }
            if(p1.getMyStorage() > p2.getMyStorage()){
                System.out.println("Player 1 is winner");p1_win++;
            }
            else{
                System.out.println("Player 2 (opponent) is winner");p2_win++;
            }
            b.printBoard();
        //}

        //System.out.println("Win ratio : p2/p1 = "+(p2_win/p1_win));
    }
}
