package ua.wyverno;

import java.io.File;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComparatorFile implements Comparator<File> {


    @Override
    public int compare(File f1, File f2) {

        Pattern pattern = Pattern.compile("(\\d+)");

        Matcher matcherFile1 = pattern.matcher(f1.getName());

        String file1Number = "";
        while (matcherFile1.find()) {
            file1Number = matcherFile1.group();
        }

        Matcher matcherFile2 = pattern.matcher(f2.getName());

        String file2Number = "";
        while (matcherFile2.find()) {
            file2Number = matcherFile2.group();
        }

        int f1Number = Integer.parseInt(file1Number);
        int f2Number = Integer.parseInt(file2Number);

        return f1Number - f2Number;
    }
}
