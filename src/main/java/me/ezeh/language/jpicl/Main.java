package me.ezeh.language.jpicl;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // If you are going to go through my code, please ignore the static abuse and extremely bad design. I'm (probably) going to go through it later
        List<String> arguments = Arrays.stream(args).collect(Collectors.toList());
        if (arguments.contains("--demo")) {
            new JPICLRunner(getTestCode()).execute();
        }
        if(args.length<1){
            System.out.println("Please include the input file name");
            System.exit(0);
        }
        try {
            String code = new String(Files.readAllBytes(Paths.get("./" + args[args.length - 1])));
            if (arguments.contains("--java") || arguments.contains("-j")) {
                // File f = new File(args[args.length - 1] + ".java"); // Not working
                // f.createNewFile();
                // new BufferedWriter(new FileWriter(f)).write(new JPICLRunner(code).getJavaCode()); // Not working
                System.out.println(new JPICLRunner(code).getJavaCode());
            } else {
                new JPICLRunner(code).execute();
            }
        }
        catch (NoSuchFileException e){
            System.out.println("That file was not found");
            System.exit(0);
        }
        catch (AccessDeniedException e){
            System.out.println("You do not have access to that file");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Unable to read the specified file");
            e.printStackTrace();
        }
    }

    private static String getTestCode() {
        ClassLoader classLoader = Main.class.getClassLoader();
        File file = new File(classLoader.getResource("demo.txt").getFile());
        try {
            return readFile(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to load the demo code");
            return "";
        }
    }

    static String readFile(String fileName) {
        return readFile(Main.class.getClassLoader().getResourceAsStream(fileName));
    }

    private static String readFile(InputStream file) {
            Scanner scanner = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
                sb.append("\n");
            }
            String read = sb.toString();
            return read.substring(0, read.length() - 1);

    }
}
