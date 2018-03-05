import listeners.MessageReceivedListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    //Insert the token of your bot-account here
    private static final String TOKEN = "";

    public static void main(String[] args){
        try {
            JDA jda  = new JDABuilder(AccountType.BOT).setToken(TOKEN).buildAsync();
            jda.addEventListener(new MessageReceivedListener());
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
