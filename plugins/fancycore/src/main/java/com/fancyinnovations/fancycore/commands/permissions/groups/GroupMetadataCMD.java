package com.fancyinnovations.fancycore.commands.permissions.groups;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

public class GroupMetadataCMD extends AbstractCommandCollection {

    public GroupMetadataCMD() {
        super("metadata", "Manages custom metadata for player groups");
        requirePermission("fancycore.commands.groups.metadata");

        addSubCommand(new GroupMetadataListCMD());
        addSubCommand(new GroupMetadataSetCMD());
        addSubCommand(new GroupMetadataRemoveCMD());
        addSubCommand(new GroupMetadataClearCMD());
    }

}
