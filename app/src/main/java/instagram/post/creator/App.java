/**
 * Author: Joshua Rylance
 * Desc: A Bot to create routine session instagram post via the canva tool
 * 
 */
package instagram.post.creator;

import org.openqa.selenium.*;
import java.time.*;
import java.util.Date;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;

import org.openqa.selenium.chrome.ChromeDriver;

public class App {

    WebDriver driver;
    WebElement colourB = null;
    String info;
    String date;
    String location;
    String desc;

    public App(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Title of Session> ");
        info = sc.nextLine();
        System.out.print("Enter Location of Session> ");
        location = sc.nextLine();
        System.out.print("Enter Short Description of session> ");
        desc = sc.nextLine();
        sc.close();
        date = getDate();
        new DescBot(info, date, location, desc);
        bot(info, location);
    }

    public void bot(String info, String location){

        //sets up driver to operate chrome
        driver = new ChromeDriver();
        driver.get("https://www.canva.com/design/DAFyX_FVztw/ZCgJwJCD4Un-ySCXx0goBg/edit");
        driver.manage().window().maximize();

        cookieClicker();

        //duplicates page
        //uses cssSelector as more efficent than xpath
        WebElement dPage = driver.findElement(By.cssSelector("button[aria-label='Duplicate page']"));
        dPage.click();

        

        sleep(500);

        //adds text to elements that match the text inside template
        //[2] selects the second instance of the xpath - second page in canva
        addText(driver, "(//span[text()='Date'])[2]", date);
        addText(driver, "(//span[text()='Info text'])[2]", "Join us for " + info + "!");

        WebElement locationText = findClear(driver, "(//span[text()='Location'])[2]");
        addColouredText(locationText, "blue", "Location: ", true);
        addColouredText(locationText, "white", (location + "\n"), false);
        addColouredText(locationText, "blue", "Time: ", false);
        addColouredText(locationText, "white", "6PM", false);
        colourB.click();

    }


    //wrapper for pausing the bot
    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //haha fun game - dont worry rejects all :)
    private void cookieClicker(){
        //checks if cookies is displayed -- popup is inconsistent
        try{
            WebElement cookieClicker = findEle("//span[text()='Reject all cookies']");
            cookieClicker.click();
        } catch (NoSuchElementException e){
            //pop up doesnt exist
            System.out.println("No cookies, no worries");
        }
    }

    //automatically set the correct date
    private String getDate(){
        Date date = new Date();
        //grabs the current date and adjusts it to the next monday
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
		String[] localDateSplit = localDate.toString().split("-");
        String day = localDateSplit[2];
        String month = localDate.getMonth().toString();
        
        //removes leading zero
        if (day.charAt(0) == '0'){
            day = String.valueOf(day.charAt(1));
        }


        //adds the 'th', 'rd' and 'nd' suffixes to the date
        switch(day){
            case "4","5","6","7","8","9","10","11","12"
                ,"13","14","15","16","17","18","19","20"
                ,"24","25","26","27","28","29","30","31":

                day = day + "th";
                break;
            case "2","22":
                day = day + "nd";
                break;
            case "1","21":
                day = day + "st";
                break;
            case "3","23":
                day = day + "rd";
                break;
            default:
                System.out.println("Error - Missing case for " + day);
                break;
        }

        //makes month lowercase except first letter
        month = month.charAt(0) + month.substring(1).toLowerCase();
        String dateS = day + " " + month;

        return dateS;
    }

    //picks the colour from the colour menu
    private void setColour(String colour, WebElement e){
        
        //finds buttons that match the colours
        //change this if you change the colours used
        if (colour.equals("blue")){
            WebElement bluePress = driver.findElement(By.cssSelector("button[aria-label='#09cece']"));
            bluePress.click();
        } else if (colour.equals("white")){
            WebElement whitePress = driver.findElement(By.cssSelector("button[aria-label='#ffffff']"));
            whitePress.click();
        } else {
            System.out.println("Error: Invalid Colour - find colour code in aria label \n");
            System.out.println("Add an extra 'else if' with colour you want");
        }
    }

    //finds and clears an elements then adds text to it
    private void addText(WebDriver driver, String findString, String text){
        WebElement textElement = findClear(driver, findString);
        textElement.sendKeys(text);
    }

    //finds an element
    private WebElement findEle(String findString){
        WebElement textElement = driver.findElement(By.xpath(findString));
        return textElement;
    }

    //finds and clears an element by double clicking and then pressing back space
    //needs to switch to the focussed element as when you edit a element on canva, a new identical element is created
    private WebElement findClear(WebDriver driver, String findString){
        WebElement textElement = findEle(findString);
        textElement.click();
        textElement.click();
        textElement = driver.switchTo().activeElement();
        textElement.sendKeys(Keys.BACK_SPACE);
        return textElement;
    }

    //adds coloured text 
    private void addColouredText(WebElement textElement, String colour, String text, boolean firstTime){
        //double clicks element, then opens the colour menu
        if (firstTime){
            textElement.click();
            textElement.click();
            colourB = driver.findElement(By.cssSelector("button[aria-label='Text color']"));
            colourB.click();
            sleep(250);
        }

        setColour(colour, textElement);
        textElement.sendKeys(text);
    }

    public static void main(String[] args) {
        new App();
    }
}