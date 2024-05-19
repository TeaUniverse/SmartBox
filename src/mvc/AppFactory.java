package mvc;

public interface AppFactory {
    public Model makeModel();
    public View makeView(Model m);
    public String getTitle();
    public String[] getHelp();
    public String[] getEditCommands();
    public String about();
    public Command makeEditCommand(Model model, String type, Object source);
}
