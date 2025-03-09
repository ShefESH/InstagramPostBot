# InstagramPostBot
A bot for creating monday session instagram posts by Joshua Rylance (Publicity Officer 2024-25)

## Installation

This requires java 17 or higher to run (probably can run on older but 17 is what i have on my system and i know it works).

ChromeDriver is required for this.
Download - https://developer.chrome.com/docs/chromedriver/downloads  
Check which version of chrome you have - it is important!  
You can use other browser drivers - replace the chromedriver code with the browser driver of your choice  
As far as I know you can put the chromedriver files anywhere on your system. However, I put them in the same folder as the program for simplicity.  

Selenium is also required.
Download - https://www.selenium.dev/downloads/  
Pick the java installation.  
These files need to be at least next to the folder you place the program files. I'm not sure about if moving these files elsewhere is safe or not so consult the selenium guides.  


## Run instructions: - from the Instagram Bot directory containing all program files
### linux(also works in vscode terminals on windows):
```
sh ./runJar/run.sh 
```
or
```
cd ./runJar
sh run.sh
```

### windows - via powershell:
```
cd .\runJar\
run.bat
```

Supply the prompts with the session title and location. The short description is used for the text post generation. Time is assumed to be 6pm. Date is the next monday and is calculated internally.
The text generation file is called post.txt and is placed in app/build/libs  
It contains two versions of the text, one for discord and instagram, and one for email. 
You will need to go to app/src/java/instagram/post/creator and then edit the PUBOFF variable in the DescBot.java file, with your name. (you will need to rebuild the app for the change to take affect - see below)

## How it works:

### Build new version:
```
./gradlew shadowJar
```
This command runs the shadowJar Task. This replaces the current jar file with the updated one.
Jar file is found at app/build/libs

It uses Selenium to drive the website. Creates a session post based off the template in canva. 
The template has to be unlocked, other the bot will not work.
The template has to be at the top of the canva page.
The text inside the template is specific, the bot finds these textboxes based off the text inside - so don't change it!

Known Issues:
- Occasionally the design tab will open when running the bot. I am not sure why this happens every so often. Just re-run the bot and it will work.
- Occasionally the cookies will not be clicked so the bot will stop. Re run the bot and it will work. I believe this is due to the speed of the bot.
  Future publicity officers - you can change this if you like, but i prefer sheer speed :). 
I apologise for the lack of commits, I created this for personal use and then thought about oh I should put this on the github, and now here we are!

## Any other questions:
You can always send me a message on discord @CrowdedFire :)

