/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Executable;

import Utilities.Distribution;
import Utilities.Leitura;
import java.util.ArrayList;

/**
 *
 * @author escobar
 */
public final class Executable {

    public static String arquivo_csv = "instances_extendedTSP/C101.txt";
    
    public static void main(String[] args) {
        // TODO code application logic here
        Leitura fileReader = new Leitura(arquivo_csv);
        int vehicle_number = fileReader.getVehicle_number();
        int capacity = fileReader.getCapacity();
        ArrayList<int[]> Veiculos = fileReader.getVeiculos();
        Distribution d = new Distribution(Veiculos, vehicle_number, capacity);
        d.start();
        
        
        /*for (Integer id : d.geraSequenciaAleatoria()) {
            System.out.println(id+"\t");
        }*/
        
        /*System.out.println("Numero de veiculos: "+vehicle_number);
        System.out.println("Capacidade veiculos: "+capacity);
        System.out.println("");
        
        for (int[] info : Veiculos) {
            for (int i = 0; i < 7; i++) {
                System.out.print(info[i]+"\t");
            }
            System.out.println("");
        }*/
    }
    
}
