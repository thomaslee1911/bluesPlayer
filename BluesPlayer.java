// 2012 Thomas Lee
// This class converts numerical blues notation to JFugue MusicString with swing rhythm.
// There is also added feature of back-accent articulation for more realistic blues playing.

public class BluesPlayer
{
static String Rhythms(String str)
{
    String out = "T[150] ";
    for (int i=0; i<str.length(); i++)
    {
        String n = str.substring(i,i+1); //current note
        if (n.equals("0")) out+="G3i";
        if (n.equals("1")) out+="G3ii";
        if (n.equals("2")) out+="G3iii";
        if (n.equals("3")) out+="Ri";
        if (n.equals("4")) out+="Ri";
        if (n.equals("5")) out+="Ri";
        out += " ";
    }
    return out;
}

static String Swing(String str) //creates MusicString with shuffle beat 
{
    String out = "T[150] ";
    boolean downbeat = true;
    for (int i=0; i<str.length(); i++)
    {
        out += Blues(str.substring(i,i+1));
        out += "i";
        if (downbeat)
        {
            out += "i"; //downbeat note is longer
            out += "a127"; //emulates back-accent
        }
        else
            out += "a100";
        downbeat = !downbeat;
        //ADD: change attack and decay velocity to reflect back-accent tongue
        out += "d127";
        out += " ";
    }
    return out;
}   
    
static String Blues(String str)
{
    if (str.equals("0")) return "G3";
    if (str.equals("1")) return "Bb3";
    if (str.equals("2")) return "C4";
    if (str.equals("3")) return "C#4";
    if (str.equals("4")) return "D4";
    if (str.equals("5")) return "F4";
    if (str.equals("6")) return "G4";
    else return "";
}

String Blues(int r, String str) //root note r; string of blues scale numbers
{
    //generate blues scale numerically from intervals
    return "";
}
}