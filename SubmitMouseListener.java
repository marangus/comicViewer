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
