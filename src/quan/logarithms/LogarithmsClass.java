package quan.logarithms;

import java.util.Scanner;

// 2^3 = 2*2*2 =8
// 3 is called an exponent(số mũ) and determines the number of times the base should be multiplied
// 8 is called the product
// Suppose exponent x is unknown? 2^x = 8
//If you only are interested in the exponent , the mathematical notation: x = log2(8)
// you pronounce(tuyên bố) it as: x =log base 2 of 8
// Exponentials x =2^3 and logarithms x = log2(8) are each other opposites.

// the goal of exponentials is to calculate the products: x = 2^3
//The goal of logarithms is to calculate the exponent: x = log2(8), 8 = 2^x
//


public class LogarithmsClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter value of Base:");
		double baseNumber = scanner.nextDouble();
		
		System.out.println("Enter the result:");
		double exponentNumber  = scanner.nextDouble();
		
		double result = Math.log(exponentNumber)/Math.log(baseNumber);
		System.out.println("exponent (số mủ) of base " + baseNumber + " of result " + exponentNumber + " is " + result);

	}

}
