package com.anotherworld.model.ai;

import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlackBoard {

    public enum AI_State{
        NORMAL,INVULNERABLE
    }

    private static Map<String, String>targetedBalls = new HashMap<>();
    private static Map<String, String> targetedPlayers = new HashMap<>();
    private static Map<String, AI_State> playersStates = new HashMap<>();

    /**
     *
     * @param targetPlayer target player id
     * @param ai ai aid
     * @return
     */
    public static boolean isPlayerTargeted(String targetPlayer, String ai) {
        return !(targetedPlayers.containsKey(targetPlayer) | targetedPlayers.get(targetPlayer).equals(ai));
    }

    public static void targetThePlayer(String targetPlayer, String ai) {
        targetedPlayers.put(targetPlayer,ai);
    }

    public static void stopTargetingPlayer(String targetPlayer, String ai) {
        targetedPlayers.remove(targetPlayer);
    }

    public static void setState(String id, AI_State state){
        playersStates.put(id,state);

    }

    public static void main(){

    }

}
