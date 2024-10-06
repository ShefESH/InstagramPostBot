package instagram.post.creator;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

import instagram.post.creator.Starters.Starter;

public class DescBot{

    private String title;
    private String date;
    private String location;
    private String desc;
    private final String PUBOFF = "Josh";
    private final String newLine = System.lineSeparator();

    public DescBot(String title, String date, String location, String desc){
        this.title = title;
        this.date = date;
        this.location = location;
        this.desc = desc;


        String disGram = genText(Starter.DISGRAM);
        String email = genText(Starter.EMAIL);

        String doubleNL = newLine + newLine;
        String fullFile = "Discord/Instagram" + doubleNL + disGram + doubleNL +
        "Email" + doubleNL + email;

        genFile(fullFile);
        
    }

    private String genText(Starter starter){
        String starting = "";
        String emailSendOff = "";
        String descFS;
        String an;
        String the;

        descFS = checkDescFS();
        an = checkAn();
        the = checkThe();

        
        if (starter.equals(Starter.EMAIL)){
            starting = Starter.EMAIL.toString();
            emailSendOff = newLine + newLine + PUBOFF + " -- Publicity Officer"; 
        } else {
            starting = Starter.DISGRAM.toString();
        }
        String post = starting + "Ethical Hackers! Next session is " + an + title + ". "
         + desc + descFS + " Join us in " + the + location + " at 6pm, on Monday "+ date + "." + newLine + "We look forward to seeing all of you!"
         + emailSendOff;

        return post;
    }

    //checks if a full stop is at the end of the description, if not adds one in
    private String checkDescFS(){
        String descFS;
        if (desc.charAt(desc.length()-1) == '.'){
            descFS = "";
        } else {
            descFS = ".";
        }
        return descFS;
    }

    //check if a an is required before the title
    //this is only done for letters, not for vowel sounds
    //if using this, put an 'an' when you think you need to. This is a basic check
    private String checkAn(){
        String an = "";
        String[] titleSplit = title.split(" ");
        String start = titleSplit[0];
        //stops a double an situation
        if (!start.toLowerCase().equals("an")){
            switch(start.toLowerCase().charAt(0)){
                case 'a','e','i','o','u':
                    an = "an ";
                    break;
                default:
                    break;
            }
        }
        return an;
    }

    //This is a harder check, I prefer putting the before Diamond, but please add to this if you have other location titles
    private String checkThe(){
        String the = "";
        String[] locationSplit = location.split(" ");
        String start = locationSplit[0];
        switch(start.toLowerCase()){
            case "the":
                break;
            case "diamond":
                the = "the ";
                break;
            default:
                break;
        }
        return the;
    }

    private void genFile(String fullFile){
        String path = "post.txt";
        //checks if file is already created, if not creates one
        try{
            File file = new File(path);
            if (file.isFile()){
                writeFile(path, fullFile);
            } else {
                file.createNewFile();
                writeFile(path, fullFile);
            }
        }catch (IOException e){
            System.out.println("Error: Unable to create file");
            e.printStackTrace();
        }
    }

    private void writeFile(String file, String fullFile){
        try{
            FileWriter writer = new FileWriter(file);
            writer.write(fullFile);
            writer.close();
            System.out.println("Created post.txt file");
        } catch (IOException e){
            System.out.println("Error: Unable to write to file");
            e.printStackTrace();
        }

    }

    

    
    /* Only used for testing - is normally called via App.java
    public static void main(String[] args){
        new DescBot("Intro to Linux", "7th October", "Diamond Workroom 3", "We will cover basic commands and navigation of the terminal. This is particularly of interest to anyone doing a Computer Science degree as it is a great boost to your course. As ever, anyone is welcome and we will teach you everything you need to know");
    }
    */
    
    
}
