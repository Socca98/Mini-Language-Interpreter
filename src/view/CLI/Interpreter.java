package view.CLI;

import controller.Controller;
import model.statements.IStmt;
import utils.ProgramGenerator;

public class Interpreter {
    public static void main(String[] args) {

        Controller controllerProgramStates = ProgramGenerator.generatePrograms().get(0);
        IStmt mainStatement = controllerProgramStates.getRepo().getPrgList().get(0).getExeStack().top();

        //Here we initialize the available options and run the program
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExample("1", mainStatement.toString(), controllerProgramStates));
        menu.show();
    }
}

