package smartbox;
import mvc.*;

import java.beans.PropertyChangeEvent;
import java.util.Collection;

public class ContainerView extends View {

    private java.awt.List components;

    public ContainerView(Model model) {
        super(model);
        components = new java.awt.List(10);
        this.add(components);
    }

    public void update() {
        Container container = (Container) model;
        Collection<Component> coms = container.getComponents();
        components.removeAll();
        for (Component component : coms) {
            components.add(component.name);
        }

        this.revalidate();
        this.repaint();
    }
    // etc.
}