import com.github.javafaker.Faker;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PersonalDataGenerator {
    static Logger log = LogManager.getLogger(PersonalDataGenerator.class);

    public static void main(String[] args){
        int userAmount = 0;
        while (userAmount == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter users amount: ");
            if (scanner.hasNext("^[1-9]\\d*")) {
                userAmount = Integer.parseInt(scanner.nextLine());
                scanner.close();
            } else {
                System.out.println("Please enter the digit! Not Zero!");
            }
        }
        System.out.println("Generate " + userAmount + " rows of personal data");

        createFile("result.csv");
        for (int i=0; i<userAmount; i++) {
            Faker faker = new Faker();
            String email = faker.name().firstName() + "@" + faker.company().name().replaceAll(" ","") + ".com";
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String city = faker.address().city();
            String country = faker.address().country();
            // Is it correct format of tel in 9 digits?
            String phone = String.valueOf(faker.number().digits(9));
            String result = String.format("%s,%s,%s,%s,%s,%s",email,firstName,lastName,city,country,phone);
            writeToFileData("result.csv", result);
        }
        System.out.println("File generated. You can found results in result.csv that was created in the same directory as this JAR file located");
    }

    public static void createFile(String fileName){
        File file = new File(fileName);
        try {
            if(file.exists())
            {
                file.delete();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.trace("Can't create temporary file", e);
        }
    }

    public static void writeToFileData(String fileName, String data){
        FileWriter myWriter;
        try {
            myWriter = new FileWriter(fileName, true);
            myWriter.write(data);
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.trace("Something going wrong with writing data to result file", e);
        }
    }
}
