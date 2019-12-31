package Model;

import Model.Exceptions.duplicateDirector;
import Model.Exceptions.duplicateObject;
import View.View;

import java.io.*;
import java.util.ArrayList;

public class Editor {
    public static void createDepartment(String name,String id_dir) throws IOException, ClassNotFoundException {
        String emptyId = emptyId("1");
        Department temp = new Department();
        if(findEmployee(id_dir).getOtdel()==0 && findEmployee(id_dir)!=null)
        {
            temp.setDirector(id_dir);
            temp.setName(name);
        }
        else
            report("Сотрудник ID "+id_dir+" не находится в отделе переводов(СТОК) или его не существует");
        try {
            checkExistsDepartment(temp);
            changeEmployee(temp.getDirector(),"отдел",emptyId);
            FileOutputStream outputStream = new FileOutputStream("C:\\1_"+emptyId);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(temp);
            objectOutputStream.close();

        }
        catch (duplicateObject ex)
        {
            ex.message("Отдел с указанным именем уже существует.\nВыберите другое имя.");
        }
        catch (duplicateDirector ex)
        {
            ex.message("Заданный сотрудник уже возглавляет другой отдел");
        }
    }
    private static Employee findEmployee(String id) throws IOException, ClassNotFoundException {
     if(new File("C:\\0_"+id).exists())
    {
        FileInputStream fileInputStream = new FileInputStream("C:\\"+"0_"+id);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Employee read = (Employee) objectInputStream.readObject();
        fileInputStream.close();
        objectInputStream.close();
        return read;
    }
    else
        return null;
}
    private static void checkExistsDepartment(Department temp) throws IOException, duplicateDirector, ClassNotFoundException, duplicateObject {
        File file = new File("C:\\");
        String[] test = file.list();
        ArrayList<String[]> stro4ki = new ArrayList<String[]>();
        for (int i = 0; i < test.length; i++)
            stro4ki.add(test[i].split("_"));
        ArrayList<String[]> res = new ArrayList<String[]>();
        for (int i = 0; i < stro4ki.size(); i++)
        {
            if(stro4ki.get(i)[0].equals("1"))
                res.add(stro4ki.get(i));
        }
        for(int i=0;i<res.size();i++) {
            FileInputStream fileInputStream = new FileInputStream("C:\\" + res.get(i)[0] + "_" + res.get(i)[1]);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Department read = (Department) objectInputStream.readObject();
            if (read.getName().equals(temp.getName())) {
                throw new duplicateObject();
            }
            if(read.getDirector().equals(temp.getDirector()))
                throw new duplicateDirector();
        }
    }
    private static String emptyId(String k) {
        File file = new File("C:\\");
        String[] test = file.list();
        ArrayList<String[]> stro4ki = new ArrayList<String[]>();
        for (int i = 0; i < test.length; i++)
            stro4ki.add(test[i].split("_"));
        ArrayList<String[]> res = new ArrayList<String[]>();
        for (int i = 0; i < stro4ki.size(); i++)
        {
            if(stro4ki.get(i)[0].equals(k))
                res.add(stro4ki.get(i));
        }
        boolean flag = false;
        for(int i=0;i<1000;i++)
        {
            flag = false;
            for(int j=0;j<res.size();j++)
            {
                if(Integer.parseInt(res.get(j)[1])==i)
                {
                    flag = true;
                }
            }
            if(!flag)
            {
                return Integer.toString(i);
            }
        }
        return "";
    }
    public static void createEmployee(String firstName,String lastName, String middleName,String depId,String phone,String salary) throws IOException {
        Employee temp = new Employee();
        temp.setFirstName(firstName);
        temp.setLastName(lastName);
        temp.setMiddleName(middleName);
        temp.setOtdel(Integer.parseInt(depId));
        temp.setPhone(phone);
        temp.setSalary(Integer.parseInt(salary));
        try {
            checkExistsEmployee(temp);
            FileOutputStream outputStream = new FileOutputStream("C:\\0_"+emptyId("0"));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(temp);
            objectOutputStream.close();
        }
        catch (duplicateObject ex)
        {
            ex.message("Сотрудник с указанными параметрами уже существует.");
        }
        catch(ClassNotFoundException ex){}
    }
    private static void checkExistsEmployee(Employee temp) throws duplicateObject, IOException, ClassNotFoundException {
        File file = new File("C:\\");
        String[] test = file.list();
        ArrayList<String[]> stro4ki = new ArrayList<String[]>();
        for (int i = 0; i < test.length; i++)
            stro4ki.add(test[i].split("_"));
        ArrayList<String[]> res = new ArrayList<String[]>();
        for (int i = 0; i < stro4ki.size(); i++)
        {
            if(stro4ki.get(i)[0].equals("0"))
                res.add(stro4ki.get(i));
        }
        for(int i=0;i<res.size();i++)
        {
            FileInputStream fileInputStream = new FileInputStream("C:\\"+res.get(i)[0]+"_"+res.get(i)[1]);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Employee read = (Employee) objectInputStream.readObject();
            if(read.equals(temp)) throw new duplicateObject();
            fileInputStream.close();
            objectInputStream.close();
        }
    }
    private static void reportEmployee(Employee ob) {

        String kek =
                "Сотрудник: " +
                        ob.getLastName() + " " +
                        ob.getFirstName() + " " +
                        ob.getMiddleName() + " " + "\n" +
                        "Отдел № " +
                        ob.getOtdel() + "\n" +
                        "Телефон: " +
                        ob.getPhone() +
                        "\nРазмер зарплаты: " +
                        ob.getSalary();
        View.sent(kek);
    }
    public static void showEmployee(String id) throws IOException, ClassNotFoundException {
        if(new File("C:\\0_"+id).exists())
        {
            FileInputStream fileInputStream = new FileInputStream("C:\\"+"0_"+id);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Employee read = (Employee) objectInputStream.readObject();
            report("ID сотрудника:"+ id);
            reportEmployee(read);
            fileInputStream.close();
            objectInputStream.close();
        }
        else
            report("Сотрудник с указанным ID не существует");
    }
    public static void showEmployees() throws IOException, ClassNotFoundException {
        File file = new File("C:\\");
        String[] test = file.list();
        ArrayList<String[]> stro4ki = new ArrayList<String[]>();
        for (int i = 0; i < test.length; i++)
            stro4ki.add(test[i].split("_"));
        ArrayList<String[]> res = new ArrayList<String[]>();
        for (int i = 0; i < stro4ki.size(); i++)
        {
            if(stro4ki.get(i)[0].equals("0"))
                res.add(stro4ki.get(i));
        }
        boolean flag = false;
        for(int i=0;i<res.size();i++)
        {
                FileInputStream fileInputStream = new FileInputStream("C:\\"+res.get(i)[0]+"_"+res.get(i)[1]);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Employee read = (Employee) objectInputStream.readObject();
                report("ID сотрудника:"+ res.get(i)[1]);
                reportEmployee(read);
                fileInputStream.close();
                objectInputStream.close();
                flag = true;
        }
        if(!flag) report("Ни одного сотрудника не обнаружено");
    }
    public static void showDepartment(String id) throws IOException, ClassNotFoundException {
        if(new File("C:\\1_"+id).exists())
        {
        FileInputStream fileInputStream = new FileInputStream("C:\\"+"1_"+id);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Department read = (Department) objectInputStream.readObject();
        report("ID отдела:"+ id);
        reportDeparment(read);
        fileInputStream.close();
        objectInputStream.close();
        }
        else
            report("Отдел с указанным ID не существует");
    }
    public static void showDepartments() throws IOException, ClassNotFoundException {
        File file = new File("C:\\");
        String[] test = file.list();
        ArrayList<String[]> stro4ki = new ArrayList<String[]>();
        for (int i = 0; i < test.length; i++)
            stro4ki.add(test[i].split("_"));
        ArrayList<String[]> res = new ArrayList<String[]>();
        for (int i = 0; i < stro4ki.size(); i++)
        {
            if(stro4ki.get(i)[0].equals("1"))
                res.add(stro4ki.get(i));
        }
        boolean flag = false;
        for(int i=0;i<res.size();i++)
        {
            FileInputStream fileInputStream = new FileInputStream("C:\\"+res.get(i)[0]+"_"+res.get(i)[1]);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Department read = (Department) objectInputStream.readObject();
            report("ID отдела:"+ res.get(i)[1]);
            reportDeparment(read);
            fileInputStream.close();
            objectInputStream.close();
            flag = true;
        }
        if(!flag) report("Ни одного отдела не обнаружено");
    }
    private static void reportDeparment(Department temp) {
        String kek =
                "Отдел:" + temp.getName()+ "\n"+
                        "ID начальника:" + temp.getDirector();
        View.sent(kek);
    }
    private static void report(String result) {
        View view = new View();
        view.sent(result);
    }
    public static void searchDepartament(String attr,String value) throws IOException, ClassNotFoundException {
        boolean flag = false;
        File file = new File("C:\\");
        String[] test = file.list();
        ArrayList<String[]> stro4ki = new ArrayList<String[]>();
        for (int i = 0; i < test.length; i++)
            stro4ki.add(test[i].split("_"));
        ArrayList<String[]> res = new ArrayList<String[]>();
        for (int i = 0; i < stro4ki.size(); i++)
        {
            if(stro4ki.get(i)[0].equals("1"))
                res.add(stro4ki.get(i));
        }
        for(int i=0;i<res.size();i++)
        {
            FileInputStream fileInputStream = new FileInputStream("C:\\"+res.get(i)[0]+"_"+res.get(i)[1]);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Department read = (Department) objectInputStream.readObject();
                switch (attr)
                {
                    case("имя"):
                    {
                    if(read.getName().equals(value))
                    {
                        report("ID отдела:"+ res.get(i)[1]);
                        reportDeparment(read);
                        flag = true;
                    }
                    break;
                    }
                    case("директор"):
                    {
                        if(value.equals(read.getDirector()))
                        {
                            report("ID отдела:"+ res.get(i)[1]);
                            reportDeparment(read);
                            flag = true;
                        }
                        break;
                    }
                    default:
                    {
                        i=res.size();
                        report("Указанный атрибут: "+attr+" не существует"+"\nВоспользуйтесь справкой -h");
                        break;
                    }
                }
            }
        if(!flag) report("Отделы с указанным значением атрибута \""+attr+"\" = "+value+" не существуют");
        }
    public static void deleteDepartment(String id) throws IOException, ClassNotFoundException {

        FileInputStream fileInputStream = new FileInputStream("C:\\"+"1_"+id);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Department read = (Department) objectInputStream.readObject();
        fileInputStream.close();
        objectInputStream.close();
        File file = new File("C:\\1_"+id);
            if(file.delete()) {
            report("ID отдела:" + id);
            reportDeparment(read);
            report("Успешно удалён");
        }
        else {
            report("Отдел с таким ID не существует");
        }
    }
    public static void deleteEmployee(String id) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\"+"0_"+id);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Employee read = (Employee) objectInputStream.readObject();
        fileInputStream.close();
        objectInputStream.close();
        File file = new File("C:\\0_"+id);
        if(file.delete()) {
            report("ID сотрудника:" + id);
            reportEmployee(read);
            report("Успешно удалён");
        }
        else {
            report("Сотрудник с таким ID не существует");
        }
    }
    public static void searchEmployee(String attr,String value) throws IOException, ClassNotFoundException {
                boolean flag = false;
                File file = new File("C:\\");
                String[] test = file.list();
                ArrayList<String[]> stro4ki = new ArrayList<String[]>();
                for (int i = 0; i < test.length; i++)
                    stro4ki.add(test[i].split("_"));
                ArrayList<String[]> res = new ArrayList<String[]>();
                for (int i = 0; i < stro4ki.size(); i++)
                {
                    if(stro4ki.get(i)[0].equals("0"))
                        res.add(stro4ki.get(i));
                }
                for(int i=0;i<res.size();i++)
                {
                    FileInputStream fileInputStream = new FileInputStream("C:\\"+res.get(i)[0]+"_"+res.get(i)[1]);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    Employee read = (Employee) objectInputStream.readObject();
                    switch (attr)
                    {
                        case("имя"):
                        {
                            if(read.getFirstName().equals(value))
                            {
                                report("ID сотрудника:"+ res.get(i)[1]);
                                reportEmployee(read);
                                flag = true;
                            }
                            break;
                        }
                        case("фамилия"):
                        {
                            if(read.getLastName().equals(value))
                            {
                                report("ID сотрудника:"+ res.get(i)[1]);
                                reportEmployee(read);
                                flag = true;
                            }
                            break;
                        }
                        case("отчество"):
                        {
                            if(read.getMiddleName().equals(value))
                            {
                                report("ID сотрудника:"+ res.get(i)[1]);
                                reportEmployee(read);
                                flag = true;
                            }
                            break;
                        }
                        case("телефон"):
                        {
                            if(read.getPhone().equals(value))
                            {
                                report("ID сотрудника:"+ res.get(i)[1]);
                                reportEmployee(read);
                                flag = true;
                            }
                            break;
                        }
                        case("отдел"):
                        {
                            if(read.getOtdel() == Integer.parseInt(value))
                            {
                                report("ID сотрудника:"+ res.get(i)[1]);
                                reportEmployee(read);
                                flag = true;
                            }
                            break;
                        }
                        case("зарплата"):
                        {
                            if(read.getSalary() == Integer.parseInt(value))
                            {
                                report("ID сотрудника:"+ res.get(i)[1]);
                                reportEmployee(read);
                                flag = true;
                            }
                            break;
                        }
                        default:
                        {
                            i=res.size();
                                report("Указанный атрибут: "+attr+" не существует"+"\nВоспользуйтесь справкой -h");
                        }
                    }
                    fileInputStream.close();
                    objectInputStream.close();
        }
                if(!flag) report("Сотрудников с указанным значением атрибута \""+attr+"\" = "+value+" не существует");
    }
    public static void changeDepartment(String id, String attr,String value) throws IOException, ClassNotFoundException {
                FileInputStream fileInputStream = new FileInputStream("C:\\"+"1_"+id);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Department read = (Department) objectInputStream.readObject();
                fileInputStream.close();
                objectInputStream.close();
                switch (attr) {
                    case ("имя"): {
                        read.setName(value);
                        break;
                    }
                    case ("директор"): {
                        read.setDirector(value);
                        break;
                    }
                }
                FileOutputStream outputStream = new FileOutputStream("C:\\1_"+id);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(read);
                objectOutputStream.close();
    }
    public static void changeEmployee(String id,String attr,String value) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\"+"0_"+id);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Employee read = (Employee) objectInputStream.readObject();
        fileInputStream.close();
        objectInputStream.close();
        switch (attr) {
            case ("имя"): {
                read.setFirstName(value);
                break;
            }
            case ("фамилия"): {
                read.setLastName(value);
                break;
            }
            case("отчество"):
            {
                read.setMiddleName(value);
                break;
            }
            case("отдел"):
            {
                read.setOtdel(Integer.parseInt(value));
                break;
            }
            case("зарплата"):
            {
                read.setSalary(Integer.parseInt(value));
                break;
            }
            case("телефон"):
            {
                read.setPhone(value);
                break;
            }
        }
        FileOutputStream outputStream = new FileOutputStream("C:\\0_"+id);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(read);
        objectOutputStream.close();
    }
}

