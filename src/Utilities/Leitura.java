/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author escobar
 */
public class Leitura {

    String path;
    ArrayList<int[]> Veiculos = new ArrayList<>();
    int vehicle_number;
    int capacity;

    public Leitura(String path) {
        this.path = path;
        lerTorres(path);
    }

    public void lerTorres(String arquivoCSV) {

        BufferedReader br = null;
        String linha = "";
        String csvDivisor = "\\s+";
        int tamanho = 7;

        try {

            br = new BufferedReader(new FileReader(arquivoCSV));
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            linha = br.readLine();
            linha = linha.trim();
            String[] ler = linha.split(csvDivisor);
            vehicle_number = Integer.parseInt(ler[0]);
            capacity = Integer.parseInt(ler[1]);
            
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            while ((linha = br.readLine()) != null) {
                if (linha.equals("")) {
                    break;
                }
                int[] info = new int[tamanho];
                linha = linha.trim();
                ler = linha.split(csvDivisor);

                for (int i = 0; i < 7; i++) {
                    info[i] = Integer.parseInt(ler[i]);
                }

                this.Veiculos.add(info);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<int[]> getVeiculos() {
        return Veiculos;
    }

    public void setVeiculos(ArrayList<int[]> Veiculos) {
        this.Veiculos = Veiculos;
    }

    public int getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(int vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}
