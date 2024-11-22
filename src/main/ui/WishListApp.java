package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import model.Friend;
import model.FriendList;
import model.Wallet;
import model.Wish;
import model.WishList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WishListApp extends JFrame {
    private JTextField nameField;
    private JTextField brandField;
    private JTextField priceField;
    private JLabel walletLabel;
    private WishList userWishList;
    private FriendList friendList;
    private Wallet wallet;
    private CardLayout cardLayout;
    private JPanel cardsPanel;
    private JPanel wishListPanel;
    private JPanel friendListPanel;
    private JsonReader jsonReader;
    private DefaultTableModel tableModel;
    private JTable table;
    private JPanel titleScreenPanel;

    public WishListApp() {
        setTitle("My Wishlist");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        userWishList = new WishList();
        friendList = new FriendList();
        wallet = new Wallet();

        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        wishListPanel = createUserWishListPanel();
        titleScreenPanel = createTitleScreenPanel();

        cardsPanel.add(titleScreenPanel, "Title Screen");
        cardsPanel.add(wishListPanel, "User's Wishlist");

        cardLayout.show(cardsPanel, "Title Screen");

        add(cardsPanel, BorderLayout.CENTER);
    }

    // Creates opening title screen
    @SuppressWarnings("methodlength")
    private JPanel createTitleScreenPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                String sep = System.getProperty("file.separator");
                ImageIcon backgroundImage = new ImageIcon(
                        System.getProperty("user.dir") + sep + "images" + sep + "logo.png");
                g.drawImage(backgroundImage.getImage(), (getWidth() - backgroundImage.getIconWidth()) / 2,
                        (getHeight() - backgroundImage.getIconHeight()) / 2,
                        500, 500, this);
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setForeground(new Color(255, 27, 167));
        startButton.setBackground(new Color(241, 213, 219));
        startButton.setOpaque(true);
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        startButton.setPreferredSize(new Dimension(150, 40));

        // ActionListener for the "Start" button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "User's Wishlist");
            }
        });

        panel.add(startButton, BorderLayout.SOUTH);

        return panel;
    }

    // Creates an input panel for input fields
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Brand:"));
        brandField = new JTextField();
        inputPanel.add(brandField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        ((PlainDocument) priceField.getDocument()).setDocumentFilter(new DecimalFilter());
        inputPanel.add(priceField);

        return inputPanel;
    }

    // Creates user wishlist panel
    @SuppressWarnings("methodlength")
    private JPanel createUserWishListPanel() {
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.add(createInputPanel(), BorderLayout.NORTH);

        String[] columnNames = { "Name", "Brand", "Price", "Checked Off" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 3 ? Boolean.class : String.class;
            }
        };
        JScrollPane scrollPane = new JScrollPane(table);
        userPanel.add(scrollPane, BorderLayout.CENTER);
        table.getModel().addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                boolean isChecked = (boolean) table.getValueAt(e.getFirstRow(), e.getColumn());
                if (e.getColumn() == 3 && isChecked) {
                    checkOff(table, e.getFirstRow(), userWishList);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Wish");
        JButton deleteButton = new JButton("Delete");
        JButton walletButton = new JButton("Wallet");
        JButton viewFriendsButton = new JButton("View Friends");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load From File");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(walletButton);
        buttonPanel.add(viewFriendsButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        String sep = System.getProperty("file.separator");
        ImageIcon originalCoinImage = new ImageIcon(
                System.getProperty("user.dir") + sep + "images" + sep + "dollar.png");
        Image scaledImage = originalCoinImage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledCoinImage = new ImageIcon(scaledImage);
        String label = "$" + String.format("%.2f", wallet.getMoney());
        walletLabel = new JLabel(label, scaledCoinImage, JLabel.LEADING);
        buttonPanel.add(walletLabel);

        userPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWish(userWishList, tableModel);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteWish(userWishList, table, tableModel);
            }
        });

        walletButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToWallet();
            }
        });

        viewFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewFriends();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });

        return userPanel;

    }

    // Checks if wish can be checked off based on amount in wallet
    private void checkOff(JTable table, int row, WishList wishlist) {
        String name = (String) table.getValueAt(row, 0);
        String brand = (String) table.getValueAt(row, 1);
        double price = (double) table.getValueAt(row, 2);

        Wish wish = wishlist.findWish(name, brand);

        if (wish != null) {
            if (wallet.getMoney() >= price) {
                wallet.spendMoney(price);
                wish.markChecked();
                walletLabel.setText("$" + String.format("%.2f", wallet.getMoney()));
            } else {
                // Insufficient funds, uncheck the wish
                JOptionPane.showMessageDialog(this, "Insufficient funds to check off this wish!",
                        "Insufficient Balance", JOptionPane.ERROR_MESSAGE);
                table.setValueAt(false, row, 3);
            }

        } else {
            System.out.println("Error: Wish not found");
        }
    }

    // Loads file into application
    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a JSON file to load");

        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON Files", "json"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                jsonReader = new JsonReader(selectedFile.getAbsolutePath());

                // Load the wishlist, friend list, and wallet
                WishList wishList = jsonReader.readWishList();
                FriendList friendList = jsonReader.readFriendList();
                Wallet wallet = jsonReader.readWallet();

                this.userWishList = wishList;
                this.friendList = friendList;
                this.wallet = wallet;

                updatePanels();
                walletLabel.setText("$" + String.format("%.2f", wallet.getMoney()));

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error loading the file: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Updates wishlist panel and friend's panels after loading
    private void updatePanels() {
        tableModel.setRowCount(0);

        for (Wish wish : userWishList.getWishList()) {
            String name = wish.getName();
            String brand = wish.getBrand();
            double price = wish.getPrice();
            boolean isChecked = wish.isChecked();
            tableModel.addRow(new Object[] { name, brand, price, isChecked });
        }

        for (Friend friend : friendList.getFriendList()) {
            String friendName = friend.getName();
            openFriendsWishlist(friendName);
        }
        cardLayout.show(cardsPanel, "User's Wishlist");
        setTitle("My Wishlist");
    }

    // Saves wishlist, friend list, and wallet to file
    private void saveToFile() {
        try {
            JsonWriter jsonWriter = new JsonWriter("wishlist_data.json");
            jsonWriter.open();
            jsonWriter.write(userWishList, wallet, friendList);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error: Could not save data.", "Save Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    // Filters to only allow doubles to two decimal places
    private static class DecimalFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (isValidDecimal(fb.getDocument().getText(0, fb.getDocument().getLength()) + string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            if (isValidDecimal(newText)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean isValidDecimal(String text) {
            return text.matches("^\\d*(\\.\\d{0,2})?$");
        }
    }

    // Allows user to add money to wallet
    @SuppressWarnings("methodlength")
    private void addToWallet() {
        JDialog walletDialog = new JDialog(this, "Add Money to Wallet", true);
        walletDialog.setSize(300, 150);
        walletDialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter amount to add ($): ");
        JTextField walletInputField = new JTextField(15);
        ((PlainDocument) walletInputField.getDocument()).setDocumentFilter(new DecimalFilter());
        JButton submitButton = new JButton("Add Money");

        panel.add(label);
        panel.add(walletInputField);
        panel.add(submitButton);
        walletDialog.add(panel, BorderLayout.CENTER);

        // Action listener for submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amountToAdd = Double.parseDouble(walletInputField.getText());

                    if (amountToAdd < 0) {
                        JOptionPane.showMessageDialog(walletDialog, "Amount must be positive.", "Input Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        wallet.addMoney(amountToAdd);
                        walletLabel.setText("$" + String.format("%.2f", wallet.getMoney()));
                        JOptionPane.showMessageDialog(walletDialog,
                                "Successfully added $" + amountToAdd + " to your wallet.");
                        walletDialog.dispose();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(walletDialog, "Please enter a valid number.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        walletDialog.setVisible(true);

    }

    // Adds wish to wishlist
    private void addWish(WishList wishlist, DefaultTableModel tableModel) {
        String name = nameField.getText();
        String brand = brandField.getText();
        String price = priceField.getText();

        if (!name.isEmpty() && !brand.isEmpty() && !price.isEmpty()) {
            double priceValue = Double.parseDouble(price);
            wishlist.addWish(name, brand, priceValue); // Add to WishList
            // String formattedPrice = "$" + String.format("%.2f", priceValue);
            tableModel.addRow(new Object[] { name, brand, priceValue, false });
            nameField.setText("");
            brandField.setText("");
            priceField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Deletes selected wish
    private void deleteWish(WishList wishlist, JTable table, DefaultTableModel tableModel) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String name = (String) tableModel.getValueAt(selectedRow, 0);
            String brand = (String) tableModel.getValueAt(selectedRow, 0);
            wishlist.deleteWish(name, brand);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a wish to delete", "Selection Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Allows user to view friends, add friends, and select friends
    @SuppressWarnings("methodlength")
    private void viewFriends() {
        JDialog friendsDialog = new JDialog(this, "View Friends", true);
        friendsDialog.setSize(300, 200);
        friendsDialog.setLayout(new BorderLayout());

        DefaultListModel<String> friendsListModel = new DefaultListModel<>();
        for (Friend friend : friendList.getFriendList()) {
            friendsListModel.addElement(friend.getName());
        }
        JList<String> friendsList = new JList<>(friendsListModel);
        JScrollPane scrollPane = new JScrollPane(friendsList);
        friendsDialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton selectFriendButton = new JButton("Select Friend");
        JButton addFriendButton = new JButton("Add Friend");
        JButton deleteFriendButton = new JButton("Delete Friend");
        buttonPanel.add(selectFriendButton);
        buttonPanel.add(addFriendButton);
        buttonPanel.add(deleteFriendButton);
        friendsDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for selecting a friend
        selectFriendButton.addActionListener(e -> {
            String selectedFriend = friendsList.getSelectedValue();
            if (selectedFriend != null) {
                openFriendsWishlist(selectedFriend);
                friendsDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(friendsDialog, "Please select a friend.", "No Friend Selected",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Action listener for adding a new friend
        addFriendButton.addActionListener(e -> {
            String newFriendName = JOptionPane.showInputDialog(friendsDialog, "Enter the new friend's name:");
            if (newFriendName != null && !newFriendName.trim().isEmpty()) {
                friendsListModel.addElement(newFriendName.trim());
                friendList.addFriend(newFriendName);
            } else {
                JOptionPane.showMessageDialog(friendsDialog, "Friend's name cannot be empty.", "Input Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Action listener for the delete friend button
        deleteFriendButton.addActionListener(e -> {
            String selectedFriend = friendsList.getSelectedValue();
            if (selectedFriend.isEmpty()) {
                JOptionPane.showMessageDialog(WishListApp.this, "Please enter a friend's name to delete",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                friendList.deleteFriend(selectedFriend);
                friendsListModel.removeElement(selectedFriend.trim());
            }
        });

        friendsDialog.setVisible(true);
    }

    // Creates each friend's to-buy list
    @SuppressWarnings("methodlength")
    private JPanel createFriendsWishListPanel(String friendName) {
        JPanel friendsPanel = new JPanel(new BorderLayout());
        friendsPanel.add(createInputPanel(), BorderLayout.NORTH);
        setTitle(friendName + "'s To-Buy List");

        String[] columnNames = { "Name", "Brand", "Price", "Checked Off" };
        DefaultTableModel friendTableModel = new DefaultTableModel(columnNames, 0);
        JTable friendTable = new JTable(friendTableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 3 ? Boolean.class : String.class;
            }
        };
        JScrollPane scrollPane = new JScrollPane(friendTable);
        friendsPanel.add(scrollPane, BorderLayout.CENTER);

        for (Wish wish : friendList.getFriend(friendName).getToBuyList().getWishList()) {
            String name = wish.getName();
            String brand = wish.getBrand();
            double price = wish.getPrice();
            boolean isChecked = wish.isChecked();
            friendTableModel.addRow(new Object[] { name, brand, price, isChecked });
        }

        friendTable.getModel().addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                boolean isChecked = (boolean) friendTable.getValueAt(e.getFirstRow(), e.getColumn());
                if (e.getColumn() == 3 && isChecked) {
                    checkOff(friendTable, e.getFirstRow(), friendList.getFriend(friendName).getToBuyList());
                }
            }
        });

        JPanel friendButtonPanel = new JPanel();
        JButton addButton = new JButton("Add Wish");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");

        friendButtonPanel.add(addButton);
        friendButtonPanel.add(deleteButton);
        friendButtonPanel.add(backButton);

        friendsPanel.add(friendButtonPanel, BorderLayout.SOUTH);

        // Action listener for adding wishes to friend's to-buy list
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWish(friendList.getFriend(friendName).getToBuyList(), friendTableModel);
            }
        });

        // Action listener for deleting wishes from friend's to-buy list
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteWish(friendList.getFriend(friendName).getToBuyList(), friendTable, friendTableModel);
            }
        });

        // Action listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "User's Wishlist");
                walletLabel.setText("$" + String.format("%.2f", wallet.getMoney()));
                setTitle("My Wishlist");
            }
        });

        return friendsPanel;
    }

    // Opens selected friend's to-buy list
    private void openFriendsWishlist(String friendName) {
        friendListPanel = createFriendsWishListPanel(friendName);
        cardsPanel.add(friendListPanel, "Friend's To-Buy List");
        cardLayout.show(cardsPanel, "Friend's To-Buy List");
    }
}