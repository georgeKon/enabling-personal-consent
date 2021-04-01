package dbconsent;

import java.util.*;

/**
 * A useful class to ensure that the variables we parse have distinct names.
 * Produces names similar to spreadsheet software, ie a, b, c, ... , x, y, z, aa, ab, ac, ... , ax, ay, az, ba, bb, bc, ...
 *
 * We need this to prevent two non-equal variables accidentally being equated because of what we parsed.
 *
 */
public class PoolOfNames {

    /** The number of names we have given out. Used to calculate the next name. */
    private static int count = 0;
    /** The alphabet - used to calculate the next name */
    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Return the next string name, following the pattern a, b, c, ..., x, y, z, aa, ab, ac, ...
     * @return a String distinct from every other String returned by this class
     */
    public static String getName() {
        int index = count++;
        StringBuilder sb = new StringBuilder();
        while (index >= 0) {
            sb.append(alphabet.charAt(index % 26));
            index = (index / 26) -1;
        }
        return sb.reverse().toString();
    }

    /**
     * In place change the names of terms to names given out by the PoolOfNames
     * @param query the DatalogStatement that we want to give a safe namespace to
     * @throws HeadVariableNotInQueryException thrown if there is a variable in the head of the query that is not in the body
     */
    public static void safeNamespace(DatalogStatement query) throws HeadVariableNotInQueryException {
        //The representation of this query without modification, for in case something happens
        String cleanQuery = query.toString();

        //Map of Variable's old name to the Variable - we need this for updating the head!
        Map<String, Variable> namespace = new HashMap<>();
        //For each atom's distinct variables, get a new name for them,
        for (Atom atom : query.getAtoms()) {
            for (int index = 0; index < atom.numberOfTerms(); index++) {
                Term term = atom.getTerm(index);
                //A constant can't be renamed (and doesn't need it)
                if (term instanceof Constant) {
                    continue;
                }
                Variable unsafeVariable = (Variable)term;
                if (namespace.containsKey(unsafeVariable.getName())) {
                    //If we already have the variable's name, then it's a repeated variable, so
                    //we can clean up our instances and replace the reference.
                    atom.replaceTerm(index, namespace.get(unsafeVariable.getName()));
                } else {
                    //We've never seen this variable's name before, generate it a new name
                    String safeName = getName();
                    Variable safeVariable = new Variable(safeName);
                    namespace.put(unsafeVariable.getName(), safeVariable);
                    atom.replaceTerm(index, safeVariable);
                }
            }
        }
        
       //If there are rewritten constraints, rename them too
        if (!query.getConstraints().isEmpty()) {
        	for (RewrittenConstraint constraint : query.getConstraints()) {
        		// TODO: This is repeated from above; maybe refactor DS & RC?
        		for (Atom atom : constraint.getAtoms()) {
        			for (int index = 0; index < atom.numberOfTerms(); index++) {
                        Term term = atom.getTerm(index);
                        //A constant can't be renamed (and doesn't need it)
                        if (term instanceof Constant) {
                            continue;
                        }
                        Variable unsafeVariable = (Variable)term;
                        if (namespace.containsKey(unsafeVariable.getName())) {
                            //If we already have the variable's name, then it's a repeated variable, so
                            //we can clean up our instances and replace the reference.
                            atom.replaceTerm(index, namespace.get(unsafeVariable.getName()));
                        } else {
                            //We've never seen this variable's name before, generate it a new name
                            String safeName = getName();
                            Variable safeVariable = new Variable(safeName);
                            namespace.put(unsafeVariable.getName(), safeVariable);
                            atom.replaceTerm(index, safeVariable);
                        }
                    }
        		}
        		// Equalities must exist as variables, so should always be in namespace
        		for (Equality equality : constraint.getEqualities()) {
        		    Term newTerm1 = equality.getTerm1();
        			if (newTerm1 instanceof Variable) {
                        newTerm1 = namespace.get(((Variable)newTerm1).getName());
        			}
                    Term newTerm2 = equality.getTerm2();
                    if (newTerm2 instanceof Variable) {
                        newTerm2 = namespace.get(((Variable)newTerm2).getName());
        			}
        			equality.setTerms(newTerm1, newTerm2);
        		}
        	}
        }

        //Now we want to give the head the same new names
        List<Variable> headVariables = query.getHeadVariables();
        for(int i = 0; i < headVariables.size(); i++) {
            Variable unsafeVariable = headVariables.get(i);
            String unsafeName = unsafeVariable.getName();
            //By this point, we've gone through all body variables,
            //so if there is anything in the head variables that isn't in the new namespace,
            //then query doesn't make sense anyway (projecting something we didn't select)
            //This exception should probably never get thrown
            if(!namespace.containsKey(unsafeName)) {
                throw new HeadVariableNotInQueryException(unsafeName + " not found in body: " + cleanQuery);
            }

            //Replace the head variable with reference to the safe version
            headVariables.set(i, namespace.get(unsafeName));
        }
    }

    /**
     * This is dangerous! Resets the namespace, as if no names have been given!
     * Only use this if you are sure you won't be using existing objects!
     * (eg for Unit Testing)
     */
    public static void resetPool() {
        count = 0;
    }
}
