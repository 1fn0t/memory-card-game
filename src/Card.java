import javax.swing.*;

public class Card extends JButton {
    private ImageIcon back = new ImageIcon("src/Images/square.jpg", "hi");
    private ImageIcon front;

    public Card(String str, String name) {
        this.front = new ImageIcon(str, name);
    }
    public ImageIcon getBack() {

        return this.back;
    }
    public ImageIcon getFront() {

        return this.front;
    }

}
