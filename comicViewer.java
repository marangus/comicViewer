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
public class ConsumeJson {

	public static void main(String[] args) throws IOException, ParseException, Exception {
		
		//Style
		LineBorder blackline = new LineBorder(Color.black);
		
		//Comic object class
		class ComicObject{
			private String title;
			private String description;
			private String image;
			private double price;
			private String printedDate;
			private URL purchase;
			
			public ComicObject(String comicTitle, String descript, String coverImage, 
					double issuePrice, String issueNo, String printDate,URL purchaseURL){
				title = comicTitle;
				description = descript; 
				image = coverImage;
				price = issuePrice;
				printedDate = printDate;
				purchase = purchaseURL;
			}
			
			public String getTitle(){
				return this.title;
			}
			public String getCoverImage(){
				return this.image;
			}
			public String getPrintedDate(){
				return this.printedDate;
			}
			public String getDescription(){
				return this.description;
			}
			public Double getPrice(){
				return this.price;
			}
			public URL getPurchaseURL(){
				return this.purchase;
			}
		}
		
		//comic array
		ArrayList<ComicObject> comicArr = new ArrayList<ComicObject>();
		
		//Defaults
		String format = "format=comic";
		String formatType = "&formatType=comic";
		String noVarients = "&noVariants=true";
		String hasDigitalIssue = "&hasDigitalIssue=false";
		
		//Keys
		//A random timestamp to make calls
		String ts="&ts=2009-09-22%2016:47:08";
		String apiKey="&apikey=placePublicAPIKeyHere";	
		String hash= "&hash=PlaceHashOfKeysandTimeStampHere";
		
		//Attribution
		String attribution = "Data provided by Marvel. © 2014 Marvel"; 
		
	//Main Frame
		JFrame mainWindow = new JFrame("Comic Library"); 
	
	//Main panel
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(1000, 400));
		
	//Selection panel
		JPanel selectionPanel = new JPanel();
		selectionPanel.setBorder(blackline);
		selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
		

	//Daily comic panel
		JPanel dailyPanel = new JPanel();
		JPanel dcPanel = new JPanel();
		dailyPanel.setBorder(blackline);
		dailyPanel.setLayout(new BoxLayout(dailyPanel, BoxLayout.Y_AXIS));
		JLabel dailyComicLabel = new JLabel("Mystery Comic");
		dailyPanel.add(dailyComicLabel);
		
		//Listener for random comic button
		class RandomMouseListener extends MouseInputAdapter{
			
			@Override
			public void mouseClicked(MouseEvent mouseEvent){
				
				dcPanel.removeAll();
				
				//produce random search criteria
				Random randGen = new Random();
				char randTitleStartsWithChar = (char)(randGen.nextInt(26)+'a');
				String randTitleStartsWith = "" + randTitleStartsWithChar;
				String randIssueNumber=Integer.toString(randGen.nextInt(100));
				
				try{
					URL randomResult = new URL(String.format("http://gateway.marvel.com/v1/public/comics?%s%s%s&titleStartsWith=%s&issueNumber=%s%s%s%s%s"
							,format,formatType,noVarients,randTitleStartsWith,randIssueNumber,hasDigitalIssue,ts,apiKey,hash));
							
					try{
						HttpURLConnection randomRequest= (HttpURLConnection) randomResult.openConnection();
						randomRequest.connect();
						try{
							InputStream randInputStream = (InputStream) randomRequest.getContent();
							JSONParser jsonParser = new JSONParser();
							Object randomObj= jsonParser.parse(new InputStreamReader(randInputStream));
							Map randomMap = (Map)randomObj;
							Map randData = (Map)randomMap.get("data");
							JSONArray randResults = (JSONArray)randData.get("results");
							Map randProp = (Map)randResults.get(0);
							Map randomCoverString = (Map)randProp.get("thumbnail");
							String randomCoverPath = (String)randomCoverString.get("path");
							randomCoverPath = randomCoverPath.replace("\\","");
							String randomCoverSize = "/portrait_xlarge.";
							String randomCoverExten = (String)randomCoverString.get("extension");
							String randCoverURL=randomCoverPath+randomCoverSize+randomCoverExten;
							URL randomCoverURL = new URL(randCoverURL);
							
				
							//Image panel
							BufferedImage dailyComic = ImageIO.read(randomCoverURL);
							JLabel imageLabel = new JLabel(new ImageIcon(dailyComic));
							dcPanel.add(imageLabel);
							dcPanel.setAlignmentX(0);
							dcPanel.setAlignmentY(0);
							
							dcPanel.validate();
							dcPanel.repaint();
							mainWindow.pack();
						} catch (ParseException e) {
							e.printStackTrace();
						}
						finally{
							
						}
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}finally{
					
				}
			}
		}
		
