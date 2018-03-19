import listeners.MessageReceivedListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {


    public static void main(String[] args){
        try {
            JDA jda  = new JDABuilder(AccountType.BOT).setToken(args[0]).buildAsync();
            jda.addEventListener(new MessageReceivedListener());
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
