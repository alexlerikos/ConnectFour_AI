//alpha beta
///Alex Lerikos
import java.util.Random;
import java.util.*;
import java.awt.Point;
import java.lang.Math;
/**
*
 * @author Alex Lerikos
 */
public class alphabeta_ extends AIModule
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
		

		final Random r = new Random();
        int activeplayer = game.getActivePlayer();
        full_list();

     
       int depth = 8;
       Movenval alpha = new Movenval(0,-100000);
       Movenval beta = new Movenval(0,100000);
       int move = alphabeta(game, 0, depth, alpha, beta, true, activeplayer).move;

       //alpha first then beta

   

		chosenMove = move;


	
	}
//nodes are gamestates
//depth starts initially at 8

    private Movenval alphabeta(GameStateModule games, int elmoveo, int depth, Movenval alpha, Movenval beta, boolean bplayer, int player){
        //System.out.println("elmoveo: "+elmoveo);
        //System.out.println("how deep does it go? "+(depth));
        //System.out.println("bpplayer at terminate "+bplayer);
        if(depth == 0 || games.isGameOver() || terminate){
            if(games.isGameOver()){
                if(games.getWinner() == player){
                    Movenval rate = new Movenval(elmoveo, 1000000);
                    return rate;
                }
                else{
                    Movenval rate = new Movenval(elmoveo, -1000000);
                    return rate;

                }

            }
            


         

           // games.makeMove(elmoveo);
           Movenval rating = new Movenval(elmoveo,moverating(games,player));
           // for(int j = 6; j >= 0; j--){
           // for(int i = 0; i < 7; i++){
           //  System.out.print("row: "+ games.getAt(i,j)+ " ");
           // }
           // System.out.println();
           //  }
           //System.out.println("Rating for move " + rating.move+" is "+ rating.rating);
          // System.exit(0);
            //games.unMakeMove();
            return rating;
        }
        List<Integer> valids = new ArrayList<Integer>();
        valids =  validmoves(games);
        // for (int i:valids){
        //     System.out.println("valids(i): "+i);
        // }
         //System.exit(0);
        
        if(bplayer == true){
            for(int i: valids){

               // System.out.println("Second for loop i="+i);
                //int tempmove = i;
                games.makeMove(i);
                GameStateModule chil = games;
                //games.unMakeMove();
                 Movenval tempalphabeta = alphabeta(chil, i, --depth, alpha, beta, false, player);
                 games.unMakeMove();
                 //System.out.println("tempalphabeta move "+tempalphabeta.move+" rating "+tempalphabeta.rating);
                 //System.out.println("alpha.rating "+alpha.rating);
                 
                if( alpha.rating < Math.max(alpha.rating, tempalphabeta.rating)){
                    alpha = tempalphabeta;
                }
                //System.out.println("Alpha(move,rating) after max: "+ alpha.move+" "+alpha.rating);
                //System.exit(0);
                //alpha = Math.max(alpha, alphabeta(chil, i, depth - 1, alpha, beta, false, player).rating);
                if (beta.rating <= alpha.rating){
                    break; //that sheit like a mid 2000's eletro banger
                }
            }
            return alpha;
        }
        else{
            for (int i: valids){
                //int tempmove = i;
                games.makeMove(i);
                GameStateModule chil = games;
                //games.unMakeMove();
                Movenval tempalphabeta = alphabeta(chil, i, --depth, alpha, beta, true, player);
                games.unMakeMove();
                if( beta.rating > Math.min(beta.rating, tempalphabeta.rating)){
                    beta = tempalphabeta;
                }
                //beta = Math.min(beta, alphabeta(chil, i, depth - 1,alpha, beta, true, player).rating);
                if(beta.rating <= alpha.rating){
                    break; //this fucker like you broke my heart rita loudermilk. I hope you never furfill your dreams
                }
            }
            return beta;

        }

        // Movenval ret = new Movenval();
        // return ret;

    }

    private List<Integer> validmoves(GameStateModule gamin){
        List<Integer> possibilities = new ArrayList<Integer>();
            for(int i = 0; i < 4 ;i++){
                if(gamin.canMakeMove(3-i)){
                    possibilities.add(3-i);
                }
                if(gamin.canMakeMove(3+i)){
                    possibilities.add(i+3);

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
            p2pow = 3;
        }
        else{
            p1pow = 3;
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
                //System.out.print("templay: "+ (int)solutionsman.get(i).get(j).getX()+" "+(int)solutionsman.get(i).get(j).getY()+" | " );
                //check if first player and label player first
               
                 

                if( first_player == true && templay != 0){
                    initplayer = templay;
                    first_player = false;
                    coincount++;
                   //System.out.println("First player: "+initplayer);

                }
                else if(first_player == false && templay != initplayer && templay != 0){
                    playerwin = false;
                    //System.out.println("player win was false at "+i+""+j);

                }
                 else if(templay == initplayer && playerwin == true && templay != 0 && first_player == false){
                    coincount++;
                }
                
            }
           // System.out.println();
            if (playerwin == true){
                if(initplayer == 1){
                    if(coincount >= 3){
                        p1rating = p1rating + 1000000;
                    }
                    p1rating = p1rating + (int)Math.pow(coincount,p1pow);
                  //  System.out.println("Coincount was added for player 1 coin count = :"+coincount);
                }
                else if (initplayer == 2)
                {
                    if(coincount >= 3){
                        p2rating = p2rating + 1000000;
                    }
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
              // System.out.print((int)tempp.getX()+" "+(int)tempp.getY()+"  |  ");
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


