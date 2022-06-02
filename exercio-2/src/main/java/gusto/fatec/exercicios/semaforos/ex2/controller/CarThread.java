package gusto.fatec.exercicios.semaforos.ex2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class CarThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getName());
    private static final String[] SENTIDOS = new String[]{"norte", "leste", "sul", "oeste"};
    private static int posicaoChegada;
    private static int posicaoSaida;
    private final Semaphore semaforo;
    private final String sentido;
    private final Random random;

    public CarThread(Semaphore semaforo, int sentido) {
        this.semaforo = semaforo;
        this.sentido = SENTIDOS[sentido];
        random = new Random();
    }

    @Override
    public void run() {
        try {
            carroAndando();
            semaforo.acquire();
            carroEsperando();
            carroSaindo();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            semaforo.release();
        }
    }

    private void carroAndando() throws InterruptedException {
        int distTotal = random.nextInt(1001) + 2000;
        int distPecorrida = 0;
        int delocamento = 30;

        while (distPecorrida < distTotal) {
            distPecorrida += delocamento;

            pararThread(50);

            String mensagem = getName() + " andou " + distPecorrida + "m";
            LOGGER.info(mensagem);
        }

        setPosicaoChegada();
        String mensagem = getName() + " foi o " + posicaoChegada + " a chegar";
        LOGGER.info(mensagem);
    }

    private void pararThread(int tempo) throws InterruptedException {
        Thread.sleep(tempo);
    }

    private void carroEsperando() throws InterruptedException {
        String mensagem = getName() + " está esperando";
        LOGGER.info(mensagem);

        int tempoParado = random.nextInt(2000) + 1000;
        pararThread(tempoParado);
    }

    private void carroSaindo() {
        setPosicaoSaida();

        String mensagem = getName() + " passou pelo semaforo, indo no sentido " + sentido;
        LOGGER.info(mensagem);
    }

    public static void setPosicaoSaida() {
        CarThread.posicaoSaida++;
    }

    public static void setPosicaoChegada() {
        CarThread.posicaoChegada++;
    }
}
