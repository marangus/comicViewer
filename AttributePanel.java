//Add attribute panel
JLabel attributionLabel= new JLabel(attribution);
JPanel attribPanel = new JPanel();
attribPanel.add(attributionLabel);
attribPanel.setBackground(Color.BLACK);
attributionLabel.setForeground(Color.WHITE);
container.add(attribPanel, BorderLayout.SOUTH);
