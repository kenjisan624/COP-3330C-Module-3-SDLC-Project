// NavigationMenu will include all the needed Methods from path to file, display patron, add patron
// remove by ID of the member, List and display them, finally exit which will save the patron data on the selected path.
// The program works on user input demand, meaning if the user select their own txt file, it will base on their patron data.
// I also have included a resource file with LocalLibraryPatronDataSave.txt where I have previously stored some patrons for user testing
// The program will save, update, and display the patron data whenever add, remove, and before exit the program.

import java.nio.file.*; // For paths and file reading
import java.util.*; // Wildcard including Collections, List, Arrays,
import java.io.*; // Including BufferedReader, IOException

public class NavigationMenu {
    private Path ChooseTxtFile;
    private final HashMap<String, Patron> patrons = new HashMap<String,Patron>();
    private final Scanner in = new Scanner(System.in);

    public void start() {
        //It will print only 1 time to the user that a resource of sample patron data has been included and the
        //librarian can just copy the path to access the resource or type their own absolute path.
        System.out.println("""
              *****************************************
                      Local Library Patrons Data
              *****************************************
            |You can also select [2] and copy path:     |
            |resources/LocalLibraryPatronsDataSave.txt  |
            |where a sample data is stored!             |
            |Again, No needed if you have your own file!|""");


        // While loop with case accessing each method:
        // I have added some functionalities inside the methods to make the console app
        // A little more interactive.
        while (true) {
            DisplayNavigation();
            String selection = in.nextLine().trim();
            switch (selection) {
                case "1" -> AddingManually();
                case "2" -> AddingFromTxtFile();
                case "3" -> RemovingByID();
                case "4" -> DisplayList();
                case "5" -> {
                    Save();
                    System.out.println("Exiting Local Library LMS Patrons Data Program... Good Bye!!!");
                    return;
                }
                default -> System.out.println("Error: Invalid input...");
            }
            System.out.println();
        }
    }
    //Navigation Menu is displayed using [#] to easily show user that the numbers are going to be used for selection
    public void DisplayNavigation() {
        System.out.println("""
            
              *****************************************
                    Local Library Patrons Data
              *****************************************

            [1] Add patron manually      [4] List all patrons
            [2] Load patrons from file   [5] Exit program
            [3] Remove patron by ID

            Selection:  """);
    }

    //Methods

