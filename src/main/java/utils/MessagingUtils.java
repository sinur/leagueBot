package utils;

import net.dv8tion.jda.core.entities.User;

public abstract class MessagingUtils {

    public static void sendPrivateMessage(User user, String message) {
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(message).queue();
        });
    }
}
