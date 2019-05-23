package ru.iopoz.courseTwo.homework3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneBook {

    List<Map<String, Integer>> phoneBook = new ArrayList<>();

    public PhoneBook(){}

    public void add(String lName, Integer num){
        Map<String, Integer> row = new HashMap<>();
        row.put(lName, num);
        phoneBook.add(row);
    }

    public Map get(String lName){
        Map<String, ArrayList> record = new HashMap<>();
        List<Integer> numbers = new ArrayList<>();
        for (Map<String, Integer> entry: phoneBook) {
            if (entry.containsKey(lName)){
                numbers.add(entry.get(lName));
            }

        }
        record.put(lName, (ArrayList) numbers);

        return record;
    }
}
