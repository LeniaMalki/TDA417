package lab2;
import java.util.Arrays;

public class Autocomplete {
    public Term[] terms;

    // Initializes the data structure from the given array of terms.
    // Complexity: O(N log N), where N is the number of terms
    public Autocomplete(Term[] terms) {
        if(terms==null) {
            throw new NullPointerException("Terms can't be null");
        }

        this.terms = terms.clone();
        Arrays.sort(this.terms, Term.byPrefixOrder(terms.length));
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    // Complexity: O(log N + M log M), where M is the number of matching terms
    public Term[] allMatches(String prefix) {
        if(prefix==null) {
            throw new NullPointerException("Terms can't be null");
        }

        int firstIndex = RangeBinarySearch.firstIndexOf(this.terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        if (firstIndex == -1) { //If there is no match return
            return new Term[0];
        }

        int lastIndex  = RangeBinarySearch.lastIndexOf (this.terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        Term[] terms = new Term[lastIndex - firstIndex + 1];

        for (int i = 0; i < terms.length; i++) {
            terms[i] = this.terms[firstIndex++];
        }

        Arrays.sort(terms, Term.byReverseWeightOrder());

        return terms;
    }

    // Returns the number of terms that start with the given prefix.
    // Complexity: O(log N)
    public int numberOfMatches(String prefix) {
        if(prefix==null) {
            throw new NullPointerException("Terms can't be null");
        }

        int firstIndex = RangeBinarySearch.firstIndexOf(this.terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        int lastIndex  = RangeBinarySearch.lastIndexOf (this.terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        if (lastIndex < 0 || firstIndex < 0) return 0;
        return lastIndex-firstIndex + 1;
    }

    //Performance requirements
    //
    //The constructor should make proportional to N log N compares (or better) in the worst case, where N is the number of terms.
    //The allMatches() method should make proportional to log N + M log M compares (or better) in the worst case, where M is the number of matching terms.
    //The numberOfMatches() method should make proportional to log N compares (or better) in the worst case. It should not depend on M.
    //In this context, a compare is one call to the compare() method provided by any of the comparators defined in Term.
}
