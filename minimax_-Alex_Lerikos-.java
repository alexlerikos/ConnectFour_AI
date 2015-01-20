//Minimax
///Alex  Lerikos's AI
import java.util.Random;
import java.util.*;
import java.awt.Point;
import java.lang.Math;
/**
 *
 * @author Alex Lerikos
 */
public class minimax_ extends AIModule
{
    public List<List<Point>> solutionsman = new ArrayList<List<Point>>(); 

    public class Movenval{
        public Movenval(int m, int r){
            move = m;
            rating = r;

        }
        public Movenval(){
            move = 0;
            rating = 0;
        }
        public int move; //column move value 
        public int rating;// rating detrmined by heuristic function;
    }

	@Override
	public void getNextMove(final GameStateModule game)
	{
		
        full_list();
		final Random r = new Random();
        int activeplayer = game.getActivePlayer();

     
       int depth = 5;
       int move = minimax(game, 0, depth, true, activeplayer).move;

       //alpha first then beta

   

		chosenMove = move;


	
	}
//nodes are gamestates
//depth starts initially at 8

    private Movenval minimax(GameStateModule games, int elmoveo, int depth, boolean bplayer, int player){
        if(depth == 0 || games.isGameOver() || terminate){
            
            //games.makeMove(elmoveo);
            Movenval rating = new Movenval(elmoveo,moverating(games,player));
            //games.unMakeMove();
            return rating;
        }
        List<Integer> valids = new ArrayList<Integer>();
        valids =  validmoves(games);
        //  for (int i:valids){
        //     System.out.println("valids(i): "+i);
        // }
        //  System.exit(0);
        Movenval best = new Movenval(0,0);
        if(bplayer == true){
             best.rating= -999999;
            for(int i: valids){
                //System.out.println("Second for loop");

               // int tempmove = i;
                games.makeMove(i);
                GameStateModule chil = games;
                //games.unMakeMove();
                 Movenval tempminimax = minimax(chil, i, --depth, false, player);
                 games.unMakeMove();
                if( best.rating < Math.max(best.rating, tempminimax.rating)){
                    best = tempminimax;
                }
            }
            return best;
        }
        else{
            best.rating = 999999;
            for (int i: valids){
                //int tempmove = i;
                games.makeMove(i);
                GameStateModule chil = games;
                
                Movenval tempminimax = minimax(chil, i, --depth, true, player);
                games.unMakeMove();
                if( best.rating > Math.min(best.rating, tempminimax.rating)){
                    best = tempminimax;
                }
          
            }
            return best;

        }

        // Movenval ret = new Movenval();
        // return ret;

    }

    private List<Integer> validmoves(GameStateModule gamin){
        List<Integer> possibilities = new ArrayList<Integer>();
            for(int i = 0; i < 6 ;i++){

                if(gamin.canMakeMove(i)){
                    possibilities.add(i);
                }


            }
            return possibilities;
    }


    private int moverating(GameStateModule cd, int player){

        int rating = 0;
        int p1rating = 0;
        int p2rating = 0;
        int p1pow;
        int p2pow;
        if(player == 1){
            p1pow = 2;
            p2pow = 5;
        }
        else{
            p1pow = 5;
            p2pow = 2;
        }

        if(cd.isGameOver()){
            if(cd.getWinner() == player){
                return 1000;
            }
            else 
                return -100000;

        }

        for (int i = 0; i < solutionsman.size(); i++){
            boolean playerwin = true;
            boolean first_player = true;
            int initplayer = 0;
            int coincount = 0;
            for(int j = 0; j < 4; j++){
                int templay = cd.getAt((int)solutionsman.get(i).get(j).getX(),(int)solutionsman.get(i).get(j).getY());

                //check if first player and label player first
               
                 if(templay == initplayer && playerwin == true && templay != 0 && first_player == false){
                    coincount++;
                    //System.out.println("coin was counted");
                }

                if( first_player == true && templay != 0){
                    initplayer = templay;
                    first_player = false;
                    coincount++;
                  //  System.out.println("First player: "+initplayer);

                }

                
                if(first_player == false && templay != initplayer && templay != 0){
                    playerwin = false;
                    //System.out.println("player win was false at "+i+""+j);

                }
                
            }
            if (playerwin == true){
                if(initplayer == 1){
                    p1rating = p1rating + (int)Math.pow(coincount,p1pow);
                  //  System.out.println("Coincount was added for player 1 coin count = :"+coincount);
                }
                else if (initplayer == 2)
                {
                    //System.out.println("Coincount was added for player 2 coin count = :"+coincount);
                    p2rating = p2rating + (int)Math.pow(coincount, p2pow);
                }
            }

        }

        if(player == 1){
            return ( p1rating - p2rating);
        }
        else
            return (p2rating - p1rating);
    }

   private void full_list(){
        //input row lists
        for(int k = 0; k < 6;k++){
        for(int i = 0; i < 4; i++){
            List<Point> temphit = new ArrayList<Point>();
            for(int j = 0; j < 4; j++){
                Point tempp = new Point(6-i-j,k);
                temphit.add(tempp);

            }
            solutionsman.add(temphit);
    
        }
        }

        //input column lists

        for(int k = 0; k < 7;k++){
        for(int i = 0; i < 3; i++){
            List<Point> temphit = new ArrayList<Point>();
            for(int j = 0; j < 4; j++){
                Point tempp = new Point(k,5 - i - j);
                temphit.add(tempp);

            }
            solutionsman.add(temphit);
    
        }
        }
        //input positive slope diagonal lists
        for(int k = 3; k  > 0;--k){
            //int end = 0;
            //int j = 3;
            //int i = 3;
            //int l = 2;
            int i = 3;
            
        //while(end < 4)
            for(int j = 0; j < 4; j++){
                // int i = 3;
                 int l = 2;
                int end = 0;
            List<Point> temphit = new ArrayList<Point>();
            while(end < 4){
                //negative slope with end++ and l-- and i--
                //Point tempp = new Point((6-end-i),(5 - l - k));
                Point tempp = new Point((6+end-i),(5 - l - k));
                temphit.add(tempp);
               // System.out.print((int)tempp.getX()+" "+(int)tempp.getY()+"  |  ");
                ++end;
                //--i;
                --l;
            }
            i++;
          //  System.out.println();
            solutionsman.add(temphit);
            }
        }
      //  System.out.println();

        //input negative slope diagonals
        for(int k = 3; k  > 0;--k){
            //int end = 0;
            //int j = 3;
            //int i = 3;
            //int l = 2;
            int i = 3;
            
        //while(end < 4)
            for(int j = 0; j < 4; j++){
                // int i = 3;
                 int l = 2;
                int end = 3;
            List<Point> temphit = new ArrayList<Point>();
            while(end >= 0){
                //negative slope with end++ and l-- and i--
                //Point tempp = new Point((6-end-i),(5 - l - k));
                Point tempp = new Point((6-end-i),(5 + l - k - 1));
                temphit.add(tempp);
             //  System.out.print((int)tempp.getX()+" "+(int)tempp.getY()+"  |  ");
                --end;
                //--i;
                --l;
            }
            i--;
            //System.out.println();
            solutionsman.add(temphit);
            }
        }
        //System.exit(0);
        

   }
}

