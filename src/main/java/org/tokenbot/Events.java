package org.tokenbot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Events extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent e){
        Properties prop = new Properties();
        String fileName = "bot.config";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (IOException ex) {
        }
        if (e instanceof ReadyEvent){
            TextChannel channel = e.getJDA().getTextChannelById(prop.getProperty("bot.TicketChannelID"));
            if (channel != null) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Example Embed with Button")
                        .setDescription("This is an example of an embed message with a button.");

                channel.sendMessageEmbeds(embedBuilder.build()).setActionRow(Button.primary("exampleButton", "Click Me!")).queue();
            } else {
                System.err.println("TextChannel not found. Make sure the provided channel ID is correct.");
            }
            System.out.println("Successfully Online!");
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("exampleButton")) {
            event.reply("Hello :)").queue();
        }
    }
}
