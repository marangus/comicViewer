//Comic class
class Comic{
  private String title;
  private String description;
  private String image;
  private double price;
  private String printedDate;
  private URL purchase;

  public Comic(String comicTitle, String descript, String coverImage,
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
