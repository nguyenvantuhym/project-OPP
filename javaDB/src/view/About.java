package view;

import javax.swing.*;
import java.awt.*;

public class About extends JPanel {
    private static About instance = null;
    public static About getInstance()
    {
        if(instance == null)
        {
            instance = new About();
            return instance;
        }else
            return instance;
    }

    private About()
    {
        init();
    }
    private void init()
    {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JLabel lbl =new JLabel("bản quyền thuộc về Văn Tú & Ngọc Linh :) ");
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(lbl,BorderLayout.CENTER);

    }

   /* public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 500);
        frame.setLayout(new BorderLayout());

        frame.add(About.getInstance(),BorderLayout.CENTER);
        //frame.setContentPane(panel);
        frame.setVisible(true);

    }*/
}
