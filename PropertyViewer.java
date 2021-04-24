import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This project implements a simple application. Properties from a fixed
 * file can be displayed. 
 * 
 * My own challenge: Make it so that places can be booked and then those bookings can be displayed in a seperate window. Include a scrollable panel.
 * 
 * @author Michael Kölling, Josh Murphy and Yusuf Yacoobali
 * @version 1.0
 */
public class PropertyViewer
{    
    private PropertyViewerGUI gui;  //GUI is made.
    private Portfolio portfolio;    //portfolio of all properties 
    private int index;              //helps keep track of which property is currently displayed.
    private int propertiesViewed;   
    private int sumOfPrices;
    private int sumOfBookings;
    
    /**
     * Create a PropertyViewer and display its GUI on screen.
     */
    public PropertyViewer()
    {   
        index = 0;                                          //set at 0 to display first property.
        propertiesViewed = 1;                               //Default property is viewed so starts at 1.
        sumOfPrices = 23;                                   //starts at 23 because thats the first property is viewed regardless.
        sumOfBookings = 0;
        gui = new PropertyViewerGUI(this);
        portfolio = new Portfolio("airbnb-london.csv");
        gui.showProperty(portfolio.getProperty(index));     //using propertyViewer method to show first property
        gui.showID(portfolio.getProperty(index));          
        gui.showFavourite(portfolio.getProperty(index));    //to not edit other classes I had to put this here.
        
        // I wouldnt put the first property in the constructor but I had to, to prevent touching the other classes as much as possible as the task said that the other classes were complete.
    }

    /**
     * Displays the next property in the GUI displaying certain attributes of that property.
     */
    public void nextProperty()
    {
        index ++;                                                       //increment index to show next property
        if (index > 10) {                                               //this if statment makes it so that the properties seem to be in a loop
            index = 0;
        }
        gui.showProperty(portfolio.getProperty(index));
        gui.showID(portfolio.getProperty(index));                       //same like constructor, attributes of property shown
        gui.showFavourite(portfolio.getProperty(index));
        propertiesViewed ++;                                            //this is incremented and then used later
        sumOfPrices += (portfolio.getProperty(index)).getPrice();       //price of property is added to variable to be used later
    }

    /**
     * Displays the previous property in the GUI.
     */
    public void previousProperty()
    {
        index --;
        if (index < 0) {                                                //same like the method above, if previous is pressed on first property then it goes to the last
            index = 10;
        }
        gui.showProperty(portfolio.getProperty(index));
        gui.showID(portfolio.getProperty(index));
        gui.showFavourite(portfolio.getProperty(index));
        propertiesViewed ++;
        sumOfPrices += (portfolio.getProperty(index)).getPrice();
    }

    /**
     * Marks the property displayed as favourited.
     */
    public void toggleFavourite()
    {
        (portfolio.getProperty(index)).toggleFavourite();       //if the button is pressed then the property class toggleFavourite() method is called to change its status
        gui.showFavourite(portfolio.getProperty(index));        //if button is pressed then status of display is changed as well
    }
    
    /**
     * Returns the index which is used to see which house is currently displayed.
     */
    public int getIndex(){
        return index;
    }
    

    //----- methods for challenge tasks -----
    
    /**
     * This method opens the system's default internet browser
     * The Google maps page shows- the current properties location on the map.
     */
    public void viewMap() throws Exception
    {
       double latitude = (portfolio.getProperty(index)).getLatitude();                          //location of property is inserted to be used to locate them on the map
       double longitude = (portfolio.getProperty(index)).getLongitude() ;
       
       URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
       java.awt.Desktop.getDesktop().browse(uri); 
    }
    
    /**
     * Returns the number of properties that have been viewed so far.
     */
    public int getNumberOfPropertiesViewed()
    {
        return propertiesViewed;       
    }
    
    /**
     * Caculates and returns the average price of all properties viewed so far.
     */
    public int averagePropertyPrice()
    {
        int averagePrice = (sumOfPrices / propertiesViewed);        //calculates average price using all properties seen so far and stored in a local variable
        return averagePrice;
    }
    
    /**
     * This method is used to display some details of the bookings for the scroll panel. (This is my own challenge)
     */
    public String bookingPlaced(){
        //when booking is placed, index must be used to store details added to the rightPanel as a JLabel
        String details = portfolio.getProperty(index).getHostName() + ", " + portfolio.getProperty(index).getNeighbourhood() + ", £" + portfolio.getProperty(index).getPrice() + ", " + portfolio.getProperty(index).getRoomType() ;
        sumOfBookings += portfolio.getProperty(index).getPrice();
        return details;     //when a place is booked, this will return the details of that booking and will be displayed in the scrollable window. 
    }
    
    /**
     * Returns the sum of all bookings booked so far.
     */
    public int getSumOfBookings(){
        return sumOfBookings;
    }
}
