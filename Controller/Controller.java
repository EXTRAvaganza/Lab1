package Controller;

import Model.*;
import View.View;

import java.io.IOException;
import java.util.Date;

public class Controller {
    public Controller()
    {

    }
    public void zapros(String stroka) throws IOException, ClassNotFoundException {
        String[] subStr;
        subStr = stroka.split(" ");
        switch(subStr[0])
        {
            case("-help"):
            case("-h"):
            {
                String info =
                        "-h - помощь \n-time - текущее время" +
                        "\n-create - создает новый объект " +
                            "\n\t-department [Имя отдела] [ID начальника] - отдел с соответстующими аргументам данными"+
                            "\n\t-employee [Имя] [Фамилия] [Отчество] [Номер отдела] [Телефон] [Зар.плата] - сотрудника с соответстующими аргументам данными "+
                        "\n-search - поиск" +
                            "\n\t-employee [id|имя|фамилия|отчество|телефон|зарплата|отдел] {value} - либо по ID - сотрудника, либо по указанному атрибуту со значением {value}"+
                            "\n\t-department [id|имя|директор] {value} - либо по ID - отдела, либо по указанному атрибуту со значением {value}"+
                        "\n-show - показать информацию"+
                            "\n\t-employee {id} - о сотруднике с указанным {id}(аналогичен -search -employee [id]), либо всех сотрудников"+
                            "\n\t-department {id} - об отделе с указанным {id}(аналогичен -search -department [id]), либо все отделы "+
                        "\n-delete - удалить" +
                            "\n\t-employee [id] - сотрудника с указанным [id]"+
                            "\n\t-department [id] - отдел с указанным [id]"+
                        "\n-change - изменить"+
                            "\n\t-department [id] [attr] [value] - значение параметра [attr](имя|директор) отдела [id] на новое значение [value]"+
                            "\n\t-employee [id] [attr] [value] - значение параметра [attr](имя|фамилия|отчество|отдел|телефон|зарплата) сотрудника [id] на новое значение [value]";
                View.sent(info);
                break;
            }
            case("-create"):
            {
                switch (subStr[1])
                {
                    case("-department"):
                    {
                        if(subStr.length<4)
                            View.sent("Ошибка формирования запроса.\nДля справки воспользуйтесь -h");
                            else
                        Editor.createDepartment(subStr[2],subStr[3]);
                        break;
                    }
                    case("-employee"):
                    {
                        if(subStr.length<8)
                            View.sent("Ошибка формирования запроса.\nДля справки воспользуйтесь -h");
                        else
                        {
                            Editor.createEmployee(subStr[2],subStr[3],subStr[4],subStr[5],subStr[6],subStr[7]);
                        }
                        break;
                    }
                }
            break;
            }
            case("-show"):
            {
                if (subStr[0].equals("-show") && subStr.length == 1)
                    View.sent("Недостаточно аргументов.\nВоспользуйтесь справкой -h.");
                else
                switch (subStr[1])
                {
                    case("-department"):
                    {
                        if(subStr.length==2)
                        {
                            Editor.showDepartments();
                        } else if (subStr.length==3)
                            {
                            Editor.showDepartment(subStr[2]);
                        }
                        else
                        {
                            View.sent("Неверное количество аргументов команды -show.\nВоспользуйтесь справкой -h");
                        }
                        break;
                    }
                    case("-employee"): {
                        if (subStr.length == 2) {

                            Editor.showEmployees();
                        } else if(subStr.length==3) {
                            Editor.showEmployee(subStr[2]);
                        }else
                        {
                            View.sent("Неверное количество аргументов команды -show.\nВоспользуйтесь справкой -h");
                        }
                        break;
                    }
                    default: {
                        View.sent("Неверный аргумент "+subStr[1]+".\nВоспользуйтесь справкой -h.");
                        break;
                    }
                }
                break;
            }
            case("-delete"):
            {
                switch (subStr[1])
                {
                    case("-department"):
                    {
                        if(subStr.length==3)
                        {
                            Editor.deleteDepartment(subStr[2]);
                        }
                        break;
                    }
                    case("-employee"): {
                        if(subStr.length==3)
                        {
                            Editor.deleteEmployee(subStr[2]);
                        }
                        break;
                    }
                }
            }
            case("-change"):
            {
                switch (subStr[1]){
                    case("-department"):
                    {
                        if(subStr.length==5)
                        {
                            Editor.changeDepartment(subStr[2],subStr[3],subStr[4]);
                        }
                        break;
                    }
                    case("-employee"):
                    {
                        if(subStr.length==5)
                        Editor.changeEmployee(subStr[2],subStr[3],subStr[4]);
                        break;
                    }
                }
                break;
            }
            case("-search"):
            {
                switch (subStr[1]){
                case("-department"):
                {
                    if(subStr.length == 3)
                    {
                        Editor.showDepartment(subStr[2]);
                    }
                    else if(subStr.length==4)
                    {
                        Editor.searchDepartament(subStr[2],subStr[3]);
                    }
                    else
                    {
                        View.sent("Неверное количество аргументов команды -search.\nВоспользуйтесь справкой -h");
                    }
                    break;
                }
                case("-employee"):
                {
                    if(subStr.length == 3)
                    {
                        Editor.showEmployee(subStr[2]);
                    }
                    if(subStr.length==4)
                    {
                        Editor.searchEmployee(subStr[2],subStr[3]);
                    } else
                    {
                        View.sent("Неверное количество аргументов команды -search.\nВоспользуйтесь справкой -h");
                    }
                    break;
                }
            }
            break;
            }
            case("-time"):
            {
                View.sent((new Date()).toString());
                break;
            }
            case(""):break;
            default:
            {
                View.sent("Неверный аргумент: "+subStr[0]+"\nВоспользуйтесь справкой -h.");
            }
        }
    }
}