    // AddingManually, it allows user to add the patron by typing directly to the console based app.
    // It also allows to type all the information without stopping every steps on meaning it will let you input
    // all the data, and if error found, it will explain where was those error and will not record the patron entered.
    public void AddingManually() {
        //  Print out a detailed instruction on how the system will prompt librarian's input
                System.out.println("\nINSTRUCTIONS: \n" +
                "You will be prompted to enter patron data in the following sequence:\n\n " +
                "\n1.ID " +
                "\n2.First Name" +
                "\n3.Last Name" +
                "\n4.Address" +
                "\n5.Overdue Fee\n");

            // Ask for UniqueID, indicating the acceptable format with numbers and nor characters nor symbols
                System.out.print("Accepted format: Only 7 digit numbers from 0 ~ 9, not letters nor symbol." +
                "\nPlease, type 7-digit unique member ID: ");
                String seven_digits_ID = in.nextLine().trim();

            // Ask for First Name, highlight that is only first name
                System.out.print("Please, type only member's First Name: ");
                String first = in.nextLine().trim();
            // Ask for Last Name, highlight that is only last name
                System.out.print("Following, type member Last Name: ");
                String last = in.nextLine().trim();
            // Ask for Full Address
                System.out.print("Next, Enter Address. (Example: 1234 Squaretown St. Orlando, FL 32890) " +
                                 "\nFull Home Address:");
                String address = in.nextLine().trim();
            // Ask for Fee amount within the accepted range which is printed to the user
                System.out.print("Enter overdue fee amount (0.00 ~ 250.00): ");
                String EnteredFee = in.nextLine().trim();

        // ErrorInEntry will verify either the input was correct or has some error
        // If Error found. it will print at the end once user entered all the fields.
        // Indicating in which specific items was the error found.
        boolean ErrorInEntry = false;

             // No detecting 7 digits will set ErrorInEntry to True | later it will be assigned under IDError
            if (!ID_Format(seven_digits_ID)) {
                System.out.println("\n*** ERROR: Verify digits. Unique ID must be exactly 7 digits. ***");
                ErrorInEntry = true;
                // Having a duplicate will set ErrorInEntry to True | later it will be assigned under IDError
            } else if (patrons.containsKey(seven_digits_ID)) {
                System.out.println("\n*** DUPLICATE: This patron ID already exist. Patron was not added to the records. ***");
                ErrorInEntry = true;
            }
            // Having empty first, last or & address it will set ErrorInEntry to True | | later it will be assigned under FirstNameError, LastNameError, or AddressError
            if (first.isEmpty() || last.isEmpty() || address.isEmpty() ) {
                System.out.println("\n*** ERROR: Fist Name, Last Name, and Address cannot be blank ***");
                ErrorInEntry = true;
            }
            // If there is not amount entered or not within range it will set ErrorInEntry to True | later it will be assigned under FeeError
           Double fee = Fee_Format(EnteredFee);
            if (fee == null) {
                System.out.println("\n*** ERROR: Verify Fee amount. Fee must be a number in between [0.00 ~ 250.00] ***");
                ErrorInEntry = true;
            }

        // Setting the conditions for each boolean
        boolean IDError = !ID_Format(seven_digits_ID) || patrons.containsKey(seven_digits_ID);
        boolean FirstNameError = first.isEmpty();
        boolean LastNameError = last.isEmpty();
        boolean AddressError = address.isEmpty();
        boolean FeeError = (fee == null);

        // It will print the librarian typed content and add ***** where error was found, if not ***** will not be added.
        if (ErrorInEntry) {
           System.out.println("\n PATRON NOT ADDED - Input with error has been marked with *****");
           System.out.println("\nPatron ID: " + seven_digits_ID + (IDError ? " *****" : " "));
           System.out.println("First Name: " + first + (FirstNameError ? " *****" : " "));
           System.out.println("Last Name: " + last + (LastNameError ? " *****" : " "));
           System.out.println("Address: " + address + (AddressError ? " *****" : " "));
           System.out.println("Fee: " + EnteredFee + (FeeError ? " *****" : " "));
            return;
        }

        // HashMap put with Key seven_digits_ID to identify user ID and then value of all the entry data
        // to record into Patron HashMap if everything entered by the user was right!
                patrons.put(seven_digits_ID, new Patron(seven_digits_ID, first, last, address, fee));
                Save();

                // Print in the user screen the loading animation that the data is being recorded set thread.sleep to 750 for realistic experience
                  System.out.println("Adding new patron");

                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(750); //three / quarter of a second
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.print("..");
                }
                // Inform librarian that the patron was added successfully and prints in the screen the last entered patron.
                System.out.println("\nPatron has been added successfully!.");
                System.out.println("INFORMATION JUST ADDED: " +
                "\nMember ID: " + seven_digits_ID +
                "\nFirst Name: "  + first +
                "\nLast Name: " + last +
                "\nAddress: " + address +
                "\nOverdue Fee: " + String.format("%.2f", fee) + "\n\n");

