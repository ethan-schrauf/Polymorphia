package csci.ooad.polymorphia.server.controllers;

import csci.ooad.polymorphia.Polymorphia;
import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class PolymorphiaJsonAdaptor {
    public final String name;
    public final int turn;
    public boolean isInMiddleOfTurn;
    public boolean gameOver;
    public String statusMessage;
    public List<String> livingAdventurers;
    public List<String> livingCreatures;
    public List<RoomJsonAdaptor> rooms ;
    public List<String> availableCommands = new ArrayList<>();

    public PolymorphiaJsonAdaptor(String gameName, Polymorphia polymorphia) {
        name = gameName;
        turn = polymorphia.getTurnNumber();
        rooms = new ArrayList<>();
        for(Room room: polymorphia.getMaze().getRooms()){
            rooms.add(new RoomJsonAdaptor(room));
        }
        isInMiddleOfTurn = false;
        gameOver = false;
        statusMessage = "";
        livingCreatures = polymorphia.getNamesOfLivingCreatures();
        livingAdventurers = polymorphia.getNamesOfLivingAdventurers();
        List<List<Command>> commands = polymorphia.getCommands();
        for(List<Command> command: commands){
            for(Command commandItem: command){
                availableCommands.add(commandItem.toString());
            }
        }
    }

    public static class RoomJsonAdaptor {
        public String name;
        public List<String> neighbors = new ArrayList<>();
        public List<String> contents = new ArrayList<>();

        public RoomJsonAdaptor(Room room) {
            name = room.getName();
            room.getContents();
            if (room.getContents() != null) {
                contents = room.getContents();
            }
            for (Room neighboringRoom : room.getNeighbors()) {
                    neighbors.add(neighboringRoom.getName());
            }

        }
    }

}