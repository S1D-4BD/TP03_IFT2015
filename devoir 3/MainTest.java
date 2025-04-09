import student_code.reponse_DA_DA;

public class MainTest {
    public static void main(String[] args) {

        String input = "6\n" + "1 2 3\n" +"3 4 5\n" +"4 6\n" +  "2 7\n" +"6 8\n" + "8 13 14\n" +"-1";

        reponse_DA_DA test = new reponse_DA_DA();
        String resultat = test.question_1(input);

        System.out.println("Resultat du test avec mon main:");
        System.out.println(resultat);

    }
}