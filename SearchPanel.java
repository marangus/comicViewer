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
