/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author escobar
 */
public class Distribution {

    ArrayList<int[]> Veiculos = new ArrayList<>();
    ArrayList<Integer> Sequencia = new ArrayList<>();
    ArrayList<ArrayList<Integer>> caminho_caminhao;

    int vehicle_number;
    int capacity;

    public Distribution(ArrayList<int[]> Veiculos, int vehicle_number, int capacity) {
        this.Veiculos = Veiculos;
        this.vehicle_number = vehicle_number;
        this.capacity = capacity;

        this.caminho_caminhao = new ArrayList<>();
        while (this.caminho_caminhao.size() < this.vehicle_number) {
            this.caminho_caminhao.add(new ArrayList<>());
        }
    }

    public void start() {

        ArrayList<Integer> ids_aleatorios = geraSequenciaAleatoria();

        boolean start = true;
        for (Integer id : ids_aleatorios) {
            double demora = Double.MAX_VALUE;
            int[] melhor_posicao = new int[2];
            int contador = 0;
            boolean adiciona = false;
            if (start) {
                this.caminho_caminhao.get(0).add(id);
                adiciona = true;
                start = false;
            }
            boolean ok = false;
            while (!adiciona) {
                if (contador == 25) {
                    this.caminho_caminhao.get(melhor_posicao[0]).add(melhor_posicao[1]);
                    break;
                }
                if (this.caminho_caminhao.get(contador).isEmpty()) {
                    if (ok) {
                        this.caminho_caminhao.get(melhor_posicao[0]).add(melhor_posicao[1]);
                        break;
                    } else {
                        this.caminho_caminhao.get(contador).add(id);
                        break;
                    }
                }

                for (int i = 0; i < this.caminho_caminhao.get(contador).size() + 1; i++) {
                    int[] tempox = new int[2];
                    int[] cargax = new int[2];

                    tempox = restrictionTime(colocaEntre(this.caminho_caminhao.get(contador), id, i));
                    cargax = restrictionCap(colocaEntre(this.caminho_caminhao.get(contador), id, i));

                    if (tempox[1] == 1 && cargax[1] == 1) {
                        if (tempox[0] < demora) {
                            ok = true;
                            melhor_posicao[0] = contador;
                            melhor_posicao[1] = id;
                            demora = tempox[0];
                        }
                    }
                }

                contador++;

            }
        }

        for (ArrayList<Integer> caminhao : this.caminho_caminhao) {
            for (Integer rota : caminhao) {
                System.out.print(rota + "\t");
            }
            System.out.println("");
        }

        /*ArrayList<Integer> ids = new ArrayList<>();

        ids.add(7);
        ids.add(8);

        int[] restriction1 = restrictionCap(ids);
        System.out.println("Carga: " + restriction1[0]);

        int[] restriction2 = restrictionTime(ids);
        System.out.println("Tempo: " + restriction2[0]);

        if (restriction1[1] == 0 || restriction2[1] == 0) {
            System.out.println("Rota nao completa");
            if (restriction1[1] == 0 && restriction2[1] == 0) {
                System.out.println("Restricao de carga e tempo");
            } else if (restriction1[1] == 0) {
                System.out.println("Restricao de carga");
            } else {
                System.out.println("Restricao de tempo");
            }
        }*/
    }

    private int[] restrictionCap(ArrayList<Integer> ids) {
        int[] retorno = new int[2];
        retorno[1] = 1;
        int cap_max = 0;
        for (Integer id : ids) {
            cap_max += Veiculos.get(id)[3];
        }
        if (cap_max > this.capacity) {
            retorno[1] = 0;
        }
        retorno[0] = cap_max;

        return retorno;
    }

    private int[] restrictionTime(ArrayList<Integer> ids) {
        int[] retorno = new int[2];
        retorno[1] = 1;
        int time = 0;
        int id_anterior = 0;
        for (Integer id : ids) {
            if (time > Veiculos.get(id)[5]) {
                retorno[1] = 0;
                //System.out.println("O id "+id+ " já esta fechado");
            }
            time += DistanciaEuclidiana(Veiculos.get(id_anterior)[1], Veiculos.get(id_anterior)[2], Veiculos.get(id)[1], Veiculos.get(id)[2]);
            if (time < Veiculos.get(id)[4]) {
                time = Veiculos.get(id)[4];
            }
            time += Veiculos.get(id_anterior)[6];
            id_anterior = id;
        }
        time += DistanciaEuclidiana(Veiculos.get(id_anterior)[1], Veiculos.get(id_anterior)[2], Veiculos.get(0)[1], Veiculos.get(0)[2]);
        if (time > Veiculos.get(0)[5]) {
            retorno[1] = 0;
        }

        retorno[0] = time;

        return retorno;
    }

    private ArrayList<Integer> geraSequenciaAleatoria() {
        ArrayList<Integer> ordem = new ArrayList<>();
        ArrayList<Integer> aleatorio = new ArrayList<>();
        Random random = new Random();

        for (int[] ids : Veiculos) {
            if (ids[0] != 0) {
                ordem.add(ids[0]);
            }
        }

        for (int i = 0; i < this.Veiculos.size() - 1; i++) {
            int id_sorteado = ordem.get(random.nextInt(ordem.size()));
            ordem.remove(ordem.indexOf(id_sorteado));
            aleatorio.add(id_sorteado);
        }

        return aleatorio;
    }

    private ArrayList<Integer> colocaEntre(ArrayList<Integer> ids, int id_novo, int posicao) {
        ArrayList<Integer> novo = new ArrayList<>();

        int contador = 0;
        for (Integer id : ids) {
            novo.add(id);
            if (contador == posicao) {
                novo.add(id_novo);
            }
            contador++;
        }

        if (ids.size() == novo.size()) {
            novo.add(id_novo);
        }
        return novo;
    }

    private double DistanciaEuclidiana(int x1, int y1, int x2, int y2) {
        double distancia = 0;

        distancia = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

        return distancia;
    }

}
