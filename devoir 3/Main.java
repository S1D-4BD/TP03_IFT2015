import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        String folderPath = "devoir 3/student_code";

        List<List<String>> params = List.of(
                List.of(
                        "13\n13 9 4\n9 24 27\n24 8 14\n-1",
                        "8\n13 9 4\n9 24 27\n24 8 14\n-1",
                        "24\n13 9 4\n9 24 27\n24 8 14\n-1",

                        "6\n1 2 3\n3 4 5\n4 6\n2 7\n6 8\n8 13 14\n-1",
                        "6\n1 2 3\n4 6\n2 7\n3 4 5\n6 8\n8 13 14\n-1",
                        "7\n1 2 3\n3 4 5\n4 6\n2 7\n6 8\n8 13 14\n-1"
                ),
                List.of(
                        "200$ Collier\n200$ Bracelet\n100$ Bague",
                        "120$ Bracelet\n200$ Bague\n250$ Collier\n70$ Boucles d'oreilles\n100$ Broche\n50$ Montre\n300$ Pendentif\n350$ Diadème"
                ),
                List.of(
                        "A 5 #de0716\nA 10 #c20606\nA 15 #ae0b0b\nA 20 #9b0606\nA 25 #810202\nA 30 #e12929\nA 35 #b82b2b\nA 40 #9c0909\nA 45 #f60303\nS 40\nS 20\n S 25\nA 14 #f60313\nA 17 #f70313\nS 10"
                )
        );

        List<List<String>> expectedAnswers = List.of(
                List.of( "13", "8 24 9 13", "24 9 13", "6 4 3 1", "6 4 3 1", "7 2 1"),
                List.of("Collier\nBracelet\nBague", "Diadème\nPendentif\nCollier\nBague\nBracelet\nBroche\nBoucles d'oreilles\nMontre"),
                List.of("#9c0909 4\n#9b0606 4\n#810202 3\n#c20606 4")
        );


        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".java"));
        if (files == null || files.length == 0) {
            System.out.println("Aucun fichier trouvé dans le dossier " + folderPath);
            return;
        }
        for (File file : files) {
            String fileName = file.getName();
            String className = fileName.replace(".java", "");
            try {
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                int result = compiler.run(null, null, null, file.getPath());
                if (result != 0) {
                    System.out.println("Erreur de compilation pour " + className + "");
                    continue;
                }
                File classFolder = new File(folderPath);
                URL[] urls = { classFolder.toURI().toURL() };
                URLClassLoader classLoader = new URLClassLoader(urls);
                Class<?> studentClass = classLoader.loadClass("student_code." +className); //////////////
                Object studentInstance = studentClass.getDeclaredConstructor().newInstance();
                for (int questionIndex = 0; questionIndex < params.size(); questionIndex++) {
                    String methodName = "question_" + (questionIndex + 1);
                    List<String> examples = params.get(questionIndex);
                    List<String> expected = expectedAnswers.get(questionIndex);
                    System.out.println("Évaluation pour " + methodName + " :");
                    for (int exampleIndex = 0; exampleIndex < examples.size(); exampleIndex++) {
                        String input = examples.get(exampleIndex);
                        String expectedAnswer = expected.get(exampleIndex);
                        try {
                            Method method = studentClass.getMethod(methodName, String.class);
                            String studentAnswer = (String) method.invoke(studentInstance, input);
                            if (studentAnswer.equalsIgnoreCase(expectedAnswer)) {
                                System.out.println("  Exemple " + (exampleIndex + 1) + ": Bon");
                            } else {
                                System.out.println("  Exemple " + (exampleIndex + 1) + ": Mauvais (Attendu: " + expectedAnswer + ", Obtenu: " + studentAnswer + ")");
                            }
                        } catch (NoSuchMethodException e) {
                            System.out.println("  Méthode " + methodName + " non trouvée pour " + className);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Erreur " + className + " : " + e.getMessage());
            }
        }
    }
}