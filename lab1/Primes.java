public class Primes {
    public static void main(String[] args) {
        for (int i=3; i<100; i++) {
            if (isPrime(i)==true) System.out.print(i+" ");
        }
    }
    public static boolean isPrime(int n) { //функция определения простого числа
        int k=0;
        for (int i=2; i<n; i++) { //в качестве делителей выступают числа от 2 до n-1
            if (n%i==0) k++; //при целочисленном делении кол-во делителей увеличивается
        }
        if (k==0) return true; else return false; //если отсутствуют делители - число простое
    }
}
