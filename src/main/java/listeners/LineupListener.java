package listeners;

import core.MatchThread;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineupListener extends MatchListener{

    String[] nilfgaard = {"emhyr","morvran","usurper","calveit","jan","jan calveit","emhyr var emreis","morvran voorhis","voorhis"};
    String[] skellige = {"bran","harald","crach","eist","king bran","bran tuirseach","crach an craite","eist tuirseach","harald the cripple","cripple"};
    String[] monsters = {"hillock","arachas queen","elder","eredin","dagon","arachas","queen","aq","whispering hillock","unseen elder","unseen","eredin breacc glass","eredin bréacc glas"};
    String[] northernRealms = {"foltest","radovid","henselt","adda","king foltest","rado","raddy","king radovid v","king henselt","princess adda"};
    String[] scoiatael = {"brouver","eithne","francesca","filavandrel", "fran", "fila","brouver hoog","francesca findabair","eithné"};
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
    protected boolean isInputValid(Message message) {
        List<String> leaders = parseLineupString(message.getContentRaw());
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

    private List<String> parseLineupString(String contentRaw) {
        contentRaw = contentRaw.trim();
        return Arrays.asList(contentRaw.split(","));
    }

    @Override
    public String getAnnouncementString(){
        return getLineupAnnouncement(player1)+"\n"+
                getLineupAnnouncement(player2);
    }

    public String getLineupFor(User user){
        return "Your opponents leaders: "+ playerInputs.get(user);
    }

    public List<String> getLineupAsList(User user){
        return parseLineupString(playerInputs.get(user));
    }

    private String getLineupAnnouncement(User player){
        return "Leaders for " + player.getAsMention() + ": " + playerInputs.get(player);
    }


}
