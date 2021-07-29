import javax.swing.*;

public class Card extends JButton {
    private ImageIcon back = new ImageIcon("src/Images/square.jpg", "hi");
    //The front of the card is not initially in the card but it is applied to the card in the class memory board
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
