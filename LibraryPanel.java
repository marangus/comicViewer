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
