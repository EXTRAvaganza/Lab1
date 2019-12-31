package USER;
import Controller.Controller;

import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
    public UserInterface() throws IOException, ClassNotFoundException {
        Controller controller = new Controller();
        System.out.println("Добрый день, Вас приветствует информационная система:\" Отдел кадров \"");
        System.out.println("Получить информацию -h");
        System.out.println("Введите запрос:");
        int end = 0;
        while(end!=-1)
        zapros(controller);
    }
    public void zapros(Controller link) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String temp = scanner.nextLine();
        link.zapros(temp);
    }
}
