//result mouse listener
class MyMouseListener extends MouseInputAdapter{

  ComicObject currentComic;

  //create instance of buy mouse listener

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
