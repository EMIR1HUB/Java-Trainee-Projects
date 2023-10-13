package io.ylab.intensive.lesson03.OrgStructure;

import java.io.File;
import java.io.IOException;

public class OrgStructureParserTest {
    public static void main(String[] args) {
        OrgStructureParser orgStructureParser = new OrgStructureParserImpl();
        String path = "src/main/java/com/suleimanov/day03/OrgStructure/orgStructure.csv";

        try {
            Employee boss = orgStructureParser.parseStructure(new File(path));
            System.out.println(boss);

            System.out.println("\n==Подчененные босса==");
            for(Employee e : boss.getSubordinate()){
                System.out.println(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e){
            throw new RuntimeException("No arguments in the File!!");
        }


    }
}
