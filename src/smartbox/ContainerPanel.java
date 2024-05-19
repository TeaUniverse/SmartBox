package smartbox;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;

/*
    Name: John Huynh
    Date: 05/10/2024
    Project: Implementation of Container-Component framework named Smartbox
*/

import javax.swing.*;
import mvc.*;

public class ContainerPanel extends AppPanel {
    java.awt.List components;
    public ContainerPanel(AppFactory factory) {
        // set up controls
        super(factory);

        JButton add = new JButton("Add");
        add.addActionListener(this);
        controlPanel.add(add);

        JButton remove = new JButton("Rem");
        remove.addActionListener(this);
        controlPanel.add(remove);

        JButton run = new JButton("Run");
        run.addActionListener(this);
        controlPanel.add(run);

    }

    // this override needed to re-initialize component fields table:
    public void setModel(Model m) {
        super.setModel(m);
        ((Container) m).initContainer(); // restore fields of components
    }

    public static void main(String[] args) {
        AppPanel panel = new ContainerPanel(new ContainerFactory());
        panel.display();
    }
}