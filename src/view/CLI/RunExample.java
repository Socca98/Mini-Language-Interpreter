package view.CLI;

import controller.Controller;

public class RunExample extends Command {
    private Controller ctrl;

    public RunExample(String key, String description, Controller c) {
        super(key, description);
        this.ctrl = c;
    }

    @Override
    public void execute() {
        try {
            ctrl.allStep();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
