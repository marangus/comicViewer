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
