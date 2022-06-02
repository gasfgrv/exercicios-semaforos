package gusto.fatec.exercicios.semaforos.ex1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class PessoaThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getName());
    private final Semaphore semaforo;
    private final Random random;
    private static int chegadaPorta;
    private static int passagemPorta;

    public PessoaThread(Semaphore semaforo) {
        this.semaforo = semaforo;
        random = new Random();
    }

    @Override
    public void run() {
        try {
            pessoaAndando();
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

    private void pessoaAndando() throws InterruptedException {
        int distanciaPecorrida = 0;
        int delocamento = random.nextInt(2 + 4);

        while (distanciaPecorrida < 200) {
            distanciaPecorrida += delocamento;

            pararThread(10);

            String logInfo = getName() + " andou " + distanciaPecorrida + "m";
            LOGGER.info(logInfo);
        }

        setChegadaPorta();
    }

    private void pararThread(long tempo) throws InterruptedException {
        Thread.sleep(tempo);
    }

    private void pessoaEsperando() throws InterruptedException {
        String logInfo = getName() + " chegou na porta.";
        LOGGER.info(logInfo);

        long tempo = random.nextInt(1) + 1L;

        pararThread(tempo);
    }

    private void pessoaSaindo() {
        setPassagemPorta();

        String logInfo = getName() + " foi o " + passagemPorta + "° a sair.";
        LOGGER.info(logInfo);

        Thread.currentThread().interrupt();
    }

    public static void setChegadaPorta() {
        PessoaThread.chegadaPorta = chegadaPorta + 1;
    }

    public static void setPassagemPorta() {
        PessoaThread.passagemPorta = passagemPorta + 1;
    }
}