		//daily button
		JButton dcButton = new JButton("See Mystery Comic");
		RandomMouseListener randomMouse = new RandomMouseListener();
		dcButton.addMouseListener(randomMouse);
		
	//search panel
		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(Color.BLACK);
		searchPanel.setLayout(new FlowLayout());
		JLabel searchLabel = new JLabel("Search");
		searchLabel.setForeground(Color.WHITE);
		JLabel characterLabel = new JLabel("Series Begins With:");
		characterLabel.setForeground(Color.WHITE);
		JLabel issueLabel = new JLabel("Issue Number:");
		issueLabel.setForeground(Color.WHITE);
		JTextField charSearch = new JTextField(20);
		JTextField issueSearch = new JTextField(5);
		
		searchPanel.add(searchLabel);
		searchPanel.add(characterLabel);
		searchPanel.add(charSearch);
		searchPanel.add(issueLabel);
		searchPanel.add(issueSearch);
		
	//Library Panel
		JPanel libPanel = new JPanel();
		libPanel.setLayout(new BoxLayout(libPanel,BoxLayout.Y_AXIS));
		libPanel.setBorder(blackline);
		JLabel resultsLabel = new JLabel("Results: ");
		JPanel resultsLabelPanel = new JPanel();
		resultsLabelPanel.add(resultsLabel);
		resultsLabelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		libPanel.add(resultsLabelPanel);
		libPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel searchResults = new JPanel();
		searchResults.setLayout(new BoxLayout(searchResults, BoxLayout.Y_AXIS));
		resultsLabelPanel.add(Box.createRigidArea(new Dimension(250,0)));
		
		
		//result mouse listener
		class MyMouseListener extends MouseInputAdapter{
			
			ComicObject currentComic;
			
