public class Palindrome {
    public static void main(String[] args) {
        String s="java Palindrome madam racecar apple kayak song noon";
        String[] words = s.split(" "); //разбиение строки на подстроки
            for(String word : words){ //проверка каждой подстроки на палиндром
                if (isPalindrome(word)==true) System.out.println(word); 
            } 
    }
    public static String reverseString(String s) { //функция получения обратной строки
        String k=""; //пустая строка
        for (int i=s.length()-1; i>=0; i--) {
            k+=s.charAt(i); //добавление к пустой строке символов строки s в обратном порядке
        }
        return k;
    }
    public static boolean isPalindrome(String s) { //функция проверки строки на палиндром
        if (s.equals(reverseString(s))==true) return true; else return false; //при совпадении строки с обратной ей - палиндром
    }
}
