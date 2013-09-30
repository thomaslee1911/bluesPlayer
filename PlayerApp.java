// 2012 Thomas Lee
// This class extends BluesPlayer to send blues melody MusicString to JFugue

import org.jfugue.*;
import java.util.Random;
import java.lang.Integer;
import java.lang.Double;

public class PlayerApp extends BluesPlayer
{    
    public static String melody;
    static double noteTable[][] = new double[7][7];
    static double rhythmTable[][] = new double[6][6];
    
    //plays a sequence of notes on the blues scale in a swing rhythm
    public static void play()
    {
        Player player = new Player();
        Random rand = new Random();
        
        melody = "";
        int first = rand.nextInt(7); // random seed note
        for (int i = 0; i<100; i++)
        {
            int second = improvise(first); // get next note based on note table
            melody += second;
            first = second;
        }        
        Pattern pattern = new Pattern(BluesPlayer.Swing(melody));
        player.play(pattern);
        //System.exit(0);
    }
    
    //takes the weights from the prob table and decides the next note
    //accordingly to curent note
    static int improvise(int i)
    {        
        Random rand = new Random();
        double p = rand.nextDouble();
        
        //returns a note value according to the weights
        double pSum = 0;
        for (int x=0; x<=6; x++)
        {
            pSum += noteTable[i][x];
            if (p <= pSum)
            return x;
        }
        return -1;
    }
    
    //verifies improvise probability calculating function
    static void test1() 
    {
        int sample = 10000;
        double probs[] = new double[7];
        for (int i=0; i<sample; i++)
            probs[improvise(0)] += 1;
        for (int i=0; i<7; i++)
        System.out.println(probs[i]/sample);
    }
    
    //generates a prob table based on sequences of note values
    static void learnPatterns(String[] rep)
    {
        int sum[] = new int[7]; //counter
        //initialize noteTable
        for (int x=0; x<7; x++)
            for (int y=0; y<7; y++)
                noteTable[x][y] = 0.0;
        //add to sum for each note table node according to sequence
        for (int i=0; i<rep.length; i++)
        {
            String seq = rep[i];
            for (int n=0; n<rep[i].length()-1; n++)
            {
                int currentNote = (int)Double.parseDouble(seq.substring(n,n+1));
                int nextNote = (int)Double.parseDouble(seq.substring(n+1,n+2));
                noteTable[currentNote][nextNote] += 1.0;
                sum[currentNote]++;
            }
        }
        //divide by number of instances to get probability [0,1]
        for (int x=0; x<7; x++)
            for (int y=0; y<7; y++)
            if (sum[x]!=0.0)
            noteTable[x][y] /= sum[x];
        //return noteTable;
    }
    
    //tests learnPatterns method
    static void fullTest()
    {
        String rep1[] = new String[2];
        rep1[0]= "012345600112244134110056130410056661123401024";
        rep1[1]= "";
        learnPatterns(rep1);
        for (int x=0; x<7; x++)
        {
            System.out.println();
            for (int y=0; y<7; y++)
                System.out.print(noteTable[x][y]+" ");
        }       
        play();
    }
    
    //===============================================================
    // RHYTHM SECTION
    //===============================================================
    static void learnRhythms(String[] rep)
    {
        int sum[] = new int[6]; //counter
        //initialize noteTable
        for (int x=0; x<6; x++)
            for (int y=0; y<6; y++)
                rhythmTable[x][y] = 0.0;
        //add to sum for each note table node according to sequence
        for (int i=0; i<rep.length; i++)
        {
            String seq = rep[i];
            for (int n=0; n<rep[i].length()-1; n++)
            {
                int currentNote = (int)Double.parseDouble(seq.substring(n,n+1));
                int nextNote = (int)Double.parseDouble(seq.substring(n+1,n+2));
                rhythmTable[currentNote][nextNote] += 1.0;
                sum[currentNote]++;
            }
        }
        //divide by number of instances to get probability [0,1]
        for (int x=0; x<6; x++)
            for (int y=0; y<6; y++)
            if (sum[x]!=0.0)
            rhythmTable[x][y] /= sum[x];
    }
    static int improviseRhythm(int i)
    {
        Random rand = new Random();
        double p = rand.nextDouble();
        
        //returns a note value according to the weights
        double pSum = 0;
        for (int x=0; x<=6; x++)
        {
            pSum += noteTable[i][x];
            if (p <= pSum)
            return x;
        }
        return -1;
    }
    static void testRhythm()
    {
        String rep1[] = new String[1];
        rep1[0]= "01010101020501010101250101010102030101010102510122103010101012";
        learnRhythms(rep1);
        
        Player player = new Player();
        Random rand = new Random();
        melody = "";
        int first = rand.nextInt(6); // random seed note
        int second;
        for (int i = 0; i<100; i++)
        {
            if(first != -1)
            second = improviseRhythm(first); // get next note based on note table
            else
            second = rand.nextInt(6);
            melody += second;
            first = second;
        }        
        Pattern pattern = new Pattern(BluesPlayer.Rhythms(melody));
        player.play(pattern);
    }
}