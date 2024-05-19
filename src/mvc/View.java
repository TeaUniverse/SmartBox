package mvc;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class View extends JPanel implements Subscriber{
    protected Model model;
    public View(Model model){
        this.model = model;
        this.model.subscribe(this);
        setSize(500, 500);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        setBorder(blackline);
        setBackground(Color.LIGHT_GRAY);
    }
    public void setModel(Model newModel) {
        model.unsubscribe(this);
        model = newModel;
        model.subscribe(this);
        repaint();
    }
    public void update(){
        repaint();
    }
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
    }
}
