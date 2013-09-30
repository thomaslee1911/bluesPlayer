import java.awt.*; 
import java.applet.*; 
import java.awt.event.*;

//This applet translates textual information into sounds.

public class Saraswati extends Applet implements ActionListener
{
    Button actBut;
    TextArea in;
    TextArea out;
    
    //blues note factor
    int cons;
    //blues note factor 2
    boolean play;
    //root note factor
    int root;
    //rest factor
    int rest;
    
    //GUI layout------------------------
    public void init()
    {
        setLayout(new FlowLayout());
        actBut = new Button("Play");
        //input text box
        in = new TextArea("Type...", 10,40,TextArea.SCROLLBARS_VERTICAL_ONLY);
        //output text box
        out = new TextArea("",10,40,TextArea.SCROLLBARS_VERTICAL_ONLY);
        
        add(actBut);
        add(in);
        add(out);
        actBut.addActionListener(this);
        
        //init variables
        cons = 0;
        play=false;
        root =0;
        rest=0;
    }
    
    //action button-----------------------
    public void actionPerformed(ActionEvent evt)
    {
        String text = in.getText();
        String parsed = "";
        
        //traverses input text and picks out vowels
        for (int x=0; x<text.length(); x++)
        {
           String A = text.substring(x,x+1);
 
           //plays root if last note
           if (x==text.length()-1)
           note("a");
           
           //plays vowels as notes of pentatonic scale
           else if (A.equals("a")|| A.equals("e")|| A.equals("i")||A.equals("o")|| A.equals("u"))
            {
                //plays note
                note(A);
                //updates stuff
                parsed +=A;
                root++;
                rest++;
                
                //updats blues note factor 2
                if (A.equals("a")|| A.equals("e") || A.equals("u"))
                    play=false;        
                else play=true;
            }
            
            //rests if played enough
            else if (A.equals(" ")|| rest==5){
                    Wait.sec(0.25);
                    rest=0;}

            //plays root if played enough
            else if (A.equals(" "))
            {
                if (root==4){
                    note("a");
                    root=0;}
            }
            
            //plays blues note after _ consonants
            else if (cons==5 && play)
            {
               note("blues");
                cons=0;
            }
            
            else cons++;
        }
        out.setText(parsed);
     }
     
    //play note submethod------------------------------
    public void note(String note)
    {
        AudioClip C = getAudioClip(getDocumentBase(), "noteC.au");
        AudioClip D = getAudioClip(getDocumentBase(), "noteEb.au");
        AudioClip F = getAudioClip(getDocumentBase(), "noteF.au");
        AudioClip G = getAudioClip(getDocumentBase(), "noteG.au");
        AudioClip B = getAudioClip(getDocumentBase(), "noteBb.au");
        AudioClip Blues = getAudioClip(getDocumentBase(), "noteBlues.au");

        if(note.equals("blues") && play)
        {
            Blues.play();
            Wait.sec(0.25);}
        
       if(note.equals("a"))
        {C.play();
        Wait.sec(0.25);}
            
            if(note.equals("e"))
            {D.play();
            Wait.sec(0.25);}
            
            if(note.equals("i"))
            {F.play();
            Wait.sec(0.25);}
            
            if(note.equals("o"))
            {G.play();
            Wait.sec(0.25);}
            
            if(note.equals("u"))
            {B.play();
            Wait.sec(0.25);}
    }
        
   
 }