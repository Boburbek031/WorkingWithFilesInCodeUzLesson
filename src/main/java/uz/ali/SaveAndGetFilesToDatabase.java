package uz.ali;

import java.io.*;
import java.sql.*;

public class SaveAndGetFilesToDatabase {
    public static void main(String[] args) {

//        insertImage(new File("src/main/resources/filesThatGoToTheDatabase/img.png"));
//        retrieveImage();
//        insertFile(new File("files/test.txt"));
        retrieveFile();

    }

    public static void insertImage(File file) {
        try (Connection con = DatabaseUtil.getConnection()) {
            // save image
//             file = new File("src/main/resources/files/img.png");
            FileInputStream fis = new FileInputStream(file);
            String sql = "insert into image_attach (f_name, f_type, f_data) values(?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, "2 - Image");
            preparedStatement.setString(2, "png");
            preparedStatement.setBinaryStream(3, fis); // fis ==> file input stream
            preparedStatement.executeUpdate(); // Insert qilganimiz uchun executeUpdate() ni ishlatamiz.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void retrieveImage() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select f_name, f_type, f_data from image_attach");
            while (resultSet.next()) {
                // Bu 1 - step, database dan file ni yoki filelarni olib unlarni byte ko'rinishiga olib keldik
                String fName = resultSet.getString("f_name");
                String fType = resultSet.getString("f_type");
                InputStream inputStream = resultSet.getBinaryStream("f_data");

                byte[] bytes_of_file = new byte[inputStream.available()];
                inputStream.read(bytes_of_file);

                // 2 - step da byte ni olib kelgan file type bilan bir xil bo'lgan bitta file ochamiz fa o'sha
                // file ga byte ni yozib saqlab qo'yamiz va ish bitdi.
                File file = new File("src/main/resources/filesThatComeFromDatabase/"
                        + fName + "_come_from_db" + "." + fType);
                // src/main/resources/filesThatComeFromDatabase/img_1__come_from_db.png // fileni yaratib oldik
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(bytes_of_file);
                outputStream.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertFile(File file) {
        try (Connection con = DatabaseUtil.getConnection()) {
            String fileName = file.getName();
            int dotIndex = file.getName().lastIndexOf('.');

            FileInputStream fileInputStream = new FileInputStream(file);
            String sql = "insert into image_attach (f_name, f_type, f_data) values(?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql);


            preparedStatement.setString(1, fileName.substring(0, dotIndex));
            preparedStatement.setString(2, fileName.substring(dotIndex));
            preparedStatement.setBinaryStream(3, fileInputStream);
            preparedStatement.executeUpdate(); // Insert qilganimiz uchun executeUpdate() ni ishlatamiz.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void retrieveFile() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from image_attach");
            while (resultSet.next()) {
                // Bu 1 - step, database dan file ni yoki filelarni olib unlarni byte ko'rinishiga olib keldik
                String fName = resultSet.getString("f_name");
                String fType = resultSet.getString("f_type");
                InputStream inputStream = resultSet.getBinaryStream("f_data");

                if (inputStream != null) {
                    byte[] bytes_of_file = new byte[inputStream.available()];
                    inputStream.read(bytes_of_file);

                    // 2 - step da byte ni olib kelgan file type bilan bir xil bo'lgan bitta file ochamiz fa o'sha
                    // file ga byte ni yozib saqlab qo'yamiz va ish bitdi.
                    File file = new File("files/" + fName + "_come_from_db" + "." + fType);
                    // src/main/resources/filesThatComeFromDatabase/img_1__come_from_db.png // fileni yaratib oldik
                    OutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(bytes_of_file);
                    outputStream.close();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}