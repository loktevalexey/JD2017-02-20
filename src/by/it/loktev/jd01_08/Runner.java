package by.it.loktev.jd01_08;

public class Runner {

    public static void main(String[] args) {

        System.out.println();
        ElectronicDevice dev1=new ElectronicDevice("тостер Samsung",1000);
        System.out.println(dev1);

        //Computer comp=new Computer();

        System.out.println();
        Notebook note1=new Notebook("ноутбук Самсунг",2000, 220);
        System.out.println(note1);
        note1.chargeStart();
        note1.run("c:/program/program.exe");
        note1.pause();
        note1.resume();
        note1.chargeEnd();
        note1.repairBroken(); // статический полимфорфизм
        note1.repairBroken(     100); // статический полимфорфизм
        note1.runScreenSaver("c:/program/saver.exe"); // позднее связывание - внутри Computer::runScreenSaver вызывается Notebook::run

        System.out.println();
        MobilePhone phone1=new MobilePhone("смартфон Sony",100, true, true);
        System.out.println(phone1);
        phone1.chargeStart();
        phone1.run("//dev//sdcard//tetris");
        phone1.pause();
        phone1.resume();
        phone1.chargeEnd();
        phone1.repairBroken(); // статический полимфорфизм
        phone1.repairBroken(     500); // статический полимфорфизм
        phone1.runScreenSaver("//dev//sdcard//saver"); // позднее связывание - внутри Computer::runScreenSaver вызывается MobilePhone::run

        // динамический полиморфизм
        System.out.println();
        Computer comp1=new MobilePhone("смартфон HTC",120, true, false);
        System.out.println(comp1);
        comp1.run("//dev//sdcard//digger");
        comp1.pause();
        comp1.resume();
        comp1.repairBroken(); // + статический полимфорфизм
        comp1.repairBroken(     300); // + статический полимфорфизм
        comp1.runScreenSaver("//dev//sdcard//saver"); // + позднее связывание - внутри Computer::runScreenSaver вызывается MobilePhone::run

    }

}
