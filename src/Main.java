import java.io.FileWriter;
import java.io.IOException;

public class Main {

    static Graph graph;
    static GraphIsland graphIsland;
    public static void main(String[] args) {
//        graph = new Graph(5);
//        System.out.println(graph);
//        graph.firstMethod();
//        System.out.println("1--------");
//        graph.secondMethod();
//        System.out.println("\n2-------");
//        graph.thirdMethod();
        String filePath = "output.txt";

//        graph = new Graph(5, 5 );
//        System.out.println(graph.randomDirectionMethod());
        try {
            // Создание объекта FileWriter с указанием пути к файлу
            FileWriter writer = new FileWriter(filePath);
            // Запись строки в файл
            for(int i=0; i<100; i++){
//                graph = new Graph(1000, 1000);
                System.out.println(i);
                graphIsland = new GraphIsland(200, 200);
                writer.write(String.valueOf(graphIsland.depthFirstSearch())+"\n");
            }
            // Закрытие потока
            writer.close();
            System.out.println("Запись в файл успешно выполнена!");
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }

    }
}