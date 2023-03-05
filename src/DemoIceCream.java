import java.sql.*;
import java.util.Scanner;

public class DemoIceCream {
     static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {


        System.out.println("Enter your  choice");

        int choice ;
        do {
            System.out.println("1.Insert the record");
            System.out.println("2.Display the record");
            System.out.println("3.Update records");
            System.out.println("4.delete records");
            System.out.println("5.Quit");
            choice = sc.nextInt();

        }while (choice < 1 || choice > 5);

        switch (choice){
            case 1:
               // DemoIceCream main = new DemoIceCream();
               // main.insertData();
                DemoIceCream.insertData();
                break;
            case 2:
                DemoIceCream.displayData();
                break;
            case 3:
                DemoIceCream.updateData();
                break;
            case 4:
                DemoIceCream.deleteData();
                break;
            case 5:
                System.out.println("Thank you for visiting us");
                break;
            default:
                sc.next();
                break;
        }

    }

    private static void deleteData() {

        try{
            IceCream i = new IceCream();
            int id;
            System.out.println("Enter the icNo to delete");
            id = sc.nextInt();
            i.setIcNo(id);

            Connection connection = getConnect();
            String sql = "DELETE FROM ice WHERE ino =?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, i.getIcNo());

            int rows = pst.executeUpdate();

            pst.close();
            connection.close();

            if(rows > 0){
                System.out.println("Record delete success");
            }
            else {
                System.out.println("Record not found");
            }



        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void updateData() {
        IceCream i  = new IceCream();
        System.out.println("Enter the record number to update");
        int icno = sc.nextInt();
        i.setIcNo(icno);

        System.out.println("Enter the name");
        String icName = sc.next();
        i.setIcName(icName);

        System.out.println("Enter the flavor");
        String iflavor = sc.next();
        i.setFlavor(iflavor);

        System.out.println("Enter the price");
        double iprice = sc.nextDouble();
        i.setPrice(iprice);
         try{
             Connection connection = getConnect();
             String sql = "UPDATE ice SET iname = ?, iflavor = ?, iprice = ? where ino = ?";
             PreparedStatement pst = connection.prepareStatement(sql);
             pst.setString(1, i.getIcName());
             pst.setString(2, i.getFlavor());
             pst.setDouble(3, i.getPrice());
             pst.setInt(4, i.getIcNo());

             int rows = pst.executeUpdate();

             pst.close();
             connection.close();
             if (rows > 0){
                 System.out.println("update success");
             }
             else {
                 System.out.println("record not found");
             }
         }
         catch (Exception e){
             e.printStackTrace();
         }

    }

    private static void displayData() {
        try {
            Connection connection = getConnect();
            String sql = "SELECT * FROM ice";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet result = pst.executeQuery();

            while(result.next()){
                System.out.println(result.getInt(1)+ " "+result.getString(2)+ " "+result.getString(3)+ " "+result.getDouble(4));
            }
            pst.close();
            result.close();
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void insertData() {
        IceCream i =new IceCream();
        System.out.println("Enter the icecream name");
        String iName = sc.next();
        i.setIcName(iName);

        System.out.println("Enter the flavor of icecream");
        String iFlavor = sc.next();
        i.setFlavor(iFlavor);

        System.out.println("Enter the price of flavor");
        double iPrice = sc.nextDouble();
        i.setPrice(iPrice);

        try {
            Connection connection = getConnect();
            String sql = "INSERT INTO ice( iname, iflavor, iprice) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, i.getIcName());
            preparedStatement.setString(2, i.getFlavor());
            preparedStatement.setDouble(3, i.getPrice());

            int row = preparedStatement.executeUpdate();

            if (row > 0){
                System.out.println("Record inserted successfully");
            }

            preparedStatement.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Connection getConnect(){
        String url = "jdbc:mysql://localhost:3306/sampledb";
        String username = "root";
        String password = "1234";
        try {
            Connection connection  = DriverManager.getConnection(url,username, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}