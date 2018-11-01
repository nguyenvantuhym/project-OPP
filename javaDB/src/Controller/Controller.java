package Controller;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


import API.Translate;
import DataBase.DBConnect;
import ScrollableJPopupmenu.XCheckedButton;
import ScrollableJPopupmenu.XJPopupMenu;
import view.*;
import view.Card.CardItem;
import view.Card.ListCard;
import view.exam.ExamSelect;
import view.learn.LearnWord;
import view.search.CollectionContent;
import view.search.CollectionResult;
import view.search.WordItem;

import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class Controller implements ActionListener{
     private static Controller instance = null;
    public static Controller getInstance() 
    {
        if(instance ==null)
        {
            instance = new Controller();
            return instance;
        }
        else return instance;
    }
    XJPopupMenu menu;
    XCheckedButton btnKq;
    protected Controller()
    {
        init();
    }
    private void init()
    {

    }
    public  void ActionBtnSearch()
    {
        String dataInput = TopBar.getInstance().textBox.getText().trim().toLowerCase();


            if (dataInput.length() > 0) {


                try {

                        menu = new XJPopupMenu(mainFrame.getInstance());
                        String dataOutput = ControllerDB.getInstance().SearchWordByWordEng(dataInput);

                        if (dataOutput.length() > 0) {
                            btnKq= new XCheckedButton(dataOutput);
                            btnKq.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {

                                    menu.hidemenu();
                                    TopBar.getInstance().textBox.setText(((JButton) e.getSource()).getText());
                                    try {
                                        ExplanWord.getInstance().setData(((JButton) e.getSource()).getText(),

                                                ControllerDB.getInstance().SearchExplanByWordEng(((JButton) e.getSource()).getText())
                                        );
                                        CardLayout cardLayout = null;
                                        cardLayout = (CardLayout) view.ContentAreaMenuMain.getInstance().getLayout();
                                        cardLayout.show(view.ContentAreaMenuMain.getInstance(), "explan");

                                    }catch (SQLException er)
                                    {
                                        System.out.println(er);
                                    }
                                }
                            });
                            menu.add(btnKq);

                            menu.show(mainFrame.getInstance(), 233, 75);
                        }

                           ArrayList<String> res= DBConnect.getInstance().Lookup(dataInput);
                            if(res.size()>0) {

                                XCheckedButton btnItem;
                                for (int i = 0; i < res.size(); i++) {
                                    if(!res.get(i).equals(dataInput)) {
                                        btnItem = new XCheckedButton(res.get(i));

                                        btnItem.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {

                                                //System.out.println( ((JButton)e.getSource()).getText());
                                                menu.hidemenu();
                                                TopBar.getInstance().textBox.setText(((JButton) e.getSource()).getText());
                                                try {
                                                    System.out.println(ControllerDB.getInstance().SearchExplanByWordEng(((JButton) e.getSource()).getText()));
                                                    ExplanWord.getInstance().setData(((JButton) e.getSource()).getText(),

                                                            ControllerDB.getInstance().SearchExplanByWordEng(((JButton) e.getSource()).getText())
                                                        );
                                                    CardLayout cardLayout = null;
                                                    cardLayout = (CardLayout) view.ContentAreaMenuMain.getInstance().getLayout();
                                                    cardLayout.show(view.ContentAreaMenuMain.getInstance(), "explan");

                                                }catch (SQLException er)
                                                {
                                                    System.out.println(er);
                                                }
                                            }
                                        });
                                        menu.add(btnItem);
                                    }
                                }
                            }



                        //menu.setPopupSize(new Dimension(499, 500));
                        menu.show(mainFrame.getInstance(), 233, 75);

                        if(dataOutput.length() ==0&&res.size()==0)
                        {
                            menu.hidemenu();

                            System.out.println("éo thấy");
                            String trans = Translate.Translate(dataInput);
                            if (trans.toLowerCase().equals(dataInput)) {

                                JOptionPane.showMessageDialog(mainFrame.getInstance(),
                                        "Không Tìm Thấy Dữ Liệu Phù Hợp\n", "Thông Báo",
                                        JOptionPane.QUESTION_MESSAGE);
                            } else{
                                int kt = JOptionPane.showConfirmDialog(mainFrame.getInstance(),
                                        "Không Tìm Thấy Dữ Liệu Trong DataBase Phù Hợp\n" +
                                                "Kết Quả Từ GoogleTranslate \n" + dataInput +" : "+ trans, "Thông Báo",
                                        0);
                                if(kt ==0)
                                {
                                    ListCard.getInstance().panel.add(new CardItem(dataInput,trans,ListCard.getInstance().panel.getComponentCount()));
                                    ListCard.getInstance().reloadListCard();
                                }

                            }


                        }
                            /**/

                }catch (SQLException | IOException er)
                {
                    System.out.println(er);
                }
            }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        String command = e.getActionCommand();
        if (command.equals("btnSearch")) {
            ActionBtnSearch();

        } else if (command.equals("btnMenuSearch")) {
            CardLayout cardLayout = null;

            try {
                cardLayout = (CardLayout) view.ContentAreaMenuMain.getInstance().getLayout();
                cardLayout.show(view.ContentAreaMenuMain.getInstance(), "search");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }


        } else if (command.equals("card")) {
            CardLayout cardLayout = null;

            try {
                //ListCard.getInstance().loadListCard();
                cardLayout = (CardLayout) view.ContentAreaMenuMain.getInstance().getLayout();
                cardLayout.show(view.ContentAreaMenuMain.getInstance(), "ListCard");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }


        } else if (command.equals("learn")) {
            System.out.println("learn");
            CardLayout cardLayout = null;

            try {

                cardLayout = (CardLayout) view.ContentAreaMenuMain.getInstance().getLayout();
                cardLayout.show(view.ContentAreaMenuMain.getInstance(), "learn");
                LearnWord.getInstance().index=0;
                LearnWord.getInstance().loopLearn();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }


        } else if (command.equals("exam")) {
            System.out.println("exam");
            CardLayout cardLayout = null;

            try {
                ExamSelect.getInstance().loaddata();
                cardLayout = (CardLayout) view.ContentAreaMenuMain.getInstance().getLayout();
                cardLayout.show(view.ContentAreaMenuMain.getInstance(), "exam");

            } catch (SQLException e1) {
                e1.printStackTrace();
            }


        }  else if (command.equals("About")) {
            System.out.println("About");
            CardLayout cardLayout = null;

            try {
                ExamSelect.getInstance().loaddata();
                cardLayout = (CardLayout) view.ContentAreaMenuMain.getInstance().getLayout();
                cardLayout.show(view.ContentAreaMenuMain.getInstance(), "About");

            } catch (SQLException e1) {
                e1.printStackTrace();
            }


        } else if (command.startsWith("ShowExplan#", 0)) {

                try {
                    for(int i =0; i < CollectionContent.getInstance().panel.getComponentCount(); i++)
                    {
                        if(command.equals("ShowExplan#"+i))
                        {
                            String wordEng =  ((WordItem)CollectionContent.getInstance().panel.getComponent(i)).getTextValue();
                            System.out.println(ControllerDB.getInstance().SearchExplanByWordEng(wordEng));
                            ExplanWord.getInstance().setData(
                                    wordEng,
                                    ControllerDB.getInstance().SearchExplanByWordEng(wordEng)
                            );

                        }
                    }
                    CardLayout cardLayout = null;
                    cardLayout = (CardLayout) view.ContentAreaMenuMain.getInstance().getLayout();
                    cardLayout.show(view.ContentAreaMenuMain.getInstance(), "explan");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            /*for (int i = 0; i < Dictionary.getInstance().size(); i++) {
                if (command.equals("addWordItem#" + i)) {
                    System.out.println("add " + i);
                    MessgeEditAndFavorites.getInstance().Title.setText("Thêm Thẻ Mới");
                    MessgeEditAndFavorites.getInstance().isAdd =true;
                    MessgeEditAndFavorites.getInstance().setIndexCollection(i+1);
                    MessgeEditAndFavorites.getInstance().setVisible(true);
                }
            }*/
        } else if (command.startsWith("editWordItem#", 0)) {


            /*for (int i = 0; i < Dictionary.getInstance().size(); i++) {
                if (command.equals("editWordItem#" + i)) {
                    System.out.println("edit " + i);
                    System.out.println("add " + i);
                    MessgeEditAndFavorites.getInstance().Title.setText("Chỉnh Sửa Thẻ");
                    MessgeEditAndFavorites.getInstance().isAdd =false;
                    MessgeEditAndFavorites.getInstance().setIndexCollection(-i);
                   MessgeEditAndFavorites.getInstance().setVisible(true);
                }
            }*/
        } else if (command.startsWith("addWorditemToFavorits#", 0)) {
            try
            {
                for(int i =0; i < CollectionContent.getInstance().panel.getComponentCount();i++) {
                    if (command.equals("addWorditemToFavorits#" + i))
                    {
                        String inputWord =((WordItem)(CollectionContent.getInstance().panel.getComponent(i))).getTextValue();
                        String res =Translate.Translate(inputWord);
                        ListCard.getInstance().panel.add(new CardItem(inputWord,res,ListCard.getInstance().panel.getComponentCount()));
                        ListCard.getInstance().reloadListCard();
                    }
                }
            }catch (SQLException | IOException er)
            {
                System.out.println(er);
            }
            /*for (int i = 0; i < Dictionary.getInstance().size(); i++) {
                if (command.equals("addWorditemToFavorits##" + i)) {
                    System.out.println("addWorditemToFavorits# " + i);
                     FavoritesWordList.getInstance().add(new Word(
                        Dictionary.getInstance().get(i).getWord_target(),
                        Dictionary.getInstance().get(i).getWord_explain()
                     ));
                }
            }*/
        }
        else if (command.startsWith("deleteWordItem#", 0)) {

            try {
                for(int i =0; i < CollectionContent.getInstance().panel.getComponentCount();i++)
                {
                    if(command.equals("deleteWordItem#"+i))
                    {
                        CollectionContent.getInstance().panel.remove(i);
                        CollectionContent.getInstance().updateComponent();
                        CollectionContent.getInstance().updateUI();
                    }
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        } else if (command.startsWith("addCardItem#", 0)) {


            try {

                for (int i = 0; i < ListCard.getInstance().panel.getComponentCount(); i++)
                {
                    if(command.equals("addCardItem#"+i))
                    {
                        MessgeEditAndFavorites.getInstance().resetAnhViet();
                        MessgeEditAndFavorites.getInstance().setTitle("Thêm Từ");
                        MessgeEditAndFavorites.getInstance().setIndexCollection(-1);
                        MessgeEditAndFavorites.getInstance().setVisible(true);

                        System.out.println("thêm");
                        System.out.println(((CardItem)ListCard.getInstance().panel.getComponent(i)).getTextBtnAnh());
                    }
                }
            }catch (SQLException er)
            {
                System.out.println(er);
            }


        } else if (command.startsWith("editCardItem#", 0)) {


            try {

                for (int i = 0; i < ListCard.getInstance().panel.getComponentCount(); i++)
                {
                    if(command.equals("editCardItem#"+i))
                    {
                        MessgeEditAndFavorites.getInstance().resetAnhViet();
                        MessgeEditAndFavorites.getInstance().setTitle("Sửa Từ");
                        MessgeEditAndFavorites.getInstance().setIndexCollection(i);
                        MessgeEditAndFavorites.getInstance().setVisible(true);
                        MessgeEditAndFavorites.getInstance().setTxBoxAnh(

                                ((CardItem)(ListCard.getInstance().panel.getComponent(i))).getTextBtnAnh()
                        );

                        MessgeEditAndFavorites.getInstance().setTxBoxVi(
                                ((CardItem)(ListCard.getInstance().panel.getComponent(i))).getTextBtnViet()
                        );

                        System.out.println("sửa");
                        System.out.println(((CardItem)ListCard.getInstance().panel.getComponent(i)).getTextBtnAnh());
                    }
                }
            }catch (SQLException er)
            {
                System.out.println(er);
            }

        } else if (command.startsWith("deleteCardItem#", 0)) {
            try {

                for (int i = 0; i < ListCard.getInstance().panel.getComponentCount(); i++)
                {
                    if(command.equals("deleteCardItem#"+i))
                    {
                        System.out.println("Xóa");
                        System.out.println(((CardItem)ListCard.getInstance().panel.getComponent(i)).getTextBtnAnh());
                        ListCard.getInstance().panel.remove(i);
                        ListCard.getInstance().reloadListCard();
                        ListCard.getInstance().panel.updateUI();

                    }
                }
            }catch (SQLException er)
            {
                System.out.println(er);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            /*for (int i = 0; i < Dictionary.getInstance().size(); i++) {
                if (command.equals("editWordItem#" + i)) {
                    System.out.println("edit " + i);
                    System.out.println("add " + i);
                    MessgeEditAndFavorites.getInstance().Title.setText("Chỉnh Sửa Thẻ");
                    MessgeEditAndFavorites.getInstance().isAdd =false;
                    MessgeEditAndFavorites.getInstance().setIndexCollection(-i);
                    MessgeEditAndFavorites.getInstance().setVisible(true);
                }
            }*/
        }


        else if (command.equals("btnOKMessage"))
        {

            if(MessgeEditAndFavorites.getInstance().txBoxVi.getText().length() >0&&
                    MessgeEditAndFavorites.getInstance().txBoxAnh.getText().length()>0)
            {
                try {
                    int index = MessgeEditAndFavorites.getInstance().getIndexCollection();
                    if (index == -1) // index == -1 thao tac thêm du lieu
                    {
                        if(!checkExist(MessgeEditAndFavorites.getInstance().getTxBoxAnh().toLowerCase().trim()))
                        {
                            System.out.println("them nhé :D");
                            ListCard.getInstance().panel.add(new CardItem(
                                    MessgeEditAndFavorites.getInstance().getTxBoxAnh().toLowerCase().trim(),
                                    MessgeEditAndFavorites.getInstance().getTxBoxVi().toLowerCase().trim(),
                                    ListCard.getInstance().panel.getComponentCount()
                            ));
                            ListCard.getInstance().reloadListCard();
                            ListCard.getInstance().updateUI();
                        }
                        else  JOptionPane.showMessageDialog(mainFrame.getInstance(),
                                "Đã Tồn Tại từ này, không được phép thêm\n", "Thông Báo",
                                JOptionPane.QUESTION_MESSAGE);


                    }
                    else {//sửa  data ở vị trí index


                        ((CardItem)(ListCard.getInstance().panel.getComponent(index))).setTextBtnAnh(
                                MessgeEditAndFavorites.getInstance().getTxBoxAnh().toLowerCase().trim()
                        );

                        ((CardItem)(ListCard.getInstance().panel.getComponent(index))).setTextBtnViet(
                                MessgeEditAndFavorites.getInstance().getTxBoxVi().toLowerCase().trim()
                        );
                        ListCard.getInstance().repaint();
                    }
                }catch (SQLException er)
                {
                    System.out.println(er);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                System.out.println(MessgeEditAndFavorites.getInstance().txBoxAnh.getText()+"==> "+
                        MessgeEditAndFavorites.getInstance().txBoxVi.getText());
                MessgeEditAndFavorites.getInstance().resetAnhViet();
                MessgeEditAndFavorites.getInstance().setVisible(false);
                // update phan layout nưa nhé  chưa code :D
            }
        }
    }
    boolean checkExist(String word) throws SQLException {
        for(int i =0; i< ListCard.getInstance().panel.getComponentCount(); i++)
        {
                 if(((CardItem)(ListCard.getInstance().panel.getComponent(i))).getTextBtnAnh().equals(word))
                 {
                     return true;
                 }
        }
        return false;
    }
}
