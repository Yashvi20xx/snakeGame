import javax.swing.*;

public class Main extends JFrame
{

    Main()
    {
        super("Snake Game");
        add(new Board());
        pack();
        setResizable(false);
        setSize(300,300);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args)
    {
        new Main();
    }
}