package smartbox;

import mvc.Command;
import mvc.Model;

import javax.swing.*;

public class RemoveCommand extends Command {
    public RemoveCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() throws Exception {
        Container container = (Container) model;
        String userInput = JOptionPane.showInputDialog(null, "Component name?", "Input", JOptionPane.QUESTION_MESSAGE);
        container.remComponent(userInput);
    }
}
