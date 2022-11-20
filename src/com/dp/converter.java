package com.dp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class converter {
    private JButton potvrdVzorecButt;
    private JButton vytvorDizajn;
    private JButton vytvorTestbench;
    private JButton potvrdVstup;
    private JButton potvrdVystup;
    private JButton vytvorSkript;

    private JPanel wrapper;
    private JTextField inputName;
    private JTextField inputtBit;
    private JTextField outputtName;
    private JTextField outputtBit;
    private JButton infoButton;
    private JTextField vzorec;
    private JButton vytvoritButton;
    private JCheckBox a4CheckBox;
    private JCheckBox a8CheckBox;
    private JCheckBox a16CheckBox;
    private JCheckBox a32CheckBox;
    private JLabel Premenna;
    private JLabel Hodnota;
    private JLabel ZadajVzorec;
    private JComboBox inputtTyp;
    private JComboBox moznostDosky;
    private JComboBox outputtTyp;
    private JLabel infoInput;
    private JLabel infoOutput;
    private JLabel vyberrDosky;
    private JButton cestaNaVytvorenieProjektuButton;
    private JButton skriptMiesto;
    private JButton testbenchMiesto;
    private JButton dizajnMiesto;
    private JLabel infoin;
    private JLabel infoout;

    ArrayList<String> allBity = new ArrayList<String>();
    ArrayList<String> allPremenne = new ArrayList<String>();
    ArrayList<String> allTypyPremennych = new ArrayList<String>();

    ArrayList<String> vstupPrem = new ArrayList<String>(); // Create an ArrayList object
    ArrayList<String> vstupPremTyp = new ArrayList<String>(); // Create an ArrayList object
    ArrayList<String> bitVstupPrem = new ArrayList<String>(); // Create an ArrayList object

    ArrayList<String> vystupPrem = new ArrayList<String>(); // Create an ArrayList object
    ArrayList<String> vystupPremTyp = new ArrayList<String>(); // Create an ArrayList object
    ArrayList<String> bitVystupPrem = new ArrayList<String>(); // Create an ArrayList object
    ArrayList<String> konecnyVzorec = new ArrayList<String>(); // Create an ArrayList object
    ArrayList<String> isThereInput = new ArrayList<String>();
    ArrayList<String> miesto= new ArrayList<String>();

    public converter() {

        potvrdVzorecButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allPremenne = new ArrayList<String>();
                allTypyPremennych = new ArrayList<String>();
                allBity = new ArrayList<String>();

                String variable = vzorec.getText();

                if (vstupPrem.size()==0){
                    JOptionPane.showMessageDialog(null, "Nezadal si vstupne premenne");
                    return;
                }
                if (vystupPrem.size()==0){
                    JOptionPane.showMessageDialog(null, "Nezadal si vystupne premenne");
                    return;
                }

                if (variable.equals("")){
                    JOptionPane.showMessageDialog(null, "Vyplň políčko");
                    return;
                }

                boolean findAll = true;
                boolean isCorrect = controlOperators(variable);
                if (!isCorrect){
                    JOptionPane.showMessageDialog(null,"zlyVstup");
                    return ;
                }

                boolean kontrolaZatvoriek = kontrolaZatvoriek(variable);
                if (!kontrolaZatvoriek){
                    JOptionPane.showMessageDialog(null,"Oprav zátvorky");
                    return;
                }

                if (operatory(variable.charAt(variable.length()-1))){
                    JOptionPane.showMessageDialog(null,"Konci operatorom");
                    return;
                }
                vzorec.setText("");

                isThereInput = polePreVyraz(variable);
                if (!isThereInput.get(1).equals("=") || isThereInput.get(2).equals("=")){
                    JOptionPane.showMessageDialog(null,"Na druhom mieste musí byť =");
                    return;
                }

                if (!isThereInput.get(1).equals("=")){
                    JOptionPane.showMessageDialog(null,"Na druhom mieste musí byť =");
                    return;
                }

                boolean zoznamVstupov = true;
                for (int i = 2; i < isThereInput.size(); i++) {
                    if (!isThereInput.get(i).equals("")){
                    Boolean flag = Character.isLetter(isThereInput.get(i).charAt(0));
                    if (flag){ // je to pismeno
                        if (zoznamVstupov){
                            zoznamVstupov = checkAvailable(isThereInput.get(i), vstupPrem);

                        }
                    }
                }}
                boolean isThereOutput = checkAvailable(isThereInput.get(0), vystupPrem);

                for (int i = 0; i < isThereInput.size(); i++) {
                    int index = getIndex(vstupPrem, isThereInput.get(i));
                    if (index == -1){
                        index = getIndex(vystupPrem, isThereInput.get(i));
                        if (index == -1){

                        }else{
                            allPremenne.add(vystupPrem.get(index));
                            allBity.add(bitVystupPrem.get(index));
                            allTypyPremennych.add(vystupPremTyp.get(index));

                        }

                    }else{
                        allPremenne.add(vstupPrem.get(index));
                        allBity.add(bitVstupPrem.get(index));
                        allTypyPremennych.add(vstupPremTyp.get(index));

                    }
                }

                if (!zoznamVstupov){
                    JOptionPane.showMessageDialog(null,"Zle zadané premenné");
                    return;
                }
                if (!isThereOutput){
                    JOptionPane.showMessageDialog(null,"Zle zadané premenné");
                    return;
                }

                konecnyVzorec.add(variable);

                for (int i = 0; i < allTypyPremennych.size(); i++) {
                    if (allTypyPremennych.get(i).charAt(0) == 'c'){
                        allTypyPremennych.set(i, "signed");
                    }else{
                        allTypyPremennych.set(i, "unsigned");
                    }
                }

                System.out.println("premenne za sebou" + allPremenne); // tu je vlozeny vzorec
                System.out.println("bityZaSEbou" + allBity); // tu je vlozeny vzorec
                System.out.println("typeZaSEbou" + allTypyPremennych); // tu je vlozeny vzorec
                System.out.println(variable);
                System.out.println(vystupPrem);

            }
        });

        potvrdVstup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String variable = inputName.getText();
                String value = inputtTyp.getSelectedItem().toString();
                String bit = inputtBit.getText();
                System.out.println(vstupPrem);
                System.out.println(allBity);
                System.out.println(vstupPremTyp);
                System.out.println(vystupPremTyp);


                if (bit.equals("") || variable.equals("")){
                    JOptionPane.showMessageDialog(null, "Vyplň všetky políčka");
                    return;
                }

                Boolean flagForFisrtLetter = Character.isLetter(variable.charAt(0));
                if (!flagForFisrtLetter){
                    JOptionPane.showMessageDialog(null, "Názov premennej musí začínať najprv písmenom");
                    return;
                }

                boolean rightFormatBit = isNum(bit);
                if (!rightFormatBit){
                    JOptionPane.showMessageDialog(null, "Zlý vstup pre bitovú veľkosť");
                    return;
                }

                for (int i = 0; i < variable.length(); i++) {
                    if (variable.charAt(i) == ' ') {
                        JOptionPane.showMessageDialog(null, "Vyplň bez medzery");
                        return;
                    }
                }

                for (int i = 0; i < variable.length(); i++) {
                    Boolean flag = Character.isLetter(variable.charAt(i));
                    Boolean flag2 = Character.isDigit(variable.charAt(i));
                    if(!flag && !flag2){
                        JOptionPane.showMessageDialog(null, "Zadajte len pisemena");
                        return;
                    }
                }

                for (int i = 0; i < vstupPrem.size(); i++) {
                    if (vstupPrem.get(i).equals(variable)){
                        JOptionPane.showMessageDialog(null, "Rovnaká premenná je už v definovaná");
                        return;
                    }
                }
                for (int i = 0; i < vystupPrem.size(); i++) {
                    if (vystupPrem.get(i).equals(variable)){
                        JOptionPane.showMessageDialog(null, "Rovnaká premenná je už v definovaná");
                        return;
                    }
                }

                vstupPrem.add(variable);
                vstupPremTyp.add(value);
                bitVstupPrem.add(bit);

                inputName.setText("");
                inputtBit.setText("");

                StringBuffer sb = new StringBuffer();
                for(int i = 0; i < vstupPrem.size(); i++) {
                    sb.append(vstupPrem.get(i) + " ");
                }
                String str = sb.toString();
                infoInput.setText(str);

            }
        });

        potvrdVystup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String variable = outputtName.getText();
                String value = outputtTyp.getSelectedItem().toString();
                String bit = outputtBit.getText();
                System.out.println(vystupPrem);

                if (bit.equals("") || variable.equals("")){
                    JOptionPane.showMessageDialog(null, "Vyplň všetky políčka");
                    return;
                }

                Boolean flagForFisrtLetter = Character.isLetter(variable.charAt(0));
                if (!flagForFisrtLetter){
                    JOptionPane.showMessageDialog(null, "Názov premennej musí začínať najprv písmenom");
                    return;
                }

                boolean rightFormatBit = isNum(bit);
                if (!rightFormatBit){
                    JOptionPane.showMessageDialog(null, "Zlý vstup pre bitovú veľkosť");
                    return;
                }

                for (int i = 0; i < variable.length(); i++) {
                    if (variable.charAt(i) == ' ') {
                        JOptionPane.showMessageDialog(null, "Vyplň bez medzery");
                        return;
                    }
                }

                for (int i = 0; i < variable.length(); i++) {
                    Boolean flag = Character.isLetter(variable.charAt(i));
                    Boolean flag2 = Character.isDigit(variable.charAt(i));
                    if(!flag && !flag2){
                        JOptionPane.showMessageDialog(null, "Zadajte len pisemena");
                        return;
                    }
                }

                for (int i = 0; i < vystupPrem.size(); i++) {
                    if (vystupPrem.get(i).equals(variable)){
                        JOptionPane.showMessageDialog(null, "Rovnaká premenná je už v definovaná");
                        return;
                    }
                }

                for (int i = 0; i < vstupPrem.size(); i++) {
                    if (vstupPrem.get(i).equals(variable)){
                        JOptionPane.showMessageDialog(null, "Rovnaká premenná je už v definovaná");
                        return;
                    }
                }

                vystupPrem.add(variable);
                vystupPremTyp.add(value);
                bitVystupPrem.add(bit);

                StringBuffer sb = new StringBuffer();
                for(int i = 0; i < vystupPrem.size(); i++) {
                    sb.append(vystupPrem.get(i) + " ");
                }
                String str = sb.toString();

                outputtName.setText("");
                outputtBit.setText("");
                infoOutput.setText(str);


            }
        });
        String dizajnfile;

        vytvorDizajn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (vstupPrem.size()==0){
                    JOptionPane.showMessageDialog(null, "Nezadal si vstupne premenne");
                    return;
                }
                if (vystupPrem.size()==0){
                    JOptionPane.showMessageDialog(null, "Nezadal si vystupne premenne");
                    return;
                }
                if (konecnyVzorec.size()==0){
                    JOptionPane.showMessageDialog(null, "Nezadal si vyraz");
                    return;
                }
                String dizajnfile;

                try {

                    JFrame parentFrame2 = new JFrame();
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Specify a file to save");

                    int userSelection = fileChooser.showSaveDialog(parentFrame2);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                        dizajnfile = fileToSave.getAbsolutePath();

                        File file = new File(dizajnfile);

                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    String dizajnfile2 =dizajnfile.replace("\\", "/");
                    System.out.println(dizajnfile2);

                        miesto.add(dizajnfile2);

                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write("module adder (");
                    bw.newLine();
                    bw.newLine();

                    for(int i = 0; i< vstupPrem.size(); i++) {
                        int finalbit = Integer.parseInt(bitVstupPrem.get(i)) - 1;
                        bw.write("input " + vstupPremTyp.get(i) +  "["+ finalbit + ":0] " + vstupPrem.get(i) + ",");
                        bw.newLine();
                        bw.newLine();

                    }


                    for(int i = 0; i< vystupPrem.size()-1; i++) {
                         int finalbit = Integer.parseInt(bitVystupPrem.get(i)) - 1;
                        bw.write("output " + vystupPremTyp.get(i) +  "["+finalbit+ ":0] " + vystupPrem.get(i) + ",");
                        bw.newLine();
                        bw.newLine();
                    }
                    int finalbit = Integer.parseInt(bitVystupPrem.get(vystupPrem.size()-1)) - 1;
                    bw.write("output " + vystupPremTyp.get(vystupPrem.size()-1) +  "["+finalbit+ ":0] " + vystupPrem.get(vystupPrem.size()-1));

                    bw.newLine();
                    bw.newLine();
                    bw.write("     );");
                    bw.newLine();
                    bw.newLine();

                    bw.write("     assign ");

                   for(int i = 0; i< vystupPrem.size(); i++) {
                       int pocet= vystupPrem.size()-1;
                       if (i < pocet){

                           bw.write( konecnyVzorec.get(i) + ",");
                           bw.newLine();

                       }
                       if (i == pocet){
                       bw.write( konecnyVzorec.get(i) + ";");
                       bw.newLine();
                       }
                   }

                    bw.newLine();
                    bw.newLine();
                    bw.write("endmodule");

                    bw.close();
                    }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            }
        });

        vytvorTestbench.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (vstupPrem.size()==0){
                    JOptionPane.showMessageDialog(null, "Nezadal si vstupne premenne");
                    return;
                }
                if (vystupPrem.size()==0){
                    JOptionPane.showMessageDialog(null, "Nezadal si vystupne premenne");
                    return;
                }
                if (konecnyVzorec.size()==0){
                    JOptionPane.showMessageDialog(null, "Nezadal si vyraz");
                    return;
                }

                try {

                    if (vstupPrem ==null){
                        JOptionPane.showMessageDialog(null, "Najprv zadaj premenne");
                        return;
                    }

                    JFrame parentFrame = new JFrame();
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Specify a file to save");

                    int userSelection = fileChooser.showSaveDialog(parentFrame);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                        String testfile = fileToSave.getAbsolutePath();

                        File file = new File(testfile);

                        String testfile2 =testfile.replace("\\", "/");
                        System.out.println(testfile2);

                        miesto.add(testfile2);

                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }

                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);


                    bw.write("module adder_tb;");
                    bw.newLine();
                    bw.newLine();

                    int finalbitt = Integer.parseInt(allBity.get(0)) - 1;

                    for(int i = 0; i< vstupPrem.size(); i++) {
                        int finalbit = Integer.parseInt(bitVstupPrem.get(i)) - 1;
                        bw.write("reg " + vstupPremTyp.get(i) +  "["+ finalbit + ":0] " + vstupPrem.get(i) + ";");
                        bw.newLine();
                        bw.newLine();

                    }

                    for(int i = 0; i< vystupPrem.size(); i++) {
                        int finalbit = Integer.parseInt(bitVystupPrem.get(i)) - 1;
                        bw.write("wire " + vystupPremTyp.get(i) +  "["+finalbit+ ":0] " + vystupPrem.get(i) + ";");
                        bw.newLine();
                        bw.newLine();

                    }

                    bw.newLine();
                    bw.write(" adder UUT (");
                    for(int i = 0; i< vstupPrem.size(); i++) {
                        bw.write("."+ vstupPrem.get(i) + "("+ vstupPrem.get(i) + "),");
                    }

                    for(int i = 0; i< vystupPrem.size()-1; i++) {
                            bw.write("." + vystupPrem.get(i) + "(" + vystupPrem.get(i) + "),");

                    }
                    bw.write("."+ vystupPrem.get(vystupPrem.size()-1) + "("+ vystupPrem.get(vystupPrem.size()-1) + "));");

                    bw.newLine();
                    bw.newLine();
                    for(int i = 0; i< vstupPrem.size(); i++) {
                        bw.write("integer " + vstupPrem.get(i) + "i;");
                        bw.newLine();
                    }
                    bw.newLine();
                    bw.newLine();
                    bw.write("initial");
                    bw.newLine();
                    bw.newLine();
                    bw.write("begin");
                    bw.newLine();
                    bw.newLine();

                    for(int i = 0; i< vstupPrem.size(); i++) {
                        int finalbit = Integer.parseInt(bitVstupPrem.get(i));
                        bw.write( "for (" + vstupPrem.get(i) + "i=0;"
                                + vstupPrem.get(i) + "i<=2**" + finalbit + "-1;"
                                + vstupPrem.get(i) + "i=" + vstupPrem.get(i) + "i+1)" );
                        bw.newLine();
                    }

                    bw.write("begin");
                    bw.newLine();

                    for(int i = 0; i< vstupPrem.size(); i++) {
                        bw.write( vstupPrem.get(i) + "=" + vstupPrem.get(i) + "i;" );
                        bw.newLine();
                    }

                    bw.write("#0.1;");
                    bw.newLine();
                    bw.newLine();
                    bw.write("end");
                    bw.newLine();
                    bw.write("end");
                    bw.newLine();
                    bw.newLine();
                    bw.write("endmodule");
                    bw.newLine();

                    bw.close();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });

        cestaNaVytvorenieProjektuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFrame parentFrame = new JFrame();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");

                if (miesto.size()==0){
                    JOptionPane.showMessageDialog(null, "Najprv vytvor dizajn");
                    return;
                }
                if (miesto.size()==1){
                    JOptionPane.showMessageDialog(null, "Najprv vytvor testbench");
                    return;
                }

                int userSelection = fileChooser.showSaveDialog(parentFrame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    System.out.println("Save as file: " + fileToSave.getAbsolutePath());

                    String vivado = fileToSave.getAbsolutePath();


                    String vivado2 =vivado.replace("\\", "/");
                    System.out.println(vivado2);
                    miesto.add(vivado2);

                }
            }
        });


        vytvorSkript.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> dosky2 = new ArrayList<String>();
                ArrayList<String> dosky = new ArrayList<String>();
                String value = moznostDosky.getSelectedItem().toString();

                if (miesto.size()==0){
                    JOptionPane.showMessageDialog(null, "Najprv vytvor dizajn");
                    return;
                }
                if (miesto.size()==1){
                    JOptionPane.showMessageDialog(null, "Najprv vytvor testbench");
                    return;
                }
                if (miesto.size()==2){
                    JOptionPane.showMessageDialog(null, "Najprv zadaj cestu pre vytvorenie projektu");
                    return;
                }

                dosky.add(value);
                dosky2.add(value);
                System.out.println(dosky);

                System.out.println(miesto);

                for (int i = 0; i < dosky.size(); i++) {

                    if (dosky.get(i).charAt(1) == 'Y'){
                        dosky.set(0, "xc7z020clg484-1");
                        dosky2.set(0, "xilinx.com:zc702:part0:1.4");

                    }else if (dosky.get(i).charAt(1) == 'r') {
                        dosky.set(0, "xc7a200tfbg676-2");
                        dosky2.set(0, "xilinx.com:ac701:part0:1.4");

                    }
                    else if (dosky.get(i).charAt(1) == 'y') {
                        dosky.set(0, "xczu7ev-ffvc1156-2-e");
                        dosky2.set(0, "xilinx.com:zcu106:part0:2.6");

                    }
                    else if (dosky.get(i).charAt(1) == 'p') {
                        dosky.set(0, "xc7s100fgga676-2");
                        dosky2.set(0, "xilinx.com:sp701:part0:1.1");

                    }
                }

                try {

                        JFrame parentFrame = new JFrame();
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Specify a file to save");

                        int userSelection = fileChooser.showSaveDialog(parentFrame);

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToSave = fileChooser.getSelectedFile();
                            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                            String skrfile = fileToSave.getAbsolutePath();

                    File file = new File(skrfile);

                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }



                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write("create_project ProjectDP " + miesto.get(2) +" -part "+ dosky.get(0) +" -force");
                    bw.newLine();
                    bw.write("set_property board_part "+ dosky2.get(0) +" [current_project]");
                    bw.newLine();
                    bw.write("import_files " + miesto.get(0));
                    bw.newLine();
                    bw.write("import_files " + miesto.get(1));
                    bw.newLine();
                    bw.write("update_compile_order -fileset sources_1");
                    bw.newLine();
                    bw.write("update_compile_order -fileset sim_1");
                    bw.newLine();
                    bw.write("launch_simulation");
                    bw.newLine();

                    bw.close();}

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            }
        });

        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null, "Vstupy:  " + infoInput.getText() + "Výstupy:  " + infoOutput.getText() + "Výrazy: " + konecnyVzorec);
                return;

            }
        });
    }

    private int getIndex(ArrayList arrayList, String value){
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(value)){
                return i;
            }
        }
        return -1 ;
    }

    public static boolean isNum(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean checkAvailable(String slovo, ArrayList<String> arrayList){
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(slovo)){
                return true;
            }
        }
        return false;
    }

    private ArrayList<String> polePreVyraz(String variable){
        ArrayList<String> parseInput= new ArrayList<String>();
        for (int i = 0; i < variable.length(); i++) {
            Boolean flag = Character.isLetter(variable.charAt(i));
            if (flag){
                for (int j = i+1; j < variable.length(); j++) {
                    Boolean flag2 = Character.isLetter(variable.charAt(j));
                    Boolean flagDigit = Character.isDigit(variable.charAt(j));
                    if (!flag2 && !flagDigit){
                        String parsnuty = null;
                        if (j-1 > 0){
                            parsnuty = variable.substring(i, j);
                        }else{
                            parsnuty = String.valueOf(variable.charAt(i));
                        }
                        parseInput.add(parsnuty);
                        i = j-1;
                        break;
                    }
                }
            } else{
                parseInput.add(String.valueOf(variable.charAt(i)));
            }
            if (i+1 == variable.length()){
                for (int j = variable.length()-1; j >0 ; j--) {
                    Boolean flag3 = Character.isLetter(variable.charAt(j));
                    Boolean flagDigit = Character.isDigit(variable.charAt(j));
                    if (!flag3 && !flagDigit){
                        String parsnuty = variable.substring(j+1, i+1);
                        parseInput.add(parsnuty);
                        break;
                    }
                }
            }

        }
        return parseInput;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new converter().wrapper);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private boolean operatory(char textToCheck){
        // tu definovat vsetky znaky
        if (textToCheck == '+' || textToCheck == '-' || textToCheck == '*' || textToCheck == '/' || textToCheck == '^'){
            return true;
        }else{
            return false;
        }
    }

    private boolean operatoryMinus(char textToCheck){
        // tu definovat vsetky znaky
        if (textToCheck == '+' || textToCheck == '*' || textToCheck == '/' || textToCheck == '^'){
            return true;
        }else{
            return false;
        }
    }

    private boolean kontrolaZatvoriek(String insertedString){
        int numberOfLeft = 0;
        int numberOfRight = 0;
        for (int i = 0; i < insertedString.length(); i++) {
            if (insertedString.charAt(i) == '('){
                numberOfLeft++;
            }
            if (insertedString.charAt(i) == ')'){
                numberOfRight++;
            }
            if (numberOfRight > numberOfLeft){
                return false;
            }
            if (insertedString.charAt(i) == '(' && insertedString.charAt(i+1) == ')'){
                return false;
            }
            if (i+1 < insertedString.length() & insertedString.charAt(i) == '(' && operatoryMinus(insertedString.charAt(i+1))){
                return false;
            }
            if (i-1 < 0 & insertedString.charAt(i) == '(' && operatory(insertedString.charAt(i))){
                return false;
            }
        }
        if (numberOfLeft != numberOfRight){
            return false;
        }
        return true;
    }

    private boolean controlChar(String insertedString){
        for (int i = 0; i+1 < insertedString.length(); i++) {
            if (i+1< insertedString.length()){
                Boolean flag = Character.isLetter(insertedString.charAt(i));
                Boolean flag2 = Character.isLetter(insertedString.charAt(i+1));
                if(flag && flag2) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean controlOperators(String insertedString){
        for (int i = 0; i+1 < insertedString.length(); i++) {
            if (operatory(insertedString.charAt(i)) && operatory(insertedString.charAt(i+1))){
                return false;
            }
        }
        return true;
    }

}


