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
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class WishListApp extends JFrame {
    private JTextField nameField;
    private JTextField brandField;
    private JTextField priceField;
    private DefaultTableModel tableModel;
    private double walletBalance = 0.0;
    private JLabel walletLabel;
    private WishList userWishList; // Add your actual WishList instance here
    private FriendList userFriendList;
    private Wallet userWallet;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public WishListApp() {
        setTitle("Wishlist Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        mainPanel = new JPanel();
        add(mainPanel);

        userWishList = new WishList();
        userFriendList = new FriendList();
        userWallet = new Wallet(); // Assuming your Wallet class can accept an initial balance
        userFriendList.addFriend("Rachel");
        userFriendList.addFriend("Monica");
        userFriendList.addFriend("Phoebe");

        JPanel wishlistPanel = createWishlistPanel();
        JPanel viewFriendsPanel = createViewFriendsPanel();
        mainPanel.add(wishlistPanel, "Wishlist");
        mainPanel.add(viewFriendsPanel, "ViewFriends");

        cardLayout.show(mainPanel, "Wishlist");

    }

    private JPanel createWishlistPanel() {
        JPanel wishlistPanel = new JPanel(new BorderLayout());
        // Input panel for item details
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("Item Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Brand:"));
        brandField = new JTextField();
        inputPanel.add(brandField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        ((PlainDocument) priceField.getDocument()).setDocumentFilter(new DecimalFilter());
        inputPanel.add(priceField);

        wishlistPanel.add(inputPanel, BorderLayout.NORTH);

        // Wishlist table setup
        String[] columnNames = { "Item Name", "Brand", "Price", "Checked Off" };
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable wishlistTable = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 3 ? Boolean.class : String.class;
            }
        };
        wishlistPanel.add(new JScrollPane(wishlistTable), BorderLayout.CENTER);
        setupTableListener(wishlistTable, userWishList);

        // Button panel for add, delete, and wallet buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Item");
        JButton deleteButton = new JButton("Delete");
        JButton walletButton = new JButton("Set Wallet Balance");
        JButton viewFriendsButton = new JButton("View Friends");
        JButton saveButton = new JButton("Save to File");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(walletButton);
        buttonPanel.add(viewFriendsButton);
        buttonPanel.add(saveButton);


        String sep = System.getProperty("file.separator");
        ImageIcon originalCoinImage = new ImageIcon(
                System.getProperty("user.dir") + sep + "images" + sep + "dollar.png");
        Image scaledImage = originalCoinImage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledCoinImage = new ImageIcon(scaledImage);
        walletLabel = new JLabel("$0.00", scaledCoinImage, JLabel.LEADING);
        buttonPanel.add(walletLabel);

        wishlistPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedItem(wishlistTable);
            }
        });

        walletButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWalletDialog();
            }
        });

        // View Friends button action
        viewFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createViewFriendsPanel();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDataToFile();
            }
        });

        return wishlistPanel;

    }

    private void saveDataToFile() {
        try {
            JsonWriter jsonWriter = new JsonWriter("wishlist_data.json");
            jsonWriter.open();
            jsonWriter.write(userWishList, userWallet, userFriendList);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error: Could not save data.", "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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

    private void openWalletDialog() {
        // Create a new dialog
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
                    userWallet.addMoney(amountToAdd);

                    if (amountToAdd < 0) {
                        JOptionPane.showMessageDialog(walletDialog, "Amount must be positive.", "Input Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        walletBalance += amountToAdd; // Adds the amount to the current balance
                        updateWalletLabel(); // Update the wallet balance label
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

        walletDialog.setVisible(true); // Show the wallet dialog

    }

    private void setupTableListener(JTable table, WishList wishList) {
        table.getModel().addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int row = e.getFirstRow(); // Get the row that was updated
                int column = e.getColumn(); // Get the column that was updated

                // Check if the "Checked Off" column was updated
                if (column == 3) {
                    Boolean checkedOff = (Boolean) table.getValueAt(row, column);
                    String name = (String) table.getValueAt(row, 0);
                    String brand = (String) table.getValueAt(row, 1);
                    String priceString = (String) table.getValueAt(row, 2); // Get the price of the item
                    double itemPrice = Double.parseDouble(priceString.replace("$", "")); // Parse price

                    Wish wish = wishList.findWish(name, brand);

                    if (wish != null) {
                        // Update the isChecked value based on the checkbox state
                        if (checkedOff != null && checkedOff) {
                            wish.markChecked(); // Mark the wish as checked
                        }
                    }

                    if (checkedOff != null && checkedOff) {
                        // If item was checked
                        if (walletBalance >= itemPrice) {
                            walletBalance -= itemPrice; // Deduct the price from wallet
                            updateWalletLabel(); // Update the wallet balance label
                            userWallet.spendMoney(itemPrice);
                        } else {
                            // Insufficient funds, uncheck the item
                            JOptionPane.showMessageDialog(this, "Insufficient funds to check off this item!",
                                    "Insufficient Balance", JOptionPane.ERROR_MESSAGE);
                            table.setValueAt(false, row, column); // Uncheck the item
                        }
                    }
                }
            }
        });
    }

    private void updateWalletLabel() {
        walletLabel.setText("Wallet: $" + String.format("%.2f", walletBalance));
    }

    private void addItem() {
        String name = nameField.getText();
        String brand = brandField.getText();
        String price = priceField.getText();

        if (!name.isEmpty() && !brand.isEmpty() && !price.isEmpty()) {
            double priceValue = Double.parseDouble(price); // Convert to double // Create an Item instance
            userWishList.addWish(name, brand, priceValue); // Add to WishList
            String formattedPrice = "$" + String.format("%.2f", priceValue);
            tableModel.addRow(new Object[] { name, brand, formattedPrice, false });
            nameField.setText("");
            brandField.setText("");
            priceField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedItem(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String name = (String) tableModel.getValueAt(selectedRow, 0);
            String brand = (String) tableModel.getValueAt(selectedRow, 0);
            userWishList.deleteWish(name, brand);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete", "Selection Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private JPanel createViewFriendsPanel() {
        JPanel viewFriendsPanel = new JPanel(new BorderLayout());

        DefaultListModel<String> friendsListModel = new DefaultListModel<>();
        for (Friend friend : userFriendList.getFriendList()) {
            friendsListModel.addElement(friend.getName());
        }
        JList<String> friendsList = new JList<>(friendsListModel);
        JScrollPane scrollPane = new JScrollPane(friendsList);
        viewFriendsPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton selectFriendButton = new JButton("Select Friend");
        JButton addFriendButton = new JButton("Add Friend");
        JButton deleteFriendButton = new JButton("Delete Friend");
        JButton backButton = new JButton("Back");
        buttonPanel.add(selectFriendButton);
        buttonPanel.add(addFriendButton);
        buttonPanel.add(deleteFriendButton);
        buttonPanel.add(backButton);
        viewFriendsPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for selecting a friend
        selectFriendButton.addActionListener(e -> {
            String selectedFriend = friendsList.getSelectedValue();
            if (selectedFriend != null) {
                openFriendsWishlist(selectedFriend);
            } else {
                JOptionPane.showMessageDialog(viewFriendsPanel, "Please select a friend.", "No Friend Selected",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Action listener for adding a new friend
        addFriendButton.addActionListener(e -> {
            String newFriendName = JOptionPane.showInputDialog(viewFriendsPanel, "Enter the new friend's name:");
            if (newFriendName != null && !newFriendName.trim().isEmpty()) {
                friendsListModel.addElement(newFriendName.trim());
                userFriendList.addFriend(newFriendName);
            } else {
                JOptionPane.showMessageDialog(viewFriendsPanel, "Friend's name cannot be empty.", "Input Error",
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
                userFriendList.deleteFriend(selectedFriend);
                friendsListModel.removeElement(selectedFriend.trim());
            }
        });

        // Action listener for back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Wishlist"); // Switch back to the wishlist view
            }
        });

        return viewFriendsPanel;
    }

    private void openFriendsWishlist(String friendName) {
        // Create a new window for the friend's wishlist
        JFrame friendsWishlistWindow = new JFrame(friendName + "'s Wishlist");
        friendsWishlistWindow.setSize(600, 400);
        friendsWishlistWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        friendsWishlistWindow.setLayout(new BorderLayout());

        JLabel friendWalletLabel = new JLabel("Wallet: $" + String.format("%.2f", walletBalance));
        friendsWishlistWindow.add(friendWalletLabel, BorderLayout.NORTH);

        // Table setup for friend's wishlist
        String[] columnNames = { "Item Name", "Brand", "Price", "Checked Off" };
        DefaultTableModel friendTableModel = new DefaultTableModel(columnNames, 0);
        JTable friendWishlistTable = new JTable(friendTableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 3 ? Boolean.class : String.class;
            }
        };
        add(new JScrollPane(friendWishlistTable), BorderLayout.CENTER);
        setupTableListener(friendWishlistTable, userFriendList.getFriend(friendName).getToBuyList());

        friendsWishlistWindow.add(new JScrollPane(friendWishlistTable), BorderLayout.CENTER);

        // Input panel for adding items to friend's wishlist
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField friendNameField = new JTextField();
        JTextField friendBrandField = new JTextField();
        JTextField friendPriceField = new JTextField();
        ((PlainDocument) friendPriceField.getDocument()).setDocumentFilter(new DecimalFilter());

        inputPanel.add(new JLabel("Item Name:"));
        inputPanel.add(friendNameField);
        inputPanel.add(new JLabel("Brand:"));
        inputPanel.add(friendBrandField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(friendPriceField);

        // Button panel for adding and deleting items
        JPanel friendButtonPanel = new JPanel();
        JButton addFriendItemButton = new JButton("Add Item");
        JButton deleteFriendItemButton = new JButton("Delete");

        friendButtonPanel.add(addFriendItemButton);
        friendButtonPanel.add(deleteFriendItemButton);

        friendsWishlistWindow.add(inputPanel, BorderLayout.NORTH);
        friendsWishlistWindow.add(friendButtonPanel, BorderLayout.SOUTH);

        // Action listener for adding items to friend's wishlist
        addFriendItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = friendNameField.getText();
                String brand = friendBrandField.getText();
                String price = friendPriceField.getText();

                if (!itemName.isEmpty() && !brand.isEmpty() && !price.isEmpty()) {
                    double priceValue = Double.parseDouble(price);
                    String formattedPrice = "$" + String.format("%.2f", priceValue);

                    Friend selectedFriend = userFriendList.getFriend(friendName);
                    if (selectedFriend != null) {
                        selectedFriend.getToBuyList().addWish(itemName, brand, priceValue);
                    }
                    friendTableModel.addRow(new Object[] { itemName, brand, formattedPrice, false });
                    friendNameField.setText("");
                    friendBrandField.setText("");
                    friendPriceField.setText("");
                } else {
                    JOptionPane.showMessageDialog(friendsWishlistWindow, "Please fill in all fields", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for deleting items from friend's wishlist
        deleteFriendItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = friendWishlistTable.getSelectedRow();
                if (selectedRow >= 0) {
                    friendTableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(friendsWishlistWindow, "Please select an item to delete",
                            "Selection Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        friendsWishlistWindow.setVisible(true);
    }

}