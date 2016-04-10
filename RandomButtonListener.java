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
