package listeners;

import core.MatchThread;
import net.dv8tion.jda.core.entities.User;

public class BanListener extends MatchListener{

    public BanListener(User player1, User player2, MatchThread matchThread) {
        super(player1, player2, matchThread);
    }

    @Override
    protected String getRequestString() {
        return "Send me your ban.";
    }

    @Override
    protected String getMessageInvalidString() {
        return "Invalid ban.";
    }

    @Override
    protected boolean isInputValid(String contentRaw) {
        return true;
    }

    @Override
    public String getAnnouncementString() {
        return player1.getAsMention() + " bans " + playerInputs.get(player1)+"\n"+
                player2.getAsMention() + " bans " + playerInputs.get(player2);
    }
}
