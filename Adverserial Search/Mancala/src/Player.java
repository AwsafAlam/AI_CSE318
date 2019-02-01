import java.util.List;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType.D;

public class Player {

    private Board myboard;
    private boolean isOpponent;
    private List<Integer> playerBin;
    private List<Integer> opponentBin;

    public int getMyStorage() {
        if(isOpponent)
            return myboard.getLowerBinStorage();
        else
            return myboard.getUpperBinStorage();
    }

    private int myStorage;
    private int opponentStorage;
    private boolean freeturn;

    public Player(Board myboard, boolean opponent) {
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



    public void selectMove(){

        // players selecting randomly
        Random random = new Random();
        makemove(random.nextInt(5));

        //int move = MIN_MAX(myboard,2,true,-9999999,9999999); //TODO: Pass in a new reassigned board, not original one.
        //makemove(move);
    }

    public void makemove(int pos) {
        freeturn = false;
        if(playerBin.get(pos) == 0){
            System.out.println("Invalid move");
            return;
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
                freeturn = true;
                System.out.println("Free Turn ------------------------");
                makemove(new Random().nextInt(5));
                break;
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
        //myboard.printBoard();
    }

    public int MIN_MAX(Board tempBoard, int depth, boolean ismax, int alpha, int beta)
    {
        if(depth <= 0 || myboard.gameOver())
            return evaluate(tempBoard);

        //time check
//        time_end = clock();
//        time_spent = (double)(time_end - time_begin) / CLOCKS_PER_SEC;

        //if(time_spent >= time_threshold && depth != D)return evaluate(tempGrid);

//        pp backupGrid[SZ][SZ];
//        for(int i = 0; i < SZ; i++)
//        {
//            for(int j = 0; j < SZ; j++)
//                backupGrid[i][j] = tempGrid[i][j];
//        }

        int curr_value, best_value;
        int successor;
        boolean f = false;

        if(ismax)
        {
            best_value = -9999999;

            for(int i = 0 ; i < Board.getTotalBins(); i++)
            {
                makemove(i); //TODO: need to also assign the plaer here

                curr_value = MIN_MAX(tempBoard, depth - 1, false, alpha, beta);

                if(curr_value > best_value) {
                    best_value = curr_value;
                    successor = i;
                }
                alpha = max(alpha, best_value);

//                for(int i2 = 0; i2 < SZ; i2++)
//                {
//                    for(int j2 = 0; j2 < SZ; j2++)
//                        tempGrid[i2][j2] = backupGrid[i2][j2];
//                }

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

                //update tempgrid
                makemove(i); // TODO: Need to add opponent player

                curr_value = MIN_MAX(tempBoard, depth - 1, true, alpha, beta);

                best_value = min(best_value, curr_value);
                beta = min(beta, best_value);

//                for(int i2 = 0; i2 < SZ; i2++)
//                {
//                    for(int j2 = 0; j2 < SZ; j2++)
//                        tempGrid[i2][j2] = backupGrid[i2][j2];
//                }

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
        return 1000;
    }

}
