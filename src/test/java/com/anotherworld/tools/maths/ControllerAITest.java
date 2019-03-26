package com.anotherworld.tools.maths;

import com.anotherworld.model.ai.ControllerAI;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;


public class ControllerAITest {

    @Test
    public void ControlTest(){

        ControllerAI aiControl = new ControllerAI(new ArrayList<PlayerData>(Arrays.asList(new PlayerData("Bob",5,0,0, ObjectState.DEAD,0.2f,1),
                new PlayerData("John",5,0,0, ObjectState.IDLE,0.2f,1))),
                new ArrayList<PlayerData>(Arrays.asList(new PlayerData("Sam",5,0,0, ObjectState.DEAD,0.2f,1),
                        new PlayerData("Lol",5,0,0, ObjectState.IDLE,0.2f,1))),
                new ArrayList<BallData>(Arrays.asList(new BallData("2",false,0,0,ObjectState.IDLE,1,1))),
                new Platform(new PlatformData(40,4)),new GameSessionData(1));

        for (int i = 0; i < 60 ; i++){
            aiControl.makeDecision();
        }
    }
}
