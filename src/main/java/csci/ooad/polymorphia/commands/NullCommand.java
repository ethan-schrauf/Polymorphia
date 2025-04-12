package csci.ooad.polymorphia.commands;

public class NullCommand extends Command {

    @Override
    public String getName() {
        return "Do Nothing";
    }

    @Override
    public boolean execute() {
        // Do nothing
        return true;
    }

    @Override
    public String getArtifactName() {
        return "";
    }
}
