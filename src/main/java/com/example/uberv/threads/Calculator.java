package com.example.uberv.threads;

import com.example.uberv.threads.com.example.uberv.utils.Logger;

/**
 * Creates and runs 10 threads that would calculate the prime numbers within the first 20,000 numbers.
 */
public class Calculator implements Runnable {

    @Override
    public void run() {
        long current = 1L;
        long max = 20000L;
        long numPrimes = 0L;

        Logger.d("Thread '%s': START", Thread.currentThread().getName());
        while (current <= max) {
            if (isPrime(current)) {
                numPrimes++;
            }
            current++;
        }
        Logger.d("Thread '%s': END. Number of Primes: %d", Thread.currentThread().getName(), numPrimes);
    }

    private boolean isPrime(long number) {
        if (number <= 2) {
            return true;
        }
        for (long i = 2; i < number; i++) {
            if ((number % i) == 0) {
                // has natural positive divider, not a prime number
                return false;
            }
        }
        return true;
    }
}
