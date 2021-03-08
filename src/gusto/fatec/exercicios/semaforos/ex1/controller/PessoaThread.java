package gusto.fatec.exercicios.semaforos.ex1.controller;

import java.util.concurrent.Semaphore;

public class PessoaThread extends Thread {

    private final int pessoa;
    private static int chegadaPorta;
    private static int passagemPorta;
    private final Semaphore semaforo;

    public PessoaThread(int pessoa, Semaphore semaforo) {
        this.pessoa = pessoa;
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        pessoaAndando();

        try {
            semaforo.acquire();
            pessoaEsperando();
            pessoaSaindo();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            semaforo.release();
        }
    }

    private void pessoaAndando() {
        int corredor = 200;
        int distanciaPecorrida = 0;
        int delocamento = (int) ((Math.random() * 3) + 4);
        int tempo = 10;

        while (distanciaPecorrida < corredor) {
            distanciaPecorrida += delocamento;
            try {
                Thread.sleep(tempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            System.out.println("Pessoa: #" + pessoa + " andou " + distanciaPecorrida + "m");
        }

        setChegadaPorta();
    }

    private void pessoaEsperando() {
        System.out.println("Pessoa #" + pessoa + " chegou na porta.");

        int abrindoPorta = ((int) (Math.random() * 2) + 1);

        try {
            Thread.sleep(abrindoPorta);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void pessoaSaindo() {
        setPassagemPorta();

        System.out.println("Pessoa: #" + pessoa + " foi o " + passagemPorta + "� a sair.");
    }

    public static void setChegadaPorta() {
        PessoaThread.chegadaPorta = chegadaPorta + 1;
    }

    public static void setPassagemPorta() {
        PessoaThread.passagemPorta = passagemPorta + 1;
    }
}
