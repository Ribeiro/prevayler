// Prevayler, The Free-Software Prevalence Layer
// Copyright 2001-2006 by Klaus Wuestefeld
//
// This library is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.
//
// Prevayler is a trademark of Klaus Wuestefeld.
// See the LICENSE file for license details.

package org.prevayler.demos.demo1;

import org.prevayler.Prevayler;

/**
 * This is client code to the prevalent system. It does not need to be
 * persisted.
 */
class PrimeCalculator {

    private final Prevayler<NumberKeeper> _prevayler;

    PrimeCalculator(Prevayler<NumberKeeper> prevayler) {
        _prevayler = prevayler;
    }

    void start() throws Exception {
        int largestPrime = 0;
        int primesFound = 0;
        int lastNumber = _prevayler.execute(new LastNumberQuery());
        int primeCandidate = lastNumber == 0 ? 2 : lastNumber + 1;

        while (primeCandidate <= Integer.MAX_VALUE) {
            if (isPrime(primeCandidate)) {
                _prevayler.execute(new NumberStorageTransaction(primeCandidate));

                largestPrime = primeCandidate;
                primesFound = _prevayler.execute(new SizeQuery());
                System.out.println("Primes found: " + primesFound + ". Largest: " + largestPrime);
            }

            primeCandidate++;
        }
    }

    private boolean isPrime(int candidate) {
        /*
         * int factor = 2; candidate = candidate / 2; while (factor < candidate) {
         * if (candidate % factor == 0) return false; factor++; } return true;
         */

        if (candidate < 2) {
            return false;
        }
        if (candidate == 2) {
            return true;
        }
        if (candidate % 2 == 0) {
            return false;
        }

        int factor = 3;
        double square = Math.ceil(Math.sqrt(candidate));
        while (factor <= square) {
            if (candidate % factor == 0)
                return false;
            factor += 2;
        }
        return true;
    }

}