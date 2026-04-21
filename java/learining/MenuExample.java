import javax.swing.*;
import java.awt.event.*;

public class MenuExample implements ActionListener{
    JFrame f;
    JMenuBar mb;
    JMenu file, edit, help;
    JMenuItem cut, copy, paste, selectAll, save, save_as;

    JTextArea ta;

    MenuExample(){
        f=new JFrame("NotePad");
        cut=new JMenuItem("cut");
        copy=new JMenuItem("copy");
        paste=new JMenuItem("paste");
        selectAll=new JMenuItem("selectAll");
        save=new JMenuItem("save");
        save_as=new JMenuItem("save_as");

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        save.addActionListener(this);
        save_as.addActionListener(this);

        mb=new JMenuBar();

        file=new JMenu("File");
        edit=new JMenu("Edit");
        help=new JMenu("Help");

        edit.add(cut);
        edit.add(paste);
        edit.add(copy);
        edit.add(selectAll);

        file.add(save);
        file.add(save_as);

        mb.add(file);
        mb.add(edit);
        mb.add(help);

        ta=new JTextArea();

        ta.setBounds(5, 5, 360, 320);
        f.add(mb);
        f.add(ta);
        f.setJMenuBar(mb);
        f.setLayout(null);
        f.setVisible(true);
        f.setSize(400, 400);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==cut)
            ta.cut();
        if(e.getSource()==paste)
            ta.paste();
        if(e.getSource()==copy)
            ta.copy();
        if(e.getSource()==selectAll)
            ta.selectAll();
        if(e.getSource()==save)
             
    }
    public static void main(String[] args) {
        new MenuExample();
    }
}