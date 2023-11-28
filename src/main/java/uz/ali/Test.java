package uz.ali;

public class Test {
    public static void main(String[] args) {

        String s = "heloloj.txt";
        int dotIndex = s.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < s.length() - 1) {
            // Extract the file extension substring
            String fileExtension = s.substring(dotIndex);
            System.out.println("File extension: " + fileExtension);
        } else {
            System.out.println("No file extension found or invalid file name format.");
        }



    }
}
