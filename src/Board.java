
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener
{
    private int dots;
    private final int all_dots= 900;
    private final int dot_size= 10;

    private int random_position = 29;
    private int apple_x;
    private int apple_y;
    private boolean inGame = true;
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private final int x[] = new int[all_dots];
    private final int y[] = new int[all_dots];
    Timer timer;


    private Image dot;
    private Image apple;
    private Image head;
    Board()
    {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

        loadImages();
        initGame();
    }

    private void loadImages()
    {
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
        apple = i1.getImage();
        ImageIcon i2= new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        dot = i2.getImage();
        ImageIcon i3= new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
        head = i3.getImage();


    }

    public void initGame()
    {
        dots=3;
        for(int i = 0; i< dots; i++)
        {
            y[i]=50;
            x[i]=50 - i * dot_size;
            locateApple();
            timer = new Timer(160, this);
            timer.start();
        }
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        if(inGame)
        {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else
        {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g)
    {
        String msg = "Game Over!";
        g.drawString(msg, 115, 115);
    }

    public void locateApple()
    {
        int r = (int) (Math.random() * random_position);
        apple_x= r * dot_size;
        r = (int) (Math.random() * random_position);
        apple_y= r * dot_size;
        //System.out.println(r);
    }
    public void move()
    {
        for ( int i = dots; i> 0; i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection)
        {
            x[0] = x[0]-dot_size;
        }
        if(rightDirection)
        {
            x[0] = x[0]+dot_size;
        }
        if(upDirection)
        {
            y[0] = y[0]-dot_size;
        }
        if(downDirection)
        {
            y[0] = y[0]+dot_size;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame)
        {
            checkApple();
            try
            {
                checkCollision();
            }
            catch (InterruptedException ex)
            {
                throw new RuntimeException(ex);
            }
            move();
        }
        repaint();
    }

    private void checkCollision() throws InterruptedException {
        for(int i = dots; i > 0; i--)
        {
            if((i>4) && (x[0] == x[i]) && (y[0] == y[i]))
            {
                inGame = false;
            }
            if(x[0] >=290)
            {
                inGame = false;
            }
            if(y[0] >=290)
            {
                inGame = false;
            }
            if(y[0] <0)
            {
                inGame = false;
            }
            if(x[0] <0)
            {
                inGame = false;
            }
            if(!inGame)
            {
                try {
                    timer.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void checkApple()
    {
        if((x[0] == apple_x) && (y[0] == apple_y))
        {
                dots++;
                locateApple();
        }
    }

    public class TAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && (!rightDirection))
            {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_RIGHT && (!leftDirection))
            {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_UP && (!downDirection))
            {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            if (key == KeyEvent.VK_DOWN && (!upDirection))
            {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}
