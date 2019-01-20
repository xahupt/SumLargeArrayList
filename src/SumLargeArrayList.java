import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author percy 多线程并行计算随机数组之和
 *               单线程要比多线程快
 *
 */
public class SumLargeArrayList{

    static List list = new ArrayList();
    static int count =5;


    private static CountDownLatch threadsSignal =new CountDownLatch(count);
    static double Sum = 0;
    static int MaxN = 5;
    public static void main(String[] args){

        for (long i=0;i<Math.pow(10,MaxN);i++){
            list.add(Math.random()*10);
            //list.add((double)i+1);
        }
        List<Thread> threads = new ArrayList<Thread>();
        long startTime = System.currentTimeMillis();
        for (int i=0;i<count;i++){
            final List l = list.subList(i*list.size()/count,(i+1)*(list.size()/count));
            threads.add(new Thread(()->SumLargeArrayList.SumList(l)));
        }
        threads.forEach(o->o.start());
        for (;;) {
            if (threadsSignal.getCount() == 0) {
                break;
            }
        }
        /*for (int i=0;i<count;i++){
            while(threads.get(i).isAlive()){

            }

        }*/
        long stopTime = System.currentTimeMillis();
        //if (threads.size()==0){

            System.out.println(Sum);
            System.out.println("多线程计算时间:"+(stopTime-startTime));
        //}
        startTime = System.currentTimeMillis();
        double Sum_2 = 0;
        for (long i=0;i<Math.pow(10,MaxN);i++){
            Sum_2 += (double)list.get((int)i);
        }
        stopTime = System.currentTimeMillis();
        System.out.println(Sum_2);
        System.out.println("单线程计算时间:"+(stopTime-startTime));
    }

    private static double SumList(List list) {
        //System.out.println(Thread.currentThread().getName());
        long Sum_=0;
        Sum_+=Sum(list);
        Sum+=Sum_;
        threadsSignal.countDown();
        return Sum;
    }
    static double Sum(List list){
        double Sum_ = 0;
        for (int i = 0; i < list.size(); i++) {
            Sum_ += (double)list.get(i);
        }
        return Sum_;
    }

}
