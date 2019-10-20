package provider.ulti;

public class Nationality {
    private static String nationality = "Argentina";

    public static String getNationality() {
        return nationality;
    }

    public static void setNationality(String nationality) {
        Nationality.nationality = nationality;
    }
}
