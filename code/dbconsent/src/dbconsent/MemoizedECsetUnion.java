package dbconsent;

import java.util.HashSet;
import java.util.Set;

/**
 * Different to the pseudocode, rather than performing n-1 unions of n ECsets from an n-tuple of the OpenAtomicECsets,
 * we can perform 1 union, by unioning an atomic element with the memoized union of an n-1 subtuple of the n-tuple.
 * This class represents that memoization - we store the combination of OpenAtomicECset indexes,
 * signifying which OpenAtomicECsets were unioned to produce the memoizedECset
 *
 */
public class MemoizedECsetUnion {

    /** The indexes of the OpenAtomicECset that we unioned to produce this memoizedECset*/
    Set<Integer> combination;

    /** The memoized EC set of the given combinations of OpenAtomicECsets */
    ECset memoizedECset;

    /**
     * The constructor for ECsets that have not been unioned
     * @param atomicIndex the index of the ECset
     * @param memoizedECset the ECset
     */
    public MemoizedECsetUnion(Integer atomicIndex, ECset memoizedECset) {
        this.combination = new HashSet<>();
        combination.add(atomicIndex);
        this.memoizedECset = memoizedECset;
    }

    /**
     * The constructor for an ECset that is produced by a union of some ECsets
     * @param combination the indexes of the ECsets that were combined to produce the ECset
     * @param memoizedECset the ECset that was produced by combining ECsets
     */
    public MemoizedECsetUnion(Set<Integer> combination, ECset memoizedECset) {
        this.combination = combination;
        this.memoizedECset = memoizedECset;
    }
}
