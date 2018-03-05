package listeners;

import core.MatchThread;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineupListener extends MatchListener{

    String[] nilfgaard = {"emhyr","morvran","usurper","calveit","jan"};
    String[] skellige = {"bran","harald","crach","eist"};
    String[] monsters = {"hillock","arachas queen","elder","eredin","dagon","aq"};
    String[] northernRealms = {"foltest","radovid","henselt","adda"};
    String[] scoiatael = {"brouver","eithne","francesca","filavandrel", "fran", "fila"};
    List<String[]> factions;

    public LineupListener(User player1, User player2, MatchThread matchThread) {
        super(player1, player2, matchThread);
        factions = new ArrayList();
        factions.add(nilfgaard);
        factions.add(skellige);
        factions.add(monsters);
        factions.add(northernRealms);
        factions.add(scoiatael);
    }

    @Override
    protected String getRequestString() {
        return "Send me your leaders, separated with a comma.";
    }

    @Override
    protected String getMessageInvalidString() {
        return "Invalid lineup. Please insert a valid lineup.";
    }

    @Override
    protected boolean isInputValid(String contentRaw) {
        contentRaw = contentRaw.trim();
        List<String> leaders = Arrays.asList(contentRaw.split(","));
        if(leaders.size()!=4){
            return false;
        }
        int differentFactions=0;
        for (String[] faction: factions) {
            for (String leader:leaders) {
                if (Arrays.asList(faction).contains(leader.toLowerCase().trim())){
                    differentFactions++;
                    break;
                }
            }
        }

        return differentFactions==4;
    }

    @Override
    public String getAnnouncementString(){
        return getLineupAnnouncement(player1)+"\n"+
                getLineupAnnouncement(player2);
    }

    public String getLineupFor(User user){
        return "Your opponents leaders: "+ playerInputs.get(user);
    }

    private String getLineupAnnouncement(User player){
        return "Leaders for " + player.getAsMention() + ": " + playerInputs.get(player);
    }

}
