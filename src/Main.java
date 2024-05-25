import java.io.FileWriter;
import java.io.IOException;

public class Main {

    static Graph graph;
    public static void main(String[] args) {
//        graph = new Graph(5);
//        System.out.println(graph);
//        graph.firstMethod();
//        System.out.println("1--------");
//        graph.secondMethod();
//        System.out.println("\n2-------");
//        graph.thirdMethod();
        String filePath = "output.txt";

        try {
            // Создание объекта FileWriter с указанием пути к файлу
            FileWriter writer = new FileWriter(filePath);
            // Запись строки в файл
            for(int i=0; i<1000; i++){
                graph = new Graph(1000);
                writer.write(String.valueOf(graph.randomDirectionMethod())+"\n");
            }
            // Закрытие потока
            writer.close();
            System.out.println("Запись в файл успешно выполнена!");
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }

    }
}