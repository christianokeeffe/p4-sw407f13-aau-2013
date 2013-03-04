using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public class Alpha
    {
        Mutex Mut = new Mutex();
        int i = 0;

        int MyVar = 0;
        int BetaCount = 0;
        int AlphaCount = 0;
        // This method that will be called when the thread is started
        public void Beta()
        {
            
            while(i<= 1000000)
            {
                Mut.WaitOne();
                MyVar++;
                AlphaCount++;
                Console.WriteLine("Alpha is running in its own thread. : " + MyVar + " Error rate: " + (AlphaCount - BetaCount) + " Count: " + i);
                i++;
                Mut.ReleaseMutex();
            }
            
        }

        // This method that will be called when the thread is started
        public void Gamma()
        {
            while (i <= 1000000)
            {
                Mut.WaitOne();
                MyVar--;
                BetaCount++;
                Console.WriteLine("Beta is running in its own thread. : " + MyVar + " Error rate: " + (AlphaCount - BetaCount) + " Count: " + i);
                i++;
                Mut.ReleaseMutex();
            }
            
        }
    };

    class Program
    {
        static void Main(string[] args)
        {
            Alpha oAlpha = new Alpha();

            // Create the thread object, passing in the Alpha.Beta method
            // via a ThreadStart delegate. This does not start the thread.


            Thread aThread = new Thread(new ThreadStart(oAlpha.Beta));
            Thread bThread = new Thread(new ThreadStart(oAlpha.Gamma));
            // Start the thread
            aThread.Start();
            bThread.Start();

            // Spin for a while waiting for the started thread to become
            // alive:
            while (!aThread.IsAlive) ;
            while (!bThread.IsAlive) ;

            // Put the Main thread to sleep for 1 millisecond to allow oThread
            // to do some work:
            Thread.Sleep(100);
        }
    }
}
