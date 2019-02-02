import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Player {

    private Board myboard;
    private boolean isOpponent;
    private List<Integer> playerBin;
    private List<Integer> opponentBin;
    private int successor;

    public int getMyStorage() {
        if(isOpponent)
            return myboard.getLowerBinStorage();
        else
            return myboard.getUpperBinStorage();
    }

    private int myStorage;

    public int getOpponentStorage() {
        if(isOpponent)
            return myboard.getUpperBinStorage();
        else
            return myboard.getLowerBinStorage();
    }

    private int opponentStorage;
    private boolean freeturn;

    public Player(Board myboard, boolean opponent) {
        this.successor = 0;
        this.myboard = myboard;
        this.isOpponent = opponent;
        if(isOpponent){
            playerBin = myboard.getUpperBin();
            opponentBin = myboard.getLowerBin();
            myStorage = myboard.getLowerBinStorage();
            opponentStorage = myboard.getUpperBinStorage();
        }
        else{
            playerBin = myboard.getLowerBin();
            opponentBin = myboard.getUpperBin();
            myStorage = myboard.getUpperBinStorage();
            opponentStorage = myboard.getLowerBinStorage();
        }

    }



    public void selectMove(int choice){
        if(choice == 1){
            // players selecting randomly
            Random random = new Random();
            makemove(random.nextInt(5));
        }
        else if(choice == 2){
            while(true){
                System.out.println("...MINMAX called...");
                MIN_MAX(myboard,2,true,-9999999,9999999); //TODO: Pass in a new reassigned board, not original one.
                boolean turn = makemove(successor);
                if(turn)
                    continue;
                else
                    break;
            }

        }
        else{
            // Human input
            myboard.printBoard();
            Scanner sc = new Scanner(System.in);
            System.out.print("Make your move : ");
            while (makemove(sc.nextInt()));
        }
    }

    public boolean makemove(int pos) {
        //System.out.println("DEBUG: Makemove called... "+pos);

        freeturn = false;
        if(playerBin.get(pos) == 0){
            System.out.println("Invalid move");
            return false;
        }
        if(isOpponent){
            myStorage = myboard.getLowerBinStorage();
            opponentStorage = myboard.getUpperBinStorage();
        }
        else{
            myStorage = myboard.getUpperBinStorage();
            opponentStorage = myboard.getLowerBinStorage();
        }

        int stones = playerBin.get(pos);
        playerBin.set(pos,0);
        int newPos = pos;
        List<Integer> curr_Bin = playerBin;
        for (int i = 0; i < stones; i++) {

            if(curr_Bin.equals(playerBin))
            {
                if(isOpponent)
                {
                    newPos--;
                    if(newPos == -1){
                        newPos = 0;myStorage++;i++;
                        curr_Bin = opponentBin;
                    }
                }
                else
                {
                    newPos++;
                    if(newPos == 6)
                    {
                        newPos = 5;myStorage++;i++;
                        curr_Bin = opponentBin;
                    }
                }
                if(curr_Bin.get(newPos) == 0 && i == stones -1){
                    //capture all stones from opponent
                    System.out.println("Captured stone ---------");
                    myStorage += opponentBin.get(newPos) + 1;
                    opponentBin.set(newPos , 0);
                    continue;
                }

            }
            else
            {
                if(isOpponent)
                {
                    newPos++;
                    if(newPos == 6)
                    {
                        newPos = 5;//skiping opponent storage
                        curr_Bin = playerBin;
                    }
                }
                else
                {
                    newPos--;
                    if(newPos == -1){
                        newPos = 0;//skipping opponent storage
                        curr_Bin = playerBin;
                    }
                }
            }

            if(i==stones)
            {
                //freeturn = true;
                System.out.println("Free Turn ------------------------");
                myboard.printBoard();
                //makemove(new Random().nextInt(5));
                return true;
                //break;
            }

            int val = curr_Bin.get(newPos);
            curr_Bin.set(newPos,val+1);

        }
        if(isOpponent){
            myboard.setLowerBinStorage(myStorage);
            myboard.setUpperBinStorage(opponentStorage);
        }
        else{
            myboard.setUpperBinStorage(myStorage);
            myboard.setLowerBinStorage(opponentStorage);
        }
        myboard.printBoard();

        return false;
    }

    public int MIN_MAX(Board tempBoard, int depth, boolean ismax, int alpha, int beta)
    {
        if(depth <= 0 || tempBoard.gameOver())
            return evaluate(tempBoard);

        Board backupBoard = new Board(tempBoard);

        int curr_value, best_value;

        boolean f = false;

        if(ismax)
        {
            best_value = -9999999;

            for(int i = 0 ; i < Board.getTotalBins(); i++)
            {
                //boolean turn = makemove(i);
                if(playerBin.get(i) != 0){

                    boolean turn = backupBoard.generateMove(i, isOpponent);

                    if(turn) //TODO: need to also assign the player here
                    {
                        curr_value = MIN_MAX(backupBoard,depth -1, true , alpha , beta);
                    }
                    else{
                        curr_value = MIN_MAX(backupBoard, depth - 1, false, alpha, beta);

                    }


                    if(curr_value > best_value) {
                        best_value = curr_value;
                        successor = i;
                    }
                    alpha = max(alpha, best_value);
                }

                if(beta <= alpha)
                {
                    f = true;
                    break;
                }
            }

            //set successor
//            if(depth == D)
//                __successor__ = successor;

        }
        else
        {
            best_value = 99999999;
            for(int i = 0; i < Board.getTotalBins(); i++)
            {
                if(opponentBin.get(i) != 0) {

                    boolean turn = backupBoard.generateMove(i, !isOpponent);// For Min, play as the opponent of oppenent ie plays as myself

                    if (turn) {
                        curr_value = MIN_MAX(backupBoard, depth - 1, false, alpha, beta);
                    } else {
                        curr_value = MIN_MAX(backupBoard, depth - 1, true, alpha, beta);

                    }

                    best_value = min(best_value, curr_value);
                    beta = min(beta, best_value);
                }
                if(beta <= alpha)
                {
                    break;
                }
            }
        }

        return best_value;
    }

    private int evaluate(Board tempBoard) {
        // heuristic implementations
        if(isOpponent)
            return tempBoard.getLowerBinStorage() - tempBoard.getUpperBinStorage();
        else
            return tempBoard.getUpperBinStorage() - tempBoard.getLowerBinStorage();
    }

}
