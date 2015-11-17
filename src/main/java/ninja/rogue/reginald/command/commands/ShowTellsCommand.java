package ninja.rogue.reginald.command.commands;

import ninja.rogue.reginald.Reginald;
import ninja.rogue.reginald.command.Command;
import ninja.rogue.reginald.command.CommandBase;
import ninja.rogue.reginald.command.CommandInfo;
import ninja.rogue.reginald.command.CommandStatus;
import ninja.rogue.reginald.permission.Permission;

/**
 * Default description
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
@CommandInfo(name = "showtells")
public class ShowTellsCommand extends CommandBase {

    public ShowTellsCommand(Reginald project) {
        super(project);
    }

    @Override
    public CommandStatus execute(Command cmd, String[] args) {
        CommandStatus stat = this.verify(cmd.getUser(), Permission.SHOW_TELLS);
        if (stat == CommandStatus.SUCCESS) {
            this.project.getMessageHandler().readMessages(cmd.getUser());
        }
        return stat;
    }

    @Override
    public String getName() {
        return "showtells";
    }

    @Override
    public String info() {
        return "Shows you your current tells";
    }
}