			//buy button mouse listener
			class BuyMouseListener extends MouseInputAdapter{
				@Override
				public void mouseClicked(MouseEvent mouseEvent){
					URL weburl= currentComic.getPurchaseURL();
					try {
						Desktop.getDesktop().browse(weburl.toURI());
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent mouseEvent){
				
				//Layout items on selection panel
				selectionPanel.removeAll();
				
				JPanel box1 = new JPanel();
				
				JLabel detailsTitle = new JLabel("Details:");
				JLabel label = (JLabel)mouseEvent.getSource();
				for(int i=0;i<comicArr.size();i++){
					if(label.getText().equals(comicArr.get(i).getTitle())){
						//get comic
						ComicObject comic = comicArr.get(i);
						
						//get title
						JLabel selectedTitleLabel = new JLabel(comic.getTitle());
						
						//get image with URL
						String coverString = comic.getCoverImage();
						URL coverURL;
						
						//get printed date
						String printedDate = comic.getPrintedDate();
						
						//get Description
						String description = comic.getDescription();
						
						//get price
						Double price = comic.getPrice();
						
						//get purchase url
						URL purchaseURL = comic.getPurchaseURL();
						
						
						try {
							coverURL = new URL(coverString);
							try {
								BufferedImage cImage = ImageIO.read(coverURL);
								JPanel coverImage = new JPanel();
								JLabel imageContent = new JLabel();
								imageContent.setIcon(new ImageIcon(cImage));
								coverImage.add(imageContent);
								box1.add(coverImage);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} catch (MalformedURLException e1) {
							e1.printStackTrace();
						}
						
						//create components
						JTextArea selectionDescription = new JTextArea(description);
						selectionDescription.setColumns(15);
						selectionDescription.setRows(15);
						selectionDescription.setMargin(new Insets(2,2,2,2));
						
						JScrollPane scroll = new JScrollPane(selectionDescription);
						selectionDescription.setEditable(false);
						selectionDescription.setLineWrap(true);
						selectionDescription.setWrapStyleWord(true);
					    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
					    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
						JPanel descriptionPanel = new JPanel();
						descriptionPanel.add(scroll);
						
						//Price
						JPanel selectionPrice = new JPanel();
						JLabel costLabel = new JLabel("Cost: ");
						selectionPrice.add(costLabel);
						JLabel priceLabel = new JLabel("Price: $"+price.toString());
						selectionPrice.add(priceLabel);
						
						//add to panel
						JPanel titlePanel = new JPanel();
						titlePanel.add(detailsTitle);
						titlePanel.add(selectedTitleLabel);
						
						
						JPanel box2 = new JPanel();
						box2.setLayout(new BoxLayout(box2,BoxLayout.Y_AXIS));
						JPanel box3 = new JPanel();
						
						currentComic = comic;
						
						JButton purchaseButton = new JButton("Buy");
						BuyMouseListener buy = new BuyMouseListener();
						purchaseButton.addMouseListener(buy);
						
						//Add components to Screen
						
						box1.add(descriptionPanel);
						box2.add(titlePanel);
						box2.add(box1);
						box3.add(priceLabel);
						box3.add(purchaseButton);
						
						selectionPanel.add(box2);
						selectionPanel.add(box3);
						
						
					}
				}
				//Refresh Screen
				selectionPanel.validate();
				selectionPanel.repaint();
				mainWindow.pack();
			}
		}
		
		//Search submit button mouse listener
		class SubmitMouseListener extends MouseInputAdapter{
			@Override
			public void mouseClicked(MouseEvent mouseEvent){
				String issueNumber = issueSearch.getText(); 
				String titleStartsWith =charSearch.getText();
				
				//Attmpt to clear search list
				libPanel.removeAll();
				libPanel.add(resultsLabelPanel);
				libPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
				
				searchResults.removeAll();
				
				URL url;
				try {
					url = new URL(String.format("http://gateway.marvel.com/v1/public/comics?%s%s%s&titleStartsWith=%s&issueNumber=%s%s%s%s%s"
							,format,formatType,noVarients,titleStartsWith,issueNumber,hasDigitalIssue,ts,apiKey,hash));
					HttpURLConnection request;
					try {
						request = (HttpURLConnection) url.openConnection();
						request.connect();
						
						try(InputStream InputStream = (InputStream) request.getContent()){
							
							JSONParser jsonParser = new JSONParser();
							Object obj= jsonParser.parse(new InputStreamReader(InputStream));
							Map dataReturned = (Map)obj;
							
							Map data= (Map)dataReturned.get("data");
							JSONArray results = (JSONArray)data.get("results");
							
							
							//Get relevant results for search
							for(int i=0;i<results.size();i++){
								//Extract data
								Map comic = (Map)results.get(i);
								 
								String comicTitle = (String)comic.get("title"); 
								String descript = (String)comic.get("description");
								JSONArray issuePrices = (JSONArray)comic.get("prices");
								
								Map issuePriceValue = (Map)issuePrices.get(0);
								double issuePrice = ((Number)issuePriceValue.get("price")).doubleValue();
								String issueNo = (String)comic.get("issueNo");
								JSONArray dates = (JSONArray)comic.get("dates");
								String printDate = ((String)((Map)dates.get(0)).get("date")).substring(0, 9); // on-sale date
								
								Map coverImageResult = (Map)comic.get("thumbnail");
								String coverPath = (String)coverImageResult.get("path");
								coverPath = coverPath.replace("\\","");
								String coverSize = "/portrait_xlarge.";
								String coverExten = (String)coverImageResult.get("extension");
								String coverURL=coverPath+coverSize+coverExten;
								JSONArray purchaseURLArray =(JSONArray)(comic.get("urls"));
								Map purchaseURLMap =(Map)purchaseURLArray.get(1);
								String purchaseURLString = (String)purchaseURLMap.get("url");
								URL purchaseURL= new URL(purchaseURLString.replace("\\",""));
								
								//Create object with data
								comicArr.add(new ComicObject(comicTitle,descript,coverURL,issuePrice,issueNo,printDate,purchaseURL));
							}
							
							//populate Results panel
							for(int i=0;i<comicArr.size();i++){
								JLabel selectionOption = new JLabel(comicArr.get(i).getTitle());
								
								//set up action listener for each label
								MyMouseListener resultListener  = new MyMouseListener();  
								selectionOption.addMouseListener(resultListener);; 
								searchResults.add(selectionOption);
							}
							//Add scrollbar to results
							JScrollPane resultScroll = new JScrollPane(searchResults);
							resultScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
							resultScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
							resultScroll.setPreferredSize(new Dimension(150, 300));
							libPanel.add(resultScroll);
						
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Update screen
				container.validate();
				container.repaint();
				mainWindow.pack();
			}
		}
		
		//Daily Panel components
		dailyPanel.add(dcPanel);
		dailyPanel.add(dcButton);
		dailyPanel.setBackground(Color.WHITE);
		
		//LibPanel components
		libPanel.setBackground(Color.WHITE);
		
		//Add attribute panel
		JLabel attributionLabel= new JLabel(attribution);
		JPanel attribPanel = new JPanel();
		attribPanel.add(attributionLabel);
		attribPanel.setBackground(Color.BLACK);
		attributionLabel.setForeground(Color.WHITE);
		container.add(attribPanel, BorderLayout.SOUTH);
		
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
