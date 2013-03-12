using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;

namespace ConsoleApplication3
{
    class Program
    {
        public static bool issafe()
        {
            return true;
        }
        public static void MethodA()
        {
            if (issafe())
            {
            Console.WriteLine("hej");
            }
            
        }

        public static void MethodB()
        {
            if (issafe())
            {
                Console.WriteLine("Du er godt nok klog");
            }
        }

        static void Main(string[] args)
        {
            Thread aThread = new Thread(new ThreadStart(MethodA));
            Thread bThread = new Thread(new ThreadStart(MethodB));

            aThread.Start();
            bThread.Start();

            Thread.Sleep(100);
            Console.ReadKey();
        }
    }
}
