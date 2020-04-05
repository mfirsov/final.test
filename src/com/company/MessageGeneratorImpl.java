package com.company;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageGeneratorImpl implements MessageGenerator {

    private final Map<String, Object> stringObjectMap = new HashMap<>();
    private String text;

    public MessageGeneratorImpl(String text) {
        Map<String, Object> map = new HashMap<>();
        map.put("Object", new ArrayList<>(Arrays.asList("Nested object1", "Nested object2")));
        map.put("Object2", new ArrayList<>(Arrays.asList("Nested object3", "Nested object4")));


        List<Object> objects = new ArrayList<>();
        objects.add(map);
        objects.add("Bobobo");
        objects.add("Yoyoyo");

        stringObjectMap.put("{first}", new ArrayList<>(Arrays.asList("Honey", "Dear", "Darling")));
        stringObjectMap.put("{second}", objects);
        stringObjectMap.put("{third}", "have a nice day");
        this.text = text;
    }

    private List<String> extractPlaceholders() {
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(text);
        List<String> placeholders = new ArrayList<>();
        while (matcher.find()) {
            placeholders.add(matcher.group());
        }
        return placeholders;
    }

    private String messageHandler(Object o, StringJoiner stringJoiner) {
        if (o != null) {
            if (o instanceof String) {
                stringJoiner.add((CharSequence) o);
            } else if (o instanceof List) {
                if (! ((List) o).isEmpty()) {
                    stringJoiner.add(messageHandler(((List) o).get(new Random().nextInt(((List) o).size())), new StringJoiner(" ")));
                }
            } else if (o instanceof Map) {
                if (! ((Map) o).isEmpty()) {
                    Object[] keys = ((Map) o).keySet().toArray();
                    Object key = keys[new Random().nextInt(keys.length)];
                    stringJoiner.add((CharSequence) key);
                    stringJoiner.add(messageHandler(((Map) o).get(key), new StringJoiner(" ")));
                }
            }
        }
        return stringJoiner.toString();
    }

    @Override
    public void generate() {
        extractPlaceholders().forEach(s -> {
            text = text.replace(s, messageHandler(stringObjectMap.get(s), new StringJoiner(" ")));
        });
        System.out.println(text);
    }
}
