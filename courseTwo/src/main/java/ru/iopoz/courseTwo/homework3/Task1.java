package ru.iopoz.courseTwo.homework3;

import java.util.*;

public class Task1 {
    public static void main(String[] args) {
        String startStr = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla luctus libero sit amet lacinia" +
                " sodales. Suspendisse massa nisl, egestas sed quam in, tempor gravida metus. Maecenas non felis et elit" +
                " ultricies tristique. Praesent efficitur tellus vel tellus dapibus faucibus. Cras sapien nunc, mollis " +
                "eget nisi ac, rhoncus pulvinar lorem. Nam in bibendum velit. Duis gravida lectus dui, sed congue felis " +
                "ullamcorper in. Fusce erat mi, ultricies quis risus non, pretium hendrerit diam. Cras ut vulputate est," +
                " sit amet volutpat augue. Curabitur iaculis ligula enim, rhoncus eleifend augue dictum id. " +
                "Praesent ac urna tincidunt elit viverra maximus. Maecenas ornare diam ut aliquet sollicitudin. " +
                "Nullam ac orci ac nisi facilisis fringilla. Nullam rhoncus ex quis lectus porta interdum. Donec ut " +
                "neque eget metus vestibulum fermentum eu eu mauris. Duis quis magna sit amet sem ultricies posuere." +
                " Curabitur id sapien tincidunt, commodo ex non, convallis risus. Fusce eleifend sollicitudin turpis, " +
                "eu posuere elit rhoncus non. Nunc vitae rutrum purus, vulputate tincidunt ligula." +
                "Donec non lorem justo. Etiam tellus ipsum, viverra eu cursus vitae, fermentum id felis. Pellentesque " +
                "finibus lorem id urna fermentum, eget facilisis dolor elementum. Fusce quis nisi sit amet diam " +
                "pulvinar mattis posuere id odio. Nam pellentesque, odio in mollis iaculis, nisi velit accumsan turpis, " +
                "sed pharetra dui ante sit amet arcu. Etiam vitae lacus sed dui elementum dictum non sed tellus. " +
                "Donec eget enim consequat, molestie arcu sit amet, commodo quam. Nullam rhoncus ac diam et feugiat." +
                " Morbi massa mi, porttitor eget laoreet sit amet, bibendum et quam. " +
                "Morbi tempor mi leo, eu tempus ipsum consectetur nec. Proin quis diam quis mi eleifend tempus vitae vel " +
                "urna. Nam tempus ultricies nisi et dictum. Suspendisse eget risus sit amet nisi tincidunt sodales. " +
                "Sed et augue ac magna porta finibus et sit amet odio. Vivamus a orci rutrum, pharetra ligula sed, " +
                "aliquam odio. In lobortis nisl eleifend, auctor ligula sed, hendrerit felis. Nulla tempor, mi id " +
                "accumsan facilisis, odio nisi varius dolor, ut scelerisque nisl diam in lacus. Cras sit amet tincidunt" +
                " libero, rhoncus dictum erat. Suspendisse sed interdum dolor. Etiam vehicula orci urna, eget fermentum " +
                "sapien dignissim in.";
        List<String> strList = new ArrayList<String>(Arrays.asList(startStr.split(" ")));

        Map<String, Integer> hashMap = new HashMap<>();

        for (String word: strList) {
            Integer count = (Integer) getWord(strList, word);
            hashMap.put(word, count);
        }

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": "+ Integer.toString(entry.getValue()));
        }



    }

    protected static Integer getWord(List<String> listWords, String word){
        int count = 1;
        for (String curWord: listWords) {
            if (word.toLowerCase().equals(curWord.toLowerCase())){
                count ++;
            }
        }
        return count;
    }
}
