package com.rogue.reginald.command.commands;

import com.rogue.reginald.Reginald;
import com.rogue.reginald.command.Command;
import com.rogue.reginald.command.CommandBase;
import com.rogue.reginald.command.CommandStatus;
import com.rogue.reginald.permission.Permission;

/**
 * Default description
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
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
