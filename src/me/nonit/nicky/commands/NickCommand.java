package me.nonit.nicky.commands;

import me.nonit.nicky.Nick;
import me.nonit.nicky.Nicky;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor
{
    Nicky plugin;

    public NickCommand( Nicky plugin )
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if( ! (sender instanceof Player) )
        {
            runAsConsole( args );
        }
        else if( args.length >= 2 )
        {
            runAsAdmin( sender, args );
        }
        else
        {
            runAsPlayer( sender, args );
        }

        return true;
    }

    @SuppressWarnings("deprecation")
    private void runAsConsole( String[] args )
    {
        if( args.length >= 2  )
        {
            Player receiver = plugin.getServer().getPlayer( args[0] );

            String nickname = ChatColor.translateAlternateColorCodes( '&', args[1] );

            Nick nick = new Nick( plugin, receiver );

            nick.setNick( nickname );
            nick.loadNick();

            receiver.sendMessage( Nicky.getPrefix() + "Your nickname has been set to " + ChatColor.YELLOW + nickname + ChatColor.GREEN + " by console!" );
            plugin.log( receiver.getName() + "'s nick has been set to " + nickname );
        }
        else
        {
            plugin.log( "Usage: /nick <player> <nickname>" );
        }
    }

    @SuppressWarnings("deprecation")
    private void runAsAdmin( CommandSender sender, String[] args )
    {
        Player receiver = plugin.getServer().getPlayer( args[0] );

        String nickname = args[1];

        if( sender.hasPermission( "nicky.set.other" ) )
        {
            if( sender.hasPermission( "nicky.color" ) )
            {
                nickname = ChatColor.translateAlternateColorCodes( '&', nickname );
            }

            Nick nick = new Nick( plugin, receiver );

            nick.setNick( nickname );
            nick.loadNick();

            receiver.sendMessage( Nicky.getPrefix() + "Your nickname has been set to " + ChatColor.YELLOW + nickname + ChatColor.GREEN + " by " + ChatColor.YELLOW + sender.getName() + ChatColor.GREEN + "!" );
            sender.sendMessage( Nicky.getPrefix() + "You have set " + ChatColor.YELLOW + sender.getName() + ChatColor.GREEN + "'s nickname to " + ChatColor.YELLOW + nickname + ChatColor.GREEN + "." );
        }
        else
        {
            sender.sendMessage( Nicky.getPrefix() + ChatColor.RED + "Sorry, you don't have permission to set other players nicks." );
        }
    }

    private void runAsPlayer( CommandSender sender, String[] args )
    {
        Player player = (Player) sender;

        if( sender.hasPermission( "nicky.set" ) )
        {
            if( args.length >= 1 )
            {
                String nickname = args[0];

                if( sender.hasPermission( "nicky.color" ) )
                {
                    nickname = ChatColor.translateAlternateColorCodes( '&', nickname );
                }

                Nick nick = new Nick( plugin, player );

                nick.setNick( nickname );
                nick.loadNick();

                player.sendMessage( Nicky.getPrefix() + "Your nickname has been set to " + ChatColor.YELLOW + nickname + ChatColor.GREEN + " !" );
            }
            else
            {
                player.sendMessage( "To set a nick do " + ChatColor.YELLOW + "/nick <nickname>" );
            }
        }
        else
        {
            player.sendMessage( Nicky.getPrefix() + ChatColor.RED + "Sorry, you don't have permission to set a nick." );
        }
    }
}