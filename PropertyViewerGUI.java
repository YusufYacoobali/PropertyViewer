import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.io.File;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * PropertyViewerGUI provides the GUI for the project. It displays the property
 * and strings, and it listens to button clicks.

 * @author Michael Kölling, David J Barnes, Josh Murphy and Yusuf Yacoobali
 * @version 3.0
 */
public class PropertyViewerGUI
{
    // fields:
    private JFrame frame;
    private JPanel propertyPanel;
    private JLabel idLabel;
    private JLabel favouriteLabel;
    private JTextField hostIDLabel;
    private JTextField hostNameLabel;
    private JTextField neighbourhoodLabel;
    private JTextField roomTypeLabel;
    private JTextField priceLabel;
    private JTextField minNightsLabel;
    private JTextArea descriptionLabel;
    
    //Statisctics Frame
    private JFrame statsFrame;          
    private JPanel statsPanel;
    private JTextField viewedAlready;       
    private JTextField averagePrice;
    
    //Bookings Frame    This is for my own challenge, I made it possible to book places and see all the bookings that have been made
    private JFrame bookFrame;
    private JPanel mainBookingPanel;    //this frame has 3 panels, 1 left, 1 right and another to hold both of them
    private JPanel leftPanel;           
    private JPanel scrollPanel;         //called scroll panel because it gets used to create the scroll panel which becomes the right panel
    private JScrollPane rightPanel;     //called right and left panel for convenience sake, much easier than typing and remembering bookingDetailsPanel
    private JTextField bookingsMade;
    private JTextField allBookingsPrice;
    
    private Property currentProperty;
    private PropertyViewer viewer;
    private boolean fixedSize;
    private int numOfBookings;
        
    /**
     * Create a PropertyViewer and display its GUI on screen.
     */
    public PropertyViewerGUI(PropertyViewer viewer)
    {
        currentProperty = null;
        this.viewer = viewer;
        fixedSize = false;
        makeFrame();
        statsFrame();
        bookFrame();                            //all frames and variables declared here
        numOfBookings = 0;
        this.setPropertyViewSize(400, 250);
    }


    // ---- public view functions ----
    
    /**
     * Display a given property
     */
    public void showProperty(Property property)
    {
        hostIDLabel.setText(property.getHostID());
        hostNameLabel.setText(property.getHostName());
        neighbourhoodLabel.setText(property.getNeighbourhood());
        roomTypeLabel.setText(property.getRoomType());
        priceLabel.setText("£" + property.getPrice());
        minNightsLabel.setText(property.getMinNights());
    }
    
    /**
     * Set a fixed size for the property display. If set, this size will be used for all properties.
     * If not set, the GUI will resize for each property.
     */
    public void setPropertyViewSize(int width, int height)
    {
        propertyPanel.setPreferredSize(new Dimension(width, height));
        frame.pack();
        fixedSize = true;
    }
    
    /**
     * Show a message in the status bar at the bottom of the screen.
     */
    public void showFavourite(Property property)
    {
        String favouriteText = " ";
        if (property.isFavourite()){
            favouriteText += "This is one of your favourite properties!";
        }
        favouriteLabel.setText(favouriteText);
    }
    
    /**
     * Show the ID in the top of the screen.
     */
    public void showID(Property property){
        idLabel.setText("Current Property ID:" + property.getID());
    }
    
    // ---- implementation of button functions ----
    
    /**
     * Called when the 'Next' button was clicked.
     */
    private void nextButton()
    {
        viewer.nextProperty();
    }

    /**
     * Called when the 'Previous' button was clicked.
     */
    private void previousButton()
    {
        viewer.previousProperty();
    }
    
    /**
     * Called when the 'View on Map' button was clicked.
     */
    private void viewOnMapsButton()
    {
        try{
         viewer.viewMap();
        }
        catch(Exception e){
            System.out.println("URL INVALID");
        }
        
    }
    
