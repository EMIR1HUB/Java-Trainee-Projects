package io.ylab.intensive.lesson03.OrgStructure;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrgStructureParserImpl implements OrgStructureParser {
    private final List<Employee> employeesList = new ArrayList<>();

    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        try (FileReader inputStream = new FileReader(csvFile)) {
            BufferedReader bufferInput = new BufferedReader(inputStream);
            createFromSignatureFile(bufferInput);
            bufferInput.close();
        } catch (FileNotFoundException e) {
            System.out.println("FILE not found in this directory!!!");
        }
        return searchForBoss();
    }

    public void createFromSignatureFile(BufferedReader bufferReader) throws IOException {
        String stringInput;
        while ((stringInput = bufferReader.readLine()) != null) {
            String[] arrString = stringInput.split(";");
            Employee employee = new Employee();

            if (arrString[0].equals("id")) {
                continue;
            } else if (!arrString[1].equals("")) {
                employee.setBossId(Long.valueOf(arrString[1]));
            }
            employee.setId(Long.valueOf(arrString[0]));
            employee.setName(arrString[2]);
            employee.setPosition(arrString[3]);
            searchForBossForEmployee(employee);
            employeesList.add(employee);
        }
    }

    private void searchForBossForEmployee(Employee employee) {
        for (Employee e : employeesList) {
            if (e.getBossId() == employee.getId()) {
                e.setBoss(employee);
                employee.getSubordinate().add(e);
            } else if (e.getId() == employee.getBossId()) {
                employee.setBoss(e);
                e.getSubordinate().add(employee);
            }
        }
    }

    private Employee searchForBoss() {
        for (Employee e : employeesList) {
            if (e.getBossId() == null) {
                return e;
            }
        }
        return null;
    }

}
