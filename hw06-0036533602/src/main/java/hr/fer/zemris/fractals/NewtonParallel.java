package hr.fer.zemris.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <code>NewtonParallel</code> class contains implementation of a multithreaded  Newton-Raphson iteration-based fractal viewer.
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class NewtonParallel {

  /**
   * <code>PosaoIzracuna</code> implements <link>Runnable</link>  that calculates the colours of a certain track
   *
   * @author Dean Trkulja
   * @version 1.0
   */
  public static class PosaoIzracuna implements Runnable{

    private double reMin;

    private double reMax;

    private double imMin;

    private double imMax;

    private int width;

    private int height;

    private int yMin;

    private int yMax;

    private int m;


    private short[] data;
    private AtomicBoolean cancel;

    ComplexRootedPolynomial complexRootedPolynomial;

    ComplexPolynomial complexPolynomial;

    ComplexPolynomial firstDerivative;

    /**
     * Represents type of job that tells us that there are no more tracks to paint
     */

    private final static PosaoIzracuna NO_JOB = new PosaoIzracuna();

    /**
     * Default constructor for <code>PosaoIzracuna</code>
     */
    private PosaoIzracuna(){
    }

    public PosaoIzracuna(double reMin, double reMax, double imMin, double imMax, int width, int height,int yMin,int yMax ,int m, AtomicBoolean cancel, short[] data, ComplexRootedPolynomial complexRootedPolynomial, ComplexPolynomial complexPolynomial) {
      this.reMin = reMin;
      this.reMax = reMax;
      this.imMin = imMin;
      this.imMax = imMax;
      this.width = width;
      this.height = height;
      this.yMax = yMax;
      this.yMin = yMin;
      this.m = m;
      this.cancel = cancel;
      this.data = data;
      this.complexPolynomial = complexPolynomial;
      this.firstDerivative = complexPolynomial.derive();
      this.complexRootedPolynomial = complexRootedPolynomial;
    }

    /**
     * Job that an instance of this class must do
     */
    public void run(){

      double rootTreshold = 1E-3;
      double convergenceTreshold = 0.002;
      int offset = yMin * width;

      for (int y = yMin; y <= yMax && !cancel.get(); y++) {

        for (int x = 0; x < width; x++) {

          double cRe = x / (width - 1.0) * (reMax - reMin) + reMin;
          double cIm = (height - 1.0 - y) / (height - 1.0) * (imMax - imMin) + imMin;
          double module;
          int iters = 0;

          Complex zn = new Complex(cRe, cIm);

          do {
            Complex numerator = complexPolynomial.apply(zn);
            Complex denominator = firstDerivative.apply(zn);

            Complex znoId = zn;

            Complex fraction = numerator.divide(denominator);

            zn = zn.sub(fraction);
            module = zn.sub(znoId).module();

            iters++;
          } while (module > convergenceTreshold && iters < m);

          int index = complexRootedPolynomial.indexOfClosestRootFor(zn, rootTreshold);
          data[offset++] = (short) (index + 1);
        }
      }
    }
  }

  /**
   * <code>NewtonParallelProducer</code> represent a multithreaded implementation of <link>IFractalProducer</link>
   *
   * @author Dean Trkulja
   * @version 1.0
   */
  public static class NewtonParallelProducer implements IFractalProducer{

    /**
     * Polynomial used for executing Newton-Raphson fractal viewer
     */
    private final ComplexRootedPolynomial complexRootedPolynomial;

    /**
     * Complex polynomial from <code>complexRootedPolynomial</code>
     */
    private final ComplexPolynomial complexPolynomial;

    /**
     * Number of threads
     */
    private final int numOfWorkers;

    /**
     * Number of separate tracks that will job be divided into
     */
    private int numOfTracks;


    /**
     *
     * @param complexRootedPolynomial polynomial used for executing Newton-Raphson fractal viewer
     * @param numOfWorkers number of threads
     * @param numOfTracks number of separate tracks that will job be divided into
     */

    public NewtonParallelProducer(ComplexRootedPolynomial complexRootedPolynomial, int numOfWorkers, int numOfTracks) {
      this.complexRootedPolynomial = complexRootedPolynomial;
      this.complexPolynomial = complexRootedPolynomial.toComplexPolynom();
      this.numOfWorkers = numOfWorkers;
      this.numOfTracks = numOfTracks;
    }

    @Override
    public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo, IFractalResultObserver observer,
                        AtomicBoolean cancel) {

      if(numOfTracks > height) numOfTracks = height;
      int m = 16 * 16;
      short[] data = new short[width * height];

      final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

      Thread[] workers = new Thread[numOfWorkers];

      for(int i = 0; i < numOfWorkers; i++){
        workers[i] = new Thread(() -> {
          while(true){
            PosaoIzracuna p;
            try{
              p = queue.take();
              if(p == PosaoIzracuna.NO_JOB) break;
            }catch (InterruptedException e){
              continue;
            }
            p.run();
          }
        });
      }

      for(Thread thread: workers) thread.start();

      int numberOfYByTrack = height / numOfTracks;

      for(int i = 0; i < numOfTracks; i++) {
        int yMin = i * numberOfYByTrack;
        int yMax = (i + 1) * numberOfYByTrack - 1;

        if(i == numOfTracks - 1) yMax = height - 1;

        PosaoIzracuna p = new PosaoIzracuna(
            reMin, reMax, imMin, imMax, width, height,
            yMin, yMax, m, cancel, data,
            complexRootedPolynomial, complexPolynomial
        );
        while (true) {
          try {
            queue.put(p);
            break;
          } catch (InterruptedException ignored) {
          }
        }
      }

      for(int i = 0; i < numOfWorkers; i++){
        while(true) {
         try {
           queue.put(PosaoIzracuna.NO_JOB);
           break;
         }catch (InterruptedException ignored){
         }
        }
      }

      for(int i = 0; i < numOfWorkers; i++){
        while (true){
          try{
            workers[i].join();
            break;
          }catch (InterruptedException ignored){

          }
        }
      }

      System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
      observer.acceptResult(data, (short)(complexPolynomial.order() + 1), requestNo);
    }
  }

  /**
   * Entry point for program that executes multithreaded Newton-Raphson fractal viewer.
   *
   * @param args an array of command-line arguments.
   * @throws IllegalArgumentException if <code>args</code> is in invalid format
   */


    public static void main(String[] args) {

      Integer workers = null;
      Integer tracks = null;

      if(args.length != 0){
        if(args.length > 2) throw new IllegalArgumentException("Too many arguments");

        if(args[0].startsWith("--workers=") || args[0].startsWith("-w")){
          workers = extractNumber(args[0]);
        }

        if(args[0].startsWith("--tracks=") || args[0].startsWith("-t")){
          tracks = extractNumber(args[0]);
        }
        if(args.length == 2){
          if(args[1].startsWith("--workers=") || args[0].startsWith("-w")){
            if(workers != null) throw new IllegalArgumentException("Number of workers defined twice");
            workers = extractNumber(args[1]);
          }

          if(args[1].startsWith("--tracks=") || args[0].startsWith("-t")){
            if(tracks != null) throw new IllegalArgumentException("Number of tracks defined twice");
            tracks = extractNumber(args[1]);
          }
        }
      }

      if(workers == null) workers = Runtime.getRuntime().availableProcessors();
      if(tracks == null) tracks = Runtime.getRuntime().availableProcessors() * 4;


      System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
      System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

      String terminateKeyWord = "done";
      Scanner sc = new Scanner(System.in);

      List<Complex> roots = new ArrayList<>();

      while (true) {
        System.out.printf("Root %d> ", roots.size() + 1);
        String input = sc.nextLine().trim();

        if (input.isBlank()) {
          System.out.println("Input can't be blank");
          continue;
        }

        if (input.equals(terminateKeyWord)) {
          if (roots.size() < 2) throw new IllegalArgumentException("You must provide minimum 2 roots");
          break;
        }

        roots.add(Complex.parse(input));
      }

      System.out.println("Image of fractal will appear shortly. Thank you.");

      NewtonParallelProducer newtonProducer = new NewtonParallelProducer(new ComplexRootedPolynomial(Complex.ONE, roots.toArray(Complex[]::new)), workers,tracks);
      FractalViewer.show(newtonProducer);
    }

  /**
   * Extracts number from <code>input</code> and parses
   *
   * @param input string from which we extract number of workers or threads
   * @return  number of workers or threads
   * @throws IllegalArgumentException if <code>input</code> is in invalid format
   */
  public static int extractNumber(String input){
      if(input.startsWith("--workers=") || input.startsWith("--tracks=")){
        return Integer.parseInt(input.substring(input.lastIndexOf("=")));
      }
      if(input.startsWith("-w") || input.startsWith("-t"))
        return Integer.parseInt(input.split(" ")[1]);

      else throw new IllegalArgumentException();
    }
  }