    /**
     * Called when the 'Toggle Favourite' button was clicked.
     */
    private void toggleFavouriteButton(){
        viewer.toggleFavourite();     
    }
    
    /**
     * Called when 'Statistics' button is clicked.
     */
    private void statsButton(){    
        statsFrame.setVisible(true);                                                        //set stats window to true so it is displayed. Got this idea from stackoverflow url=https://stackoverflow.com/questions/15513380/how-to-open-a-new-window-by-clicking-a-button
        viewedAlready.setText(Integer.toString(viewer.getNumberOfPropertiesViewed()));      
        averagePrice.setText("£" + Integer.toString(viewer.averagePropertyPrice()));        //when button is pressed these 2 lines display the two challenge methods 
    }
    
    /**
     * Called when 'Book this place' button is clicked.
     */
    private void bookButton(){                      
        numOfBookings ++;                                       //helps to display number of properties booked later on
        scrollPanel.add(new JLabel(viewer.bookingPlaced()));    //every time a place is booked, it adds a label onto the GUI using method from PropertyViewer class
    }
    
    /**
     * Called when 'Bookings' button is clicked. 
     */
    private void placesBooked(){           
        bookFrame.setVisible(true);                                                   
        bookingsMade.setText(Integer.toString(numOfBookings));                          //these lines set text to their relative text fields
        allBookingsPrice.setText("£" + Integer.toString(viewer.getSumOfBookings()));
    }

    // ---- swing stuff to build the frame and all its components ----
    
