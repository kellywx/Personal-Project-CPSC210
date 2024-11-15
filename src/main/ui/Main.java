package ui;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            WishListApp app = new WishListApp();
            app.setVisible(true);
        });
    }
}
