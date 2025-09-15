/*Within Patron Class we will find variables that will compose each patron
its getters, and the ToString method where we will specify the format of the desired formatting" */
public class Patron{
    // Variable Declaration:
    private final String uniqueID;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final double fee; 
// Constructor
public Patron (String uniqueID, String firstName, String lastName, String address, double fee) {
  this.uniqueID = uniqueID;
  this.firstName = firstName;
  this.lastName = lastName;
  this.address = address;
  this.fee = fee;
} 
// Getters
public String getUniqueID(){
    return uniqueID;
}
public String getFirstName(){
    return firstName;
}
public String getLastName(){
    return lastName;
}
public String getAddress(){
    return address;
}
public double getFee(){
    return fee;
}
    // Override method with the customer
    @Override
    public String toString(){
        return String.format("%s-%s %s-%s-%.2f",
        uniqueID,firstName,lastName,address,fee);
    }
    // toFileLine method  is the used format when Save() method is writing lines into the txt. file
    public String toFileLine(){
        return uniqueID + "-" + firstName + " " + lastName + "-" + address + "-" + String.format("%.2f", fee);
    }
}