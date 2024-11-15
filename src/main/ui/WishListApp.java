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
    private JLabel walletLabel;
    private WishList userWishList;
    private FriendList userFriendList;
    private Wallet userWallet;
    private CardLayout cardLayout;
    private JPanel cardsPanel;
    private JPanel userWishListPanel;
    private JPanel friendsWishListPanel;

    public WishListApp() {
        setTitle("My Wishlist");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        userWishList = new WishList();
        userFriendList = new FriendList();
        userWallet = new Wallet();
        userFriendList.addFriend("Rachel");
        userFriendList.addFriend("Monica");
        userFriendList.addFriend("Phoebe");

        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        userWishListPanel = createUserWishListPanel();

        // Add the panels to the CardLayout container
        cardsPanel.add(userWishListPanel, "User's Wishlist");

        // Set the initial view to be the user's wishlist
        cardLayout.show(cardsPanel, "User's Wishlist");

        // Add the cardsPanel to the JFrame
        add(cardsPanel, BorderLayout.CENTER);
    }

    private JPanel createInputPanel() {
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

        return inputPanel;
    }

    private void tableListener(JTable table, WishList wishlist) {
        table.getModel().addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int row = e.getFirstRow(); // Get the row that was updated
                int column = e.getColumn(); // Get the column that was updated

                // Check if the "Checked Off" column was updated
                if (column == 3) {
                    Boolean checkedOff = (Boolean) table.getValueAt(row, column);
                    String name = (String) table.getValueAt(row, 0);
                    String brand = (String) table.getValueAt(row, 1);
                    double itemPrice = (Double) table.getValueAt(row, 2);
                    //String priceString = (String) table.getValueAt(row, 2); // Get the price of the item
                    //double itemPrice = Double.parseDouble(priceString.replace("$", "")); // Parse price

                    Wish wish = wishlist.findWish(name, brand);

                    if (checkedOff != null && checkedOff) {
                        if (userWallet.getMoney() >= itemPrice) {
                            userWallet.spendMoney(itemPrice);
                            wish.markChecked();
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

    private JPanel createWalletPanel(double money) {
        JPanel walletPanel = new JPanel(new BorderLayout());
        String sep = System.getProperty("file.separator");
        ImageIcon originalCoinImage = new ImageIcon(
                System.getProperty("user.dir") + sep + "images" + sep + "dollar.png");
        Image scaledImage = originalCoinImage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledCoinImage = new ImageIcon(scaledImage);
        String moneyAdded = "$" + String.format("%.2f", money);
        walletLabel = new JLabel(moneyAdded, scaledCoinImage, JLabel.LEADING);
        walletPanel.add(walletLabel);
        return walletPanel;
    }

    private JPanel createUserWishListPanel() {
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.add(createInputPanel(), BorderLayout.NORTH);

        // Add the table to a JScrollPane and set scroll bar policies
        String[] columnNames = { "Item Name", "Brand", "Price", "Checked Off" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 3 ? Boolean.class : String.class;
            }
        };
        for (Wish wish : userWishList.getWishList()) {
            String name = wish.getName();
            String brand = wish.getBrand();
            double price = wish.getPrice();
            tableModel.addRow(new Object[] { name, brand, price, false });
         }
        JScrollPane scrollPane = new JScrollPane(table);
        userPanel.add(scrollPane, BorderLayout.CENTER);
        tableListener(table, userWishList);

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
        buttonPanel.add(createWalletPanel(userWallet.getMoney()));

        userPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem(userWishList,tableModel);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedItem(userWishList, table, tableModel);
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
                openViewFriendsDialog();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDataToFile();
            }
        });

        return userPanel;

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

                    if (amountToAdd < 0) {
                        JOptionPane.showMessageDialog(walletDialog, "Amount must be positive.", "Input Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        userWallet.addMoney(amountToAdd);
                        createWalletPanel(userWallet.getMoney());
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


    // private void updateWalletLabel(double money) {

    //      walletLabel.setText("$" + String.format("%.2f", walletBalance));
    //  }

    private void addItem(WishList wishlist, DefaultTableModel tableModel ) {
        String name = nameField.getText();
        String brand = brandField.getText();
        String price = priceField.getText();

        if (!name.isEmpty() && !brand.isEmpty() && !price.isEmpty()) {
            double priceValue = Double.parseDouble(price); 
            wishlist.addWish(name, brand, priceValue); // Add to WishList
            String formattedPrice = "$" + String.format("%.2f", priceValue);
            tableModel.addRow(new Object[] { name, brand, formattedPrice, false });
            nameField.setText("");
            brandField.setText("");
            priceField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedItem(WishList wishlist, JTable table, DefaultTableModel tableModel) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String name = (String) tableModel.getValueAt(selectedRow, 0);
            String brand = (String) tableModel.getValueAt(selectedRow, 0);
            wishlist.deleteWish(name, brand);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete", "Selection Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void openViewFriendsDialog() {
        // Create dialog for friends list
        JDialog friendsDialog = new JDialog(this, "View Friends", true);
        friendsDialog.setSize(300, 200);
        friendsDialog.setLayout(new BorderLayout());

        DefaultListModel<String> friendsListModel = new DefaultListModel<>();
        for (Friend friend : userFriendList.getFriendList()) {
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
                userFriendList.addFriend(newFriendName);
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
                userFriendList.deleteFriend(selectedFriend);
                friendsListModel.removeElement(selectedFriend.trim());
            }
        });

        friendsDialog.setVisible(true);
    }

    private JPanel createFriendsWishListPanel(String friendName) {
        JPanel friendsPanel = new JPanel(new BorderLayout());
        setTitle(friendName + "'s To-Buy List");
        friendsPanel.add(createInputPanel(), BorderLayout.NORTH);

        // Wishlist table
        String[] columnNames = { "Item Name", "Brand", "Price", "Checked Off" };
        DefaultTableModel friendTableModel = new DefaultTableModel(columnNames, 0);
        JTable friendTable = new JTable(friendTableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 3 ? Boolean.class : String.class;
            }
        };
        for (Wish wish : userFriendList.getFriend(friendName).getToBuyList().getWishList()) {
            String name = wish.getName();
            String brand = wish.getBrand();
            double price = wish.getPrice();
            friendTableModel.addRow(new Object[] { name, brand, price, false });
         }
        JScrollPane scrollPane = new JScrollPane(friendTable);
        friendsPanel.add(scrollPane, BorderLayout.CENTER);
        tableListener(friendTable, userFriendList.getFriend(friendName).getToBuyList());
 
         // Button panel for adding and deleting items
         JPanel friendButtonPanel = new JPanel();
         JButton addFriendItemButton = new JButton("Add Item");
         JButton deleteFriendItemButton = new JButton("Delete");
        JButton backButton = new JButton("Back");
 
         friendButtonPanel.add(addFriendItemButton);
         friendButtonPanel.add(deleteFriendItemButton);
         friendButtonPanel.add(backButton);
 
         friendsPanel.add(friendButtonPanel, BorderLayout.SOUTH);
 
         // Action listener for adding items to friend's wishlist
         addFriendItemButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 addItem(userFriendList.getFriend(friendName).getToBuyList(), friendTableModel);
             }
         });
 
         // Action listener for deleting items from friend's wishlist
         deleteFriendItemButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                deleteSelectedItem(userFriendList.getFriend(friendName).getToBuyList(), friendTable, friendTableModel);
             }
         });

        // Action listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "User's Wishlist");
                setTitle("My Wishlist");
            }
        });

        return friendsPanel;
    }

    private void openFriendsWishlist(String friendName) {
        friendsWishListPanel = createFriendsWishListPanel(friendName);
        cardsPanel.add(friendsWishListPanel, "Friend's Wishlist");
        cardLayout.show(cardsPanel, "Friend's Wishlist");
    }
}