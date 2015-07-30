package com.example.clientrobotclementoni.service.robot;


/**
 * Created by daniele on 30/07/15.
 */
public class RobotCommand {

    /**
     * Available commands
     */
    public static enum CommandType{
        MOVEMENT,
        LED,
        SOUND
    };

    /**
     * Movement Direction and Type
     */
    public static enum MovementType{
        UP(0),
        DOWN(1),
        LEFT(2),
        RIGHT(3),
        STOP(5);

        private int _v;

        MovementType(int v){
            this._v = v;
        }

        int getValue(){
            return _v;
        }
    }

    /**
     * Led Type
     */
    public static enum LedType{
        LED_0(0);

        protected int _v;

        LedType(int v){
            this._v = v;
        }

        int getValue(){
            return _v;
        }
    }


    /**
     * Sound Type
     */
    public static enum SoundType{
        BEEP(0);

        protected int _v;

        SoundType(int v){
            this._v = v;
        }

        int getValue(){
            return _v;
        }
    }

    //MOVEMENT MAX SPEED
    public static int MAX_SPEED = 3;

    protected CommandType commandType;
    protected MovementType movementType;
    protected SoundType soundType;
    protected LedType ledType;
    protected int movement_speed;
    protected boolean led_status;

    public RobotCommand(){

    }

    protected String toStringMovement(){
        return "12D"+movementType.getValue()+"S"+movement_speed;
    }

    protected String toStringLed(){
        return "56"+soundType.getValue()+"E";
    }

    protected String toStringSound(){
        return "78"+ledType.getValue();
    }

    @Override
    public String toString() {
        if(commandType==CommandType.MOVEMENT){
            return toStringMovement();
        }else if(commandType==CommandType.LED){
            return toStringLed();
        }else if(commandType==CommandType.SOUND){
            return toStringSound();
        }
        return null;
    }

    /**
     * Generates Movement Command
     * @param movementType MovementType
     * @param speed movement speed
     * @return RobotCommand
     */
    public static RobotCommand generateMovementCommand(MovementType movementType, int speed){
        speed = speed > MAX_SPEED ? MAX_SPEED:speed;
        speed = speed < 0 ? 0: speed;
        RobotCommand command = new RobotCommand();
        command.commandType = CommandType.MOVEMENT;
        command.movementType = movementType;
        command.movement_speed = speed;
        return command;
    }

    /**
     * Generate Led ON/OFF command
     * @param ledType Led Type
     * @param lenOn TRUE if on, FALSE otherwise
     * @return
     */
    public static RobotCommand generateLedCommand(LedType ledType,boolean lenOn){
        RobotCommand command = new RobotCommand();
        command.ledType = ledType;
        return command;
    }

    /**
     * Generates Sound Command
     * @param soundType Sound Type
     * @return
     */
    public static RobotCommand generateSoundCommand(SoundType soundType){
        RobotCommand command = new RobotCommand();
        command.soundType = soundType;
        return command;
    }

}
