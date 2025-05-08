package Clases;

// Clase principal del complejo invernal
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComplejoInvernal {

    private static final int NUM_BARRERAS = 5; // Número de barreras (clases concurrentes)
    private final List<CyclicBarrier> barrerasClases = new ArrayList<>();

    private final List<MedioElevacion> medios = new ArrayList<>();
    private static final int HORA_APERTURA = 10, HORA_CIERRE = 17;
    private final AtomicInteger cantEsperandoClase = new AtomicInteger(0),
            proximaClase = new AtomicInteger(0),
            horaActual = new AtomicInteger(9),
            cantParaClase = new AtomicInteger(0);
    private boolean activo = true;
    private final Lock accesoClase = new ReentrantLock(true);
    private final Condition cabina = accesoClase.newCondition(), asientos = accesoClase.newCondition();  // Usamos la condición ya existente
    private final Semaphore postClass[] = {new Semaphore(0, true), new Semaphore(0, true), new Semaphore(0, true), new Semaphore(0, true), new Semaphore(0, true),new Semaphore(0, true)};
    private final Confiteria confiteria = new Confiteria();
    private boolean medioAbiertos = false;

    // Metodos relacionados al "Complejo Invernal"
    public ComplejoInvernal() {
        // Crear 4 medios de elevación con 1, 2, 3 y 4 molinetes respectivamente
        for (int i = 1; i <= 4; i++) {
            medios.add(new MedioElevacion(i, i));
        }

        // Crear barreras para cada clase concurrente
        for (int i = 0; i <= NUM_BARRERAS; i++) {
            barrerasClases.add(new CyclicBarrier(5, new Runnable() {
                @Override
                public void run() {
                    System.out.println(" [Complejo] Iniciando Procedimiento de Clase");
                }
            }));
        }

        // Iniciar Simulación
        iniciarRelojSimulado();
    }

    private void iniciarRelojSimulado() {

        // Para iniciar el funcionamiento del complejo invernal
        Thread reloj = new Thread(() -> {
            
            while (activo) {
                try {
                    int hora = horaActual.incrementAndGet();
                    System.out.println(" [Complejo] Hora actual: " + hora + ":00");

                    if (hora == HORA_APERTURA) {
                        for (MedioElevacion medio : medios) {
                            medio.abrir(); // ACA ABRE
                        }
                        medioAbiertos = true;
                        System.out.println(" [Complejo] Medios de elevación abiertos.");                       
                    } else if (hora == HORA_CIERRE) {
                        activo = false;
                        
                        accesoClase.lock();
                            asientos.signalAll();
                            cabina.signalAll();
                        accesoClase.unlock();
                        
                        cerrarMediosElevacion();
                        System.out.println(" [Complejo] Simulación finalizada.");
                    }
                    Thread.sleep(5000);  // Cada "hora" pasa cada 5 segundos
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        reloj.start();
    }

    private void cerrarMediosElevacion() {
        for (MedioElevacion medio : medios) {
            medio.cerrar();
        }
    }

    public boolean estaAbierto() {
        return activo && medioAbiertos;
    }

    // Metodos relacionados a acciones que toma el "Instructor"
    public boolean esperarEnCabina(int claseCorrespondiente) {
        boolean daraClases = true;
        try {
            accesoClase.lock();
            System.out.println(" [" + Thread.currentThread().getName() + "] Esperando en Cabina.");

            
            while ((cantEsperandoClase.get() < 4 || proximaClase.get() != 0) && estaAbierto() && daraClases) {
                cabina.await(15, TimeUnit.SECONDS); // Espera por un tiempo limitado
                
                if (cantEsperandoClase.get() < 4) {
                    
                    if (!estaAbierto()) {
                        daraClases = false;
                    }
                    asientos.signalAll();
                } else if (cantEsperandoClase.get() >= 4){
                    asientos.signalAll();
                }
            }
            if (daraClases) {
                proximaClase.set(claseCorrespondiente);

                asientos.signalAll();
                accesoClase.unlock();

                barrerasClases.get(claseCorrespondiente).await(); // El instructor espera en la barrera
                proximaClase.set(0);
                cantParaClase.addAndGet(-4);

                accesoClase.lock();
                try {
                    if (cantEsperandoClase.get() >= 4) {
                        cabina.signal();
                    }
                } finally {
                    accesoClase.unlock();
                }
            } else {
                accesoClase.unlock();
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ComplejoInvernal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(ComplejoInvernal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daraClases;
    }

    public void darClase(int claseCorrespondiente) {
        try {

            System.out.println(" [" + Thread.currentThread().getName() + "] dando su Clase  " + (claseCorrespondiente));
            TimeUnit.SECONDS.sleep(8);
            System.out.println(" [" + Thread.currentThread().getName() + "] Terminado su Clase  " + (claseCorrespondiente));
            postClass[claseCorrespondiente].release(4);

        } catch (InterruptedException ex) {
            Logger.getLogger(ComplejoInvernal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Metodos relacionados a acciones que toma el "Esquiador"
    public int preClase() {
        int claseCorrespondiente = -1;
        boolean hayClase = true;
        try {
            accesoClase.lock();
            try {
                cantEsperandoClase.incrementAndGet();
                System.out.println(" [" + Thread.currentThread().getName() + "] Esperando para la clase. Esquiador nro: " + cantEsperandoClase.get());

                // Verificacion de esquiadores suficientes: Si hay suficientes esquiadores, despierta al instructor
                if (cantEsperandoClase.get() >= 4) {
                    cabina.signal();
                    //System.out.println(" [" + Thread.currentThread().getName() + "] Le avisa al instructor");
                }

                //Esperan a que el instructor les avise
                while ((proximaClase.get() == 0 || cantParaClase.get() >= 4) && hayClase) {
                    System.out.println(" [" + Thread.currentThread().getName() + "] Espera en los asientos ");
                    asientos.await();

                    if (cantEsperandoClase.get() < 4 && proximaClase.get() == 0) {
                        //Si fueron avisados y no habian 4 es porque se tienen q retirar
                        hayClase = false;
                    }
                }
                cantEsperandoClase.addAndGet(-1);
                cantParaClase.addAndGet(1);

            } finally {
                if (hayClase) {
                    claseCorrespondiente = proximaClase.get();
                    accesoClase.unlock();
                    try {
                        System.out.println("claseCorrespondiente:  "+ claseCorrespondiente);
                        barrerasClases.get( claseCorrespondiente ).await();
                    } catch (BrokenBarrierException ex) {
                        Logger.getLogger(ComplejoInvernal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ComplejoInvernal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return claseCorrespondiente;
    }

    public void tenerClase(int miClase) {
        try {
            postClass[miClase].acquire();
            System.out.println(" [" + Thread.currentThread().getName() + "]  Termino su clase " + miClase);
        } catch (InterruptedException ex) {
            Logger.getLogger(ComplejoInvernal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodo relacionado a los Medios de Elevacion
    public void accederMedio(int eleccionSilla) {
        medios.get(eleccionSilla).usarMolinete();
    }

    // Metodos relacionados a la "Cafeteria"
    public boolean accederConfiteria() {
        return confiteria.entrar();
    }

    public void servirseConfiteria() {
        confiteria.servir();
    }

    public void salirConfiteria() {
        confiteria.salir();
    }
}
