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
	private final int idCarro;
	private final Semaphore semaforo;
    private final String sentido;
	private final Random random;

    public CarThread(int idCarro, Semaphore semaforo, int sentido) {
        this.idCarro = idCarro;
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

    private void carroAndando() {
        int distTotal = random.nextInt(1001) + 2000;
        int distPecorrida = 0;
        int delocamento = 30;

		while (distPecorrida < distTotal) {
            distPecorrida += delocamento;

			pararThread(50);

			String mensagem = "Carro: #" + idCarro + " andou " + distPecorrida + "m";
			LOGGER.info(mensagem);
        }

        setPosSaida();

		String mensagem = "Carro: #" + idCarro + " foi o " + posicaoChegada + " a chegar";
		LOGGER.info(mensagem);
    }

	private void pararThread(int l) {
		try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	private void carroEsperando() {
		String mensagem = "Carro: #" + idCarro + " está esperando";
		LOGGER.info(mensagem);

        int tempoParado = random.nextInt(2000) + 1000;
		pararThread(tempoParado);
	}

    private void carroSaindo() {
        setPosSaida();

		String mensagem = "Carro: #" + idCarro + " passou pelo semaforo, indo no sentido, " + sentido;
		LOGGER.info(mensagem);
    }

	public static void setPosSaida() {
        CarThread.posicaoSaida = posicaoSaida + 1;
    }
}
