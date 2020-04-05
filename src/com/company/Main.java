package com.company;

public class Main {

    public static void main(String[] args) {
        MessageGeneratorImpl messageGenerator = new MessageGeneratorImpl("Привет {first}, how {second} are {third} you?");
        messageGenerator.generate();
    }
}
