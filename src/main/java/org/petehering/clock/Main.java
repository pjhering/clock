package org.petehering.clock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

public class Main implements Runnable
{

    private static final Dimension SIZE = new Dimension(800, 300);
    private static final Color BGCOLOR = Color.BLACK;
    private static final Color TIMECOLOR = Color.GRAY;
    private static final Color DATECOLOR = Color.DARK_GRAY;
    private static final Font LGFONT = new Font("Arial", BOLD, 125);
    private static final Font SMFONT = new Font("Arial", PLAIN, 25);
    private static final SimpleDateFormat DATE = new SimpleDateFormat("EEE, MMM dd, yyyy");
    private static final SimpleDateFormat TIME = new SimpleDateFormat("hh:mm:ss a");
    private static final int DATE_X = 290;
    private static final int DATE_Y = 70;
    private static final int TIME_X = 30;
    private static final int TIME_Y = 200;

    private final Image BUFFER;
    private final JFrame FRAME;
    private final JPanel CANVAS;
    private final Toolkit TK;

    private Main()
    {
        CANVAS = new JPanel()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                g.drawImage(BUFFER, 0, 0, CANVAS);
            }
        };
        CANVAS.setSize(SIZE);
        CANVAS.setPreferredSize(SIZE);
        CANVAS.setMinimumSize(SIZE);
        CANVAS.setMaximumSize(SIZE);

        TK = CANVAS.getToolkit();

        FRAME = new JFrame("Clock");
        FRAME.setContentPane(CANVAS);
        FRAME.pack();
        FRAME.setResizable(false);
        FRAME.setLocationRelativeTo(null);
        FRAME.setDefaultCloseOperation(EXIT_ON_CLOSE);
        FRAME.setVisible(true);

        BUFFER = FRAME.createImage(SIZE.width, SIZE.height);
    }

    public static void main(String[] args)
    {
        Main m = new Main();
        new Thread(m).start();
    }

    @Override
    public void run()
    {
        while (true)
        {
            Date now = new Date();
            String date = DATE.format(now);
            String time = TIME.format(now);

            Graphics2D g = (Graphics2D) BUFFER.getGraphics();
            g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
            g.setColor(BGCOLOR);
            g.fillRect(0, 0, SIZE.width, SIZE.height);

            g.setColor(DATECOLOR);
            g.setFont(SMFONT);
            g.drawString(date, DATE_X, DATE_Y);

            g.setColor(TIMECOLOR);
            g.setFont(LGFONT);
            g.drawString(time, TIME_X, TIME_Y);

            CANVAS.repaint();
            TK.sync();

            try
            {
                Thread.sleep(800);
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
