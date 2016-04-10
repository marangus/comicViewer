import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.json.simple.*;

/*Notes:
	- System does not display a message when results are not found, simply nothing is displayed.
	- all fields are required
	- all text should be lowercase
	- example of entry "comic starts with: d issue number: 2"

	Produced by: Mark Angus
	Date: 17/02/2016*/


//Provides comic book information based on requests
public class ComicCatalogue {

	//implement login screen requiring key

	public static void main(String[] args) throws IOException, ParseException,
	 Exception {

		//Style
		LineBorder blackline = new LineBorder(Color.black);

		//comic array
		ArrayList<ComicObject> comicArr = new ArrayList<Comic>();

		//Defaults for API
		String format = "format=comic";
		String formatType = "&formatType=comic";
		String noVarients = "&noVariants=true";
		String hasDigitalIssue = "&hasDigitalIssue=false";

		//Keys
		//A random timestamp to make calls
		String ts="&ts=2009-09-22%2016:47:08";
		//provide key and hash as command line args
		String apiKey= args[1];
		String hash= args[2];

		//Attribution
		String attribution = "Data provided by Marvel. � 2014 Marvel";

		//Main Frame
		JFrame mainWindow = new JFrame("Comic Library");

		//Main panel
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(1000, 400));

		//Create Selection panel

		//Create Daily Comic panel

		//create random comic button listener

		//new daily comic button


    //create new library panel

		//create new mouse listener instance

		//create new subnit button mouse listener

		//Daily Panel components
		dailyPanel.add(dcPanel);
		dailyPanel.add(dcButton);
		dailyPanel.setBackground(Color.WHITE);

		//LibPanel components
		libPanel.setBackground(Color.WHITE);

		//Add add attribute panel

		//Add search submit button
		JButton submitButton = new JButton("Submit");
		SubmitMouseListener submitListener  = new SubmitMouseListener();
		submitButton.addMouseListener(submitListener);
		searchPanel.add(submitButton);

		//Add panels to container
		container.add(searchPanel, BorderLayout.NORTH);
		container.add(libPanel,BorderLayout.WEST);
		container.add(selectionPanel,BorderLayout.CENTER);
		container.add(dailyPanel, BorderLayout.EAST);

		//Add container to mainWindow
		mainWindow.add(container);

		//Display Interface
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.pack();
		mainWindow.setVisible(true);
	}
}
