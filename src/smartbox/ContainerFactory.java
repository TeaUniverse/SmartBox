package smartbox;

import mvc.AppFactory;
import mvc.Command;
import mvc.Model;
import mvc.View;

public class ContainerFactory implements AppFactory {

    @Override
    public Model makeModel() {
        return new Container();
    }

    @Override
    public View makeView(Model m) {
        return new ContainerView(m);
    }

    @Override
    public String getTitle() {
        return "Smart box";
    }

    @Override
    public String[] getHelp() {
        return new String[] {"Click Add to add component.\nClick Rem to remove component.\nClick Run to run component."};
    }

    @Override
    public String[] getEditCommands() {
        return new String[] {"Add", "Rem", "Run"};
    }

    @Override
    public String about() {
        return "Smart box version 1.0";
    }

    @Override
    public Command makeEditCommand(Model model, String type, Object source) {
        if (type.equalsIgnoreCase("Add")) {
            return new AddCommand(model);
        }
        else if (type.equalsIgnoreCase("Rem")) {
            return new RemoveCommand(model);
        }
        else if (type.equalsIgnoreCase("Run")) {
            return new RunCommand(model);
        }
        return null;
    }
}
