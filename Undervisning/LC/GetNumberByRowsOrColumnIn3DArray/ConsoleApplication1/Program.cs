using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    class Program
    {
        static void Main(string[] args)
        {
            MyClass N = new MyClass();

            //Console.WriteLine(N.GetByRow(0,1,2));
            Console.WriteLine(N.GetByColumn(0,1,2));
        }
    }

    class MyClass
    {
        int[] MyArray = { 01, 02, 03,
                          04, 05, 06, 
                          07, 08, 09, 
                          
                          11, 12, 13,
                          14, 15, 16,
                          17, 18, 19,
 
                          21, 22, 23,
                          24, 25, 26,
                          27, 28, 29 
                        };

        public int GetByRow(int x, int y, int z)
        {
            int result = MyArray[x*9+y*3+z];

            return result;
        }

        public int GetByColumn(int z, int y, int x)
        {
            int result = MyArray[x * 9 + y * 3 + z];

            return result;
        }
    }
}
