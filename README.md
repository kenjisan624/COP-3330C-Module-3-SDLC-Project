# COP-3330C-Module-3-SDLC-Project
This Repository will held documentation and Java files where contains the Local Library Patrons Data - LMS system. 


**Name:** Kenji Nakanishi 
**Course:** COP 3330C Object Oriented Programming 
**CRN:** 26824

**Description of the project: **

This console based app will allow librarians to add patrons data, load from a file stored in the local computer, remove patron, print the whole list, and exit.
Since it is very important to have interactive interface. The app displays messages informing the user where errors has been detected and prompt to fix them.

Also, a resource LocalLibraryPatronsDataSave.txt has been provided if librarian needs. 
** Highlight: They can provide them as well, just need to load to store the values in! 


<img src="Patrons/resources/Interface2.png" width="750" >

** An screenshot of the console based NavigationMenu **



Also includes a plan which have as introduction:
The current Software Development Plan (SDP) encapsulates the need for a software system - Library
Management System (LMS) - for our local community library librarian. This program will allow librarians
to manage current patron’s records efficiently. Each patron is composed of a unique 7-digit ID number
and first name, last name, home address, and overdue fee amount.


This console based app basically work with the below 3 files:
LibraryApp.java which contain the main method which will start the program

<img src="Patrons/resources/folder_icon.png" width="300" >

Patron.java which contains the formatting of how the user requested to be the input
example:
UniqueID-FirstName LastName-Addres-Overfue Fee
1234567-Kenji Nakanishi-12107 Bellsworth Way Orlando, FL 32837-200.00

<img src="Patrons/resources/folder_icon.png" width="300" >

NavigationMenu.java that holds most of the methods:
- AddingManually
- AddingFrmTxtFile
- RemovingByID
- DisplayList
- Exit
  These methods have been tested with a sample data stored under out/Patrons_jar/LocalLibraryDataSave.txt
  
<img src="Patrons/resources/folder_icon.png" width="300" >


The UML model is as follow:
Where LibraryApp creates NavigationMenu and this Uses & Stores Patron.

<img src="Patrons/resources/UML.png" width="750" >

**Tools used:**
- Java language
- IDE: JetBrain IntelliJ IDE

