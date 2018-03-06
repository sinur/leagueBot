package core;

import listeners.BanListener;
import listeners.LineupListener;
import listeners.MatchListener;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import utils.MessagingUtils;

import javax.sound.sampled.Line;
import java.util.List;

public class MatchThread implements Runnable{

    private final User player1;
    private final User player2;
    private final MessageChannel mainChannel;
    private final JDA jda;

    public MatchThread(User player1, User player2, MessageChannel mainChannel, JDA jda) {
        this.player1 = player1;
        this.player2 = player2;
        this.mainChannel = mainChannel;
        this.jda = jda;
    }

    @Override
    public void run() {
        LineupListener lineupListener = new LineupListener(player1, player2, this);
        handleListener(lineupListener);
        List<String> lineupPlayer1 = lineupListener.getLineupAsList(player1);
        List<String> lineupPlayer2 = lineupListener.getLineupAsList(player2);
        MessagingUtils.sendPrivateMessage(player1,lineupListener.getLineupFor(player2));
        MessagingUtils.sendPrivateMessage(player2,lineupListener.getLineupFor(player1));
        MatchListener banListener = new BanListener(player1,player2,this,lineupPlayer1,lineupPlayer2);
        handleListener(banListener);
    }

    private void handleListener(MatchListener lineupListener) {
        jda.addEventListener(lineupListener);
        try {
            synchronized (this){
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mainChannel.sendMessage(lineupListener.getAnnouncementString()).queue();
        jda.removeEventListener(lineupListener);
    }

}
