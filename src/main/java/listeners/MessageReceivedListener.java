package listeners;

import core.MatchThread;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.JDAImpl;
import net.dv8tion.jda.core.entities.impl.UserImpl;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageReceivedListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {

            String content = event.getMessage().getContentRaw();
            try {
                if (content.startsWith("!start")) {
                    String[] players = content.split(" +");
                    players[1] = players[1].replace("<@", "");
                    players[1] = players[1].replace(">", "");
                    players[1] = players[1].replace("!", "");
                    players[2] = players[2].replace("<@", "");
                    players[2] = players[2].replace(">", "");
                    players[2] = players[2].replace("!", "");

                    User player1 = new UserImpl(Long.parseLong(players[1]), (JDAImpl) event.getJDA());
                    User player2 = new UserImpl(Long.parseLong(players[2]), (JDAImpl) event.getJDA());
                    new Thread(new MatchThread(player1, player2, event.getTextChannel(), event.getJDA())).start();
                }
            }catch (Exception ex){
                System.out.println("Exception when trying to parse" + content);
                ex.printStackTrace();
            }
        }
    }
}
