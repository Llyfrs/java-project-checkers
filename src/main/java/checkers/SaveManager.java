package checkers;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SaveManager {
    public void save(Game game) {
        int[] board_state = new int[67];
        Arrays.fill(board_state,0);

        for(Pawn pawn : game.getPawns()) {
            Point2D pos = pawn.getPosition();
            int pointer = (int)(pos.getY() * 8 + pos.getX());
            board_state[pointer] += (pawn.isTeam())  ? 1 : 2;
            board_state[pointer] += (pawn.isQueen()) ? 2 : 0;
            // 1 - White normal Pawn
            // 2 - Black normal Pawn
            // 3 - Black queen Pawn
            // 4 - White queen Pawn
        }

        board_state[64] = game.getScore().getWhiteScore();
        board_state[65] = game.getScore().getBlackScore();

        board_state[66] = game.isRound() ? 1 : 0;



        try(ObjectOutputStream oos  = new ObjectOutputStream(new FileOutputStream("save.obj"))) {
            oos.writeObject(board_state);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load(Game game, Text whiteScoreText, Text blackScoreText) {
        int[] board_state = new int[67];

        try(ObjectInputStream ois  = new ObjectInputStream(new FileInputStream("save.obj"))) {
             board_state = (int[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Image blackPawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("PawnBlack.png" )));
        Image whitePawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("PawnWhite.png")));
        Image crown          = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Crown.png")));

        List<Pawn> pawns = new ArrayList<Pawn>();

        for(int i = 0; i < 66; i++) {
           switch (board_state[i]) {
               case 0:
                   break;
               case 1:
                   pawns.add(new Pawn(i%8, (int)Math.floor(i/8), true,false,game.getCanvas(),whitePawnImage,crown));
                   break;
               case 2:
                   pawns.add(new Pawn(i%8, (int)Math.floor(i/8), false,false,game.getCanvas(),blackPawnImage,crown));
                   break;
               case 3:
                   pawns.add(new Pawn(i%8, (int)Math.floor(i/8), true,true,game.getCanvas(),whitePawnImage,crown));
                   break;
               case 4:
                   pawns.add(new Pawn(i%8, (int)Math.floor(i/8), false,true,game.getCanvas(),blackPawnImage,crown));
                   break;
           }
        }

        Score score = new Score(whiteScoreText,blackScoreText,board_state[64],board_state[65]);

        game.setPawns(pawns);
        game.setScore(score);
        game.setRound(board_state[66] == 1);

        game.updateDrawables();

    }

    public boolean saveExists() {
        File file = new File ("save.obj");
        return file.exists();
    }
}
