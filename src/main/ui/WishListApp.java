package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WishListApp extends JFrame {
    private JTextField nameField;
    private JTextField brandField;
    private JTextField priceField;
    private DefaultTableModel tableModel;
    private double walletBalance = 0.0; 
    private JLabel walletLabel;

    public WishListApp() {
        setTitle("Wishlist Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("Item Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Brand:"));
        brandField = new JTextField();
        inputPanel.add(brandField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        ((PlainDocument) priceField.getDocument()).setDocumentFilter(new NumericFilter()); 
        // Filters priceField to only allow numbers
                                                                                        
        inputPanel.add(priceField);

        add(inputPanel, BorderLayout.NORTH);

        // Wishlist table setup
        String[] columnNames = { "Item Name", "Brand", "Price", "Checked Off" };
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable wishlistTable = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 3 ? Boolean.class : String.class;
            }
        };
        add(new JScrollPane(wishlistTable), BorderLayout.CENTER);
        setupTableListener(wishlistTable);

        // Button panel for add, delete, wallet buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Item");
        JButton deleteButton = new JButton("Delete");
        JButton walletButton = new JButton("Set Wallet Balance");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(walletButton);
        String sep = System.getProperty("file.separator");
        ImageIcon originalCoinImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "dollar.png"); 

        Image scaledImage = originalCoinImage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledCoinImage = new ImageIcon(scaledImage);
        walletLabel = new JLabel("$0.00", scaledCoinImage, JLabel.LEADING);
        buttonPanel.add(walletLabel);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
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
                openWalletDialog(); // Open the wallet balance dialog
            }
        });
    }

    private void addItem() {
        String name = nameField.getText();
        String brand = brandField.getText();
        String price = priceField.getText();

        if (!name.isEmpty() && !brand.isEmpty() && !price.isEmpty()) {
            String formattedPrice = "$" + price;
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
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete", "Selection Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Open the wallet balance dialog
    private void openWalletDialog() {
        // Create a new dialog
        JDialog walletDialog = new JDialog(this, "Add Money to Wallet", true);
        walletDialog.setSize(300, 150);
        walletDialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter amount to add ($): ");
        JTextField walletInputField = new JTextField(15);
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

    private void setupTableListener(JTable table) {
        table.getModel().addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int row = e.getFirstRow(); // Get the row that was updated
                int column = e.getColumn(); // Get the column that was updated

                // Check if the "Checked Off" column was updated
                if (column == 3) {
                    Boolean checkedOff = (Boolean) table.getValueAt(row, column);
                    String priceString = (String) table.getValueAt(row, 2); // Get the price of the item
                    double itemPrice = Double.parseDouble(priceString.replace("$", "")); // Parse price

                    if (checkedOff != null && checkedOff) {
                        // If item was checked
                        if (walletBalance >= itemPrice) {
                            walletBalance -= itemPrice; // Deduct the price from wallet
                            updateWalletLabel(); // Update the wallet balance label
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

    // Updates the wallet label
    private void updateWalletLabel() {
        walletLabel.setText("Wallet: $" + String.format("%.2f", walletBalance));
    }

    // DocumentFilter to allow only numeric input in priceField
    private static class NumericFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && string.matches("\\d*\\.?\\d*")) { // Allow digits and one decimal point
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text != null && text.matches("\\d*\\.?\\d*")) { // Allow digits and one decimal point
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

}
