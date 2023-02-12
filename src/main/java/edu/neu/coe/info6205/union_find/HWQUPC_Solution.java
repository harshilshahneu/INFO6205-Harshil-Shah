package edu.neu.coe.info6205.union_find;

import java.util.Random;
import java.util.Scanner;

public class HWQUPC_Solution {
    public static int count(int n) {
        UF_HWQUPC uf_hwqupc = new UF_HWQUPC(n, true);
        Random randomNumber = new Random();

        int count = 0;
        while(uf_hwqupc.components() > 1) {
            int num1 = randomNumber.nextInt(n);
            int num2 = randomNumber.nextInt(n);
            if(uf_hwqupc.connected(num1, num2)) {
                uf_hwqupc.union(num1, num2);
                count += 1;
            }
        }
        return count;
    }
    public static void main(String[] args) {
        for(int n = 1; n < 100000; n *= 10) {
            System.out.println("n = " + n + ", Number of connections(m) : " + count(n));
        }
    }
}