                // Finally, also prints all the data stored so far, so librarian do not have to go [4] List all patrons again!
                    System.out.println("Patrons list in Ascending ID Order: \n");
                    List<String> patrones1 = new ArrayList<>(patrons.keySet());
                    Collections.sort(patrones1);
                    for (String PatronMember : patrones1) {
                    System.out.println(patrons.get(PatronMember)); // uses Patron.toString()
        }
    }

    // Validation methods: To make sure the format is correct and can be accepted ===
    // ID_Format Method: It will check that what the librarian has types is neither null and have 7 digits.
    public boolean ID_Format(String seven_digits_ID) {
        return seven_digits_ID != null && seven_digits_ID.matches("\\d{7}"); // exactly 7 digits number String is required!
    }

    // Fee_Format Method: This method will verify the user input by checking the accepted range which is 0.00 < value < 250.00
        public Double Fee_Format(String EnteredFee) {
        try {
            // It converts in double format
            double value = Double.parseDouble(EnteredFee);
            if (value < 0.00 || value > 250.00) return null;
            return value;
            // It will also return null printing the Error message to the user
        } catch (Exception e) {
            return null;
        }
    }

    //AddingFromTxtFile Method: For this program, the user can choose either using my sample file stored under
    // resources titled LocalLibraryPatronsDataSave.txt so they can just copy the path. Another option is to
    // select the absolute path and the instruction portion will explain how to enter the txt file path.
     public void AddingFromTxtFile() {
        System.out.print("INSTRUCTIONS: \nFile location in Desktop, user named librarian and named patrons.txt\n " +
                "\nWindows User type C:\\Users\\librarian\\Desktop\\patrons.txt " +
                "\nMac or Linux User type /Users/librarian/Desktop/patrons.txt" +
                "\nEnter file path: "
        );
        String TxtPath = in.nextLine().trim();
        // If librarian just copied the path including " or ' and pasted to the console.
        // This will remove those "" '' making them able to be read.
        if ( (TxtPath.startsWith("\"") && TxtPath.endsWith("\"")) ||(TxtPath.startsWith("'") && TxtPath.endsWith("'")) ) {
            TxtPath = TxtPath.substring(1, TxtPath.length()-1).trim();
         }

        // In case an exception has been caught it will print depending on the case the message to the user.
        Path path;
                try {
                  path = Paths.get(TxtPath);
                } catch (Exception e) {
                    System.out.println("*** ERROR: Invalid file path ***");
                    return;
                }
                if(!Files.exists(path)) {
                    System.out.println("*** ERROR: File doesn't exist. ***");
                    return;
                }

      // For interface and interactivity:
      // Adding a counter indicator for line order, added values, duplicates, error and skipped values has been introduced.
      int lineInTxt = 0, SuccessfullyAdded = 0, DuplicatesValue = 0, Errors = 0, Skipped = 0;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(TxtPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineInTxt++;
                line = line.trim();
                if (line.isEmpty()) continue;
                // Creating an array to store values separated by Unique ID, FullName, Address, and Fee.
                // User will type naturally in their Text file as following:
                // 1231231-Kenji Nakanishi-48 Old Morton St. Orlando FL. 32837-00.02
                // Notice how first and last name goes together without a dash line.
                // This part will separate whenever a dash is found.
                String[] info = line.split("-", 4);
                // Whenever finds a line that does not contain all 4 records will be skipped and count towards the skip count.
                if (info.length != 4) {
                    System.out.println("line number: " + lineInTxt + ": has been skipped.");
                    Skipped++;
                    continue;
                }
                //Important: Dividing in 4 parts:
                String Unique_ID        = info[0].trim(); // before 1st dash will hold Unique Patron ID
                String FullName         = info[1].trim(); // This part will hold first name and last name
                String Address          = info[2].trim(); // Address will be held in the third section
                String FeeInString      = info[3].trim(); // fee information in String format

                // I used the regular expression to find a white space character and when find it separate them,
                // Whenever found a whitespace, the program understand that the first part is the first name
                // the second part must be the last name
                String[] PatronsNameParts = FullName.split("\\s+");
                // if there is less than two names or more, != 2 will detect as error
                if (PatronsNameParts.length != 2) {
                    System.out.println("line number: " + lineInTxt + " | ERROR: Naming format incorrect: " + FullName);
                    Errors++;
                    continue;
                }
                String FirstName = PatronsNameParts[0]; // understanding the string before whitespace is firstname
                String LastName  = PatronsNameParts[1]; // understanding the string after whitespace is lastname
                // Whenever does not match with ID_Format that has a regex of 7 number format
                if (!ID_Format(Unique_ID)) {
                    System.out.println("line number: " + lineInTxt + " | ERROR: Verify digits typed: " + Unique_ID);
                    Errors++;
                    continue;
                }
                // Whenever the fee format is not within range as specified in Fee_Format
                Double fee = Fee_Format(FeeInString);
                if (fee == null) {
                    System.out.println("line number: " + lineInTxt + " | ERROR:Verify fee typed: " + FeeInString);
                    Errors++;
                    continue;
                }
                // Whenever the Unique_ID has been previously entered
                if (patrons.containsKey(Unique_ID)) {
                    System.out.println("line number: " + lineInTxt + " Skipped since DUPLICATE entries. " + Unique_ID);
                    DuplicatesValue++;
                    continue;
                }
                // If everything goes well, those patrons passed the test will be added successfully!
                patrons.put(Unique_ID, new Patron(Unique_ID, FirstName, LastName, Address, fee));
                SuccessfullyAdded++;
            }
        } catch (IOException i) {
            System.out.println("I/O error: " + i.getMessage());
            return;
        }
        // This will storage the path so when hit exit it will be saved to the chosen path.
        this.ChooseTxtFile = path;
        // Print in the user screen the loading animation that the data is being loaded to add interactivity same 750 milliseconds
         System.out.println("Loading patrons from Text file.");
         for (int i = 0; i < 3; i++) {
             try {
                 Thread.sleep(750); //three / quarter of a second
             } catch (InterruptedException e) {
                 Thread.currentThread().interrupt();
             }
             System.out.print("..");
         }
         // Print to the librarian what has been counted!
        System.out.printf("\nDone! \nPatrons that were added: %d, duplicates: %d, errors: %d, Skipped: %d",
                SuccessfullyAdded, DuplicatesValue, Errors, Skipped);
    }

    // RemovingByID method: This method will  evaluate if the list is empty first.
    // If any ID found, it will print that any ID was not found.
    // If there are Patron IDs stored previously, the Librarian will be asked to check if the entered ID information matches in name and addres
    //  User will be prompted to select by Y and N Y = to erase, N = to cancel action
    // If user enter another option out of y and n, the while loop will keep printing invalid entry
    public void RemovingByID() {
        if (patrons.isEmpty()) {
            System.out.println("*** ERROR: Patrons list is EMPTY!. ***");
            return;
        }
        // Prompt to enter the Patron ID to remove
        System.out.print("Enter 7-digit Patron ID to remove: ");
        String seven_digits_ID = in.nextLine().trim();
        // This will check if any patrons were previously entered
        Patron PID = patrons.get(seven_digits_ID);
        if (PID == null) {
            System.out.println("*** ERROR: Patron ID not found!. ***");
            return;
        }
        // This will show the patron information to ask the librarian kind of are you sure this is the data you want to erase:
        System.out.println("It correspond to: \n" + PID);
        // Start a while loop to ask for user selection Y or N
        while (true) {
            System.out.println("Please confirm you want to remove this patron? (y/n); ");
            String confirmation = in.nextLine().trim();
            // for Yes = y and Y
            if (confirmation.equals("y") || confirmation.equals("Y")) {
                patrons.remove(seven_digits_ID);
                Save();
                System.out.println("Patrons have been removed successfully!\n\n");
                break;
                // for No = n and N
            } else if (confirmation.equals("n") || confirmation.equals("N")) {
                System.out.println("Cancelled");
                break;
                // in case other character was entered loop again.
            } else {
                System.out.println("Invalid, please enter Y for yes & N for no");
            }
        }
        // Print the patrons list after deleted so the librarian does not have to go [4] List all patron again.
        System.out.println("Patrons list in Ascending ID Order: \n");
        List<String> patrones2 = new ArrayList<>(patrons.keySet());
        Collections.sort(patrones2);
        for (String PatronMemberID : patrones2) {
            System.out.println(patrons.get(PatronMemberID)); // uses Patron.toString()
        }
    }

    // Method that will display the list of the patrons added so far
    // if the list is empty it will print to the user that there is none recorded.
    public void DisplayList() {
        if (patrons.isEmpty()) {
            System.out.println("No patrons stored to display.");
            return;
        }
        List<String> patrones = new ArrayList<>(patrons.keySet());
        // Print the list in Ascending order of ID:
        System.out.println("Patrons list in Ascending ID Order: \n");
        Collections.sort(patrones);
        for (String seven_digits_ID : patrones) {
            System.out.println(patrons.get(seven_digits_ID)); // uses Patron.toString()
        }
    }
    // Save method, it will inform the librarian first that the save path has not been selected yet.. and encourage to specify one.
    // Basically, It does save after ManuallyAdding, RemoveByID, and when Exiting the program.
    public void Save() {
        if(ChooseTxtFile == null) {
            System.out.println("Patron DATA SAVED; However no file path has been specified to save it yet!!! \n If you want to save this patron data please choose a file path using option [2] AddingFromTxtFile\n"+
                    "After adding the file path, this data will be stored as well!\n");
            System.out.println("Saving in temporary memory...");
            return;
        }
        List<String> ids = new ArrayList<>(patrons.keySet());
        Collections.sort(ids);
        // It uses ChooseTxtFile
        try (BufferedWriter bw = Files.newBufferedWriter(ChooseTxtFile)) {
            for (String id : ids) {
                bw.write(patrons.get(id).toFileLine());
                bw.newLine();
            }
        } catch (IOException ioe) {
            System.out.println("Error: Failed to save patrons: " + ioe.getMessage());
        }
    }
}