    /**
     * Create the Swing frame and its content.
     */
    private void makeFrame()
    {
        frame = new JFrame("Portfolio Viewer Application");
        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));

        // Specify the layout manager with nice spacing
        contentPane.setLayout(new BorderLayout(6, 6));

        // Create the property pane in the center
        propertyPanel = new JPanel();
        propertyPanel.setLayout(new GridLayout(6,2));
        
        propertyPanel.add(new JLabel("HostID: "));
        hostIDLabel = new JTextField("default");
        hostIDLabel.setEditable(false);
        propertyPanel.add(hostIDLabel);
        
        propertyPanel.add(new JLabel("Host Name: "));
        hostNameLabel = new JTextField("default");
        hostNameLabel.setEditable(false);
        propertyPanel.add(hostNameLabel);
        
        propertyPanel.add(new JLabel("Neighbourhood: "));
        neighbourhoodLabel = new JTextField("default");
        neighbourhoodLabel.setEditable(false);
        propertyPanel.add(neighbourhoodLabel);
        
        propertyPanel.add(new JLabel("Room type: "));
        roomTypeLabel = new JTextField("default");
        roomTypeLabel.setEditable(false);
        propertyPanel.add(roomTypeLabel);
        
        propertyPanel.add(new JLabel("Price: "));
        priceLabel = new JTextField("default");
        priceLabel.setEditable(false);
        propertyPanel.add(priceLabel);
        
        propertyPanel.add(new JLabel("Minimum nights: "));
        minNightsLabel = new JTextField("default");
        minNightsLabel.setEditable(false);
        propertyPanel.add(minNightsLabel);

        propertyPanel.setBorder(new EtchedBorder());
        contentPane.add(propertyPanel, BorderLayout.CENTER);
        
        // Create two labels at top and bottom for the file name and status message
        idLabel = new JLabel("default");
        contentPane.add(idLabel, BorderLayout.NORTH);

        favouriteLabel = new JLabel(" ");
        contentPane.add(favouriteLabel, BorderLayout.SOUTH);
        
        // Create the toolbar with the buttons
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(0, 1));
        
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { nextButton(); }
                           });
        toolbar.add(nextButton);
        
        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { previousButton(); }
                           });
        toolbar.add(previousButton);

        JButton mapButton = new JButton("View Property on Map");
        mapButton.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { viewOnMapsButton(); }
                           });
        toolbar.add(mapButton);
        
        JButton favouriteButton = new JButton("Toggle Favourite");
        favouriteButton.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { toggleFavouriteButton(); }
                           });
        toolbar.add(favouriteButton);
        
        JButton stats_Button = new JButton("Statistics");
        stats_Button.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { statsButton(); }
                           });
        toolbar.add(stats_Button);
        
        JButton bookButton = new JButton("Book this place");
        bookButton.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { bookButton(); }
                           });
        toolbar.add(bookButton);
        
        JButton bookingButton = new JButton("Bookings");
        bookingButton.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { placesBooked(); }
                           });
        toolbar.add(bookingButton);


        // Add toolbar into panel with flow layout for spacing
        JPanel flow = new JPanel();
        flow.add(toolbar);
        
        contentPane.add(flow, BorderLayout.WEST);
        
        // building is done - arrange the components     
        frame.pack();
        
        // place the frame at the center of the screen and show
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        frame.setVisible(true);
    } 
    
    /**
     * Statistics window is created
     */
    private void statsFrame(){
        statsFrame = new JFrame("Statistics");
        statsPanel = new JPanel(new GridLayout(2,2));                                                           //a panel of 2x2 grid is created and later added to the frame
        
        statsPanel.add(new JLabel("Properties viewed: "));                                                      //these are declared and set like the in the makeFrame() method for convenience and elegance sake
        viewedAlready = new JTextField();                                                                  
        viewedAlready.setEditable(false);                                                                    
        statsPanel.add(viewedAlready);                                                                          //TextField added to the panel which holds the grid
        
        statsPanel.add(new JLabel("Average price of properties viewed: "));
        averagePrice = new JTextField();
        averagePrice.setEditable(false);
        statsPanel.add(averagePrice);

        statsFrame.add(statsPanel);                                                                             //panel is added to the frame
        statsFrame.pack();                                                                                     
        Dimension s = Toolkit.getDefaultToolkit().getScreenSize();                                              //for elegance sake, used the same style as the frame above.
        statsFrame.setLocation(s.width/2 - statsFrame.getWidth()/2, s.height/2 - statsFrame.getHeight()/2);
        statsFrame.setVisible(false);                                                                           //set to false when created so not shown
    }
    
    /**
     * Bookings window is created
     */
    private void bookFrame(){
        bookFrame = new JFrame("Places Booked");
        mainBookingPanel = new JPanel(new FlowLayout());                    //flow layout is useful for putting togther panels, first left then right panel is added.
        leftPanel = new JPanel(new GridLayout(4,1));                        //(4,1) grid for labels and textfields
        scrollPanel = new JPanel();
        
        BoxLayout boxes = new BoxLayout(scrollPanel, BoxLayout.Y_AXIS);     //type of layout is made and then applied to the scroll panel
        scrollPanel.setLayout(boxes);
    
        scrollPanel.add(new JLabel("BOOKINGS MADE:"));
        rightPanel = new JScrollPane(scrollPanel);                          //scroll panel is then put into an actual scroll panel and called the right panel
        rightPanel.setPreferredSize(new Dimension(200,200));
        
        leftPanel.add(new JLabel("Number of bookings made:"));              //These next 2 blocks deal with the left panel
        bookingsMade = new JTextField();
        bookingsMade.setEditable(false);
        leftPanel.add(bookingsMade);
        
        leftPanel.add(new JLabel("Price of all bookings made:"));
        allBookingsPrice = new JTextField();
        allBookingsPrice.setEditable(false);
        leftPanel.add(allBookingsPrice);
        
        mainBookingPanel.add(leftPanel);                                //all panels added to main panel
        mainBookingPanel.add(rightPanel);
        bookFrame.add(mainBookingPanel);                               //main panel added to frame
        
        bookFrame.pack();
        Dimension x = Toolkit.getDefaultToolkit().getScreenSize();                                              
        bookFrame.setLocation(x.width/2 - bookFrame.getWidth()/2, x.height/2 - bookFrame.getHeight()/2);
        bookFrame.setVisible(false);
    }
}
