package listeners;

import core.MatchThread;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BanListener extends MatchListener{

    Map<String,List<String>> opposingLineup = new HashMap<>();


    public BanListener(User player1, User player2, MatchThread matchThread, List<String> lineupPlayer1, List<String> lineupPlayer2) {
        super(player1, player2, matchThread);
        opposingLineup.put(player1.getId(),lineupPlayer2);
        opposingLineup.put(player2.getId(),lineupPlayer1);
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
    protected boolean isInputValid(Message message)
    {
        List<String> lineup = opposingLineup.get(message.getAuthor().getId());
        String ban = message.getContentRaw().trim().toLowerCase();
        return lineup.stream().anyMatch(str -> str.trim().toLowerCase().equals(ban));
    }

    @Override
    public String getAnnouncementString() {
        return player1.getAsMention() + " bans " + playerInputs.get(player1)+"\n"+
                player2.getAsMention() + " bans " + playerInputs.get(player2);
    }
}
