import java.sql.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
public class ExampleData {


    public static void makeCustomers(Connection conn) throws FileNotFoundException {
        int num = 1;
        int index;
        File myObj = new File("/Users/jacobkopacz/Documents/IdeaProjects/csc436_ims/src/txt.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            index = data.indexOf(',');
            String address = data.substring(0, index);

            data = data.substring(index+1);
            index = data.indexOf(',');
            String city = data.substring(0, index);

            data = data.substring(index+1);
            index = data.indexOf(',');
            String state = data.substring(0, index);

            data = data.substring(index+1);

            String email = "cus" + num + "@gmail.com";

            CustomerDb.insert(conn, email, Integer.toString(num));
            CustomerDb.MailingList(conn, email, address, city, state, data);
            num++;
        }
    }

    public static void makeSupplier(Connection conn) throws FileNotFoundException {
        int num = 1;
        int index;
        File myObj = new File("/Users/jacobkopacz/Documents/IdeaProjects/csc436_ims/src/txt.txt");
        Scanner myReader = new Scanner(myObj);
        while (num < 25) {
//            String data = myReader.nextLine();
//            index = data.indexOf(',');
//            String address = data.substring(0, index);
//
//            data = data.substring(index+1);
//            index = data.indexOf(',');
//            String city = data.substring(0, index);
//
//            data = data.substring(index+1);
//            index = data.indexOf(',');
//            String state = data.substring(0, index);
//
//            data = data.substring(index+1);

            String email = "sup" + num + "@gmail.com";
            int quant = (int)(Math.random() * 20);
            SupplierDb.insert(conn, email,0.00,quant);
            //CustomerDb.MailingList(conn, email, address, city, state, data);
            num++;
        }
    }

    public static void makeItems(Connection conn) {

        for (int i = 1; i <= 100; i++) {

            int quant = (int)(Math.random() * 1000);
            double p = (Math.random() * 10000) / 100;

            ItemDb.insert(conn, i, quant, p, "general");
        }

    }

}
