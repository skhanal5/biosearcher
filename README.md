<p align="center">
  <img width="300px" src="https://github.com/skhanal5/biosearcher/assets/74752121/6467cad9-dfe3-4f86-b6c9-a70b708efdd9">
</p>

![language](https://img.shields.io/github/languages/top/skhanal5/biosearcher)
![license](https://img.shields.io/github/license/skhanal5/biosearcher)
![commit](https://img.shields.io/github/last-commit/skhanal5/biosearcher)
![issues](https://img.shields.io/github/issues/skhanal5/biosearcher)
![stars](https://img.shields.io/github/stars/skhanal5/biosearcher?style=social)

A program made with Instagram4J that allows users to go through any given Instagram profile and query through their
followers based on a search criteria dependent on their biography. 

# Disclaimer

Using this program for an extended period of time along with sending numerous requests in a short period of time can result in your account being rate limited or shadow banned. A workaround for this currently in the works.

# Bug Reports / Feature Requests

If you encounter any problems with the program, please make an issue with an appropriate title, label, and a detailed response [here](https://github.com/skhanal5/BioScraper/issues). Similarly, if you have any requests for additional features to be added onto this bot, please submit an issue with the label "feature-request" [here](https://github.com/skhanal5/BioScraper/issues) describing the feature you would like added. I will do my best to look over these reports and requests in my spare time.

# How to use:

## Step 1: Logging into the client

Upon opening the client, you will be prompted with the log-in screen where you will have to sign in using your Instagram credentials.

<p align="center">
<a href="https://gyazo.com/2736a011ffa4accc6922a2c242e1dee3"><img src="https://i.gyazo.com/2736a011ffa4accc6922a2c242e1dee3.gif" alt="Image from Gyazo" width="364"/></a>
</p>

## Step 2: Creating the search settings

After signing in successfully, you will be given a variety of options to set up your desired search. See the following image for more information:

<p align="center">
  <img src="https://user-images.githubusercontent.com/74752121/122689167-b4c28280-d1ee-11eb-9d86-63fdac3c40fb.png" />
</p>

From here, you MUST submit a valid Instagram handle, an input stream for the search keywords (or manually type these into the program), and a directory to export the data. The rest of the settings shown are optional. 

## Step 2.1: Specifying an input stream

If you choose to use an input stream, you must submit a text file (.txt extension) that contains all of the search words you would like to use for the query. In this text file, there must be only one keyword/phrase per line. Here is [an example](https://github.com/skhanal5/biosearcher/files/6683129/sample.txt) of what an acceptable input stream looks like:

<p align="center">
  <img src="https://user-images.githubusercontent.com/74752121/122689469-abd2b080-d1f0-11eb-971d-0a9189ba8d88.PNG" />
</p>

If you choose to use an input stream, you will NOT be allowed to specify your own keywords within the program. Please be mindful of this.

## Step 2.1 (Alternative): Manually entering keyword entries

If you choose to NOT use an input stream, you must enter the search keywords yourself into the program. When typing in the entries, make sure to input all keywords one at a time. There is no limit to the amount of entries you can enter but there is a character limit (limited to 15) per entry. Here is an example of how the program responds to valid entries:

<p align="center">
  <img src="https://i.gyazo.com/cbb2f2795f1b56cdd0bc101b093978aa.gif" />
</p>

If you choose to enter your own entries, you will NOT be allowed to specify an input stream in the program (an option to clear your keyword entries will be introduced in a later build). Please be mindful of this.

## Step 3: Starting the Search

After you have specified your desired settings, you can go ahead and click the "start search" button at the bottom of the screen. From there, if you followed the steps as intended, then you will proceed onto the "console log" screen where you can see the logs of the operation in real-time. Here is how it should appear:

<p align="center">
  <img src="https://i.gyazo.com/1f4f7f010adb7dfd98e7d83e01060085.gif" />
</p>

Once the search operation has completed, you will be greeted with a "success screen" where you can either leave the program or go back and start up another search. The program will have generated a Microsoft Excel Open XML Spreadsheet (.xlsx) file in the background containing your data wherever you specified the output to go to. It should appear like so:

<p align="center">
  <img src="https://user-images.githubusercontent.com/74752121/122689682-965e8600-d1f2-11eb-93dc-8e9fa5085c9c.png" />
</p>

## Built With
  * [Instagram4J](https://github.com/instagram4j/instagram4j) - Utilized wrapper to have access to Instagram Private API

## Acknowledgments

  * [COOL IT HELP](https://www.coolithelp.com/2020/06/javafx-redirect-console-output-to.html) - Used guides to build parts of program

## Author
* **Subodh Khanal**

## License

MIT License

Copyright (c) 2021 Subodh Khanal

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
