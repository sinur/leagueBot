package listeners;

import core.MatchThread;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import utils.MessagingUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class MatchListener extends ListenerAdapter{


    protected final User player1;
    protected final User player2;
    private final MatchThread matchThread;
    protected Map<User,String> playerInputs = new HashMap<>();
    private boolean gotInputs = false;

    public MatchListener(User player1, User player2, MatchThread matchThread) {
        this.player1 = player1;
        this.player2 = player2;
        this.matchThread = matchThread;
        playerInputs.put(player1,null);
        playerInputs.put(player2,null);
        MessagingUtils.sendPrivateMessage(player1,getRequestString());
        MessagingUtils.sendPrivateMessage(player2,getRequestString());
    }

    protected abstract String getRequestString();


    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
         if (shouldAnswerPlayer(event,player1) || shouldAnswerPlayer(event,player2)) {
            String input = event.getMessage().getContentRaw();
            if(isInputValid(event.getMessage())){
                playerInputs.put(event.getAuthor(),input);
                MessagingUtils.sendPrivateMessage(event.getAuthor(),"Thanks :D");
            }else {
                MessagingUtils.sendPrivateMessage(event.getAuthor(),getMessageInvalidString());
            }
        }
        if(playerInputs.get(player1)!=null && playerInputs.get(player2)!=null) {
            synchronized (matchThread){
                gotInputs=true;
                matchThread.notifyAll();
            }
        }
    }

    private boolean shouldAnswerPlayer(PrivateMessageReceivedEvent event,User player){
        return event.getAuthor().getId().equals(player.getId())&&playerInputs.get(player)==null;
    }

    protected abstract String getMessageInvalidString();

    protected abstract boolean isInputValid(Message message);

    public abstract String getAnnouncementString();

    public boolean hasGottenInputs() {
        return gotInputs;
    }
}
