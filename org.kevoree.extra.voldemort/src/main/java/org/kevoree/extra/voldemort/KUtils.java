package org.kevoree.extra.voldemort;

import voldemort.cluster.Node;
import voldemort.utils.ByteArray;
import voldemort.versioning.VectorClock;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 17/04/12
 * Time: 14:07
 */
public class KUtils {

    public static final String DIGITS = "0123456789";
        public static final String LETTERS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        public static final String CHARACTERS = LETTERS + DIGITS + "~!@#$%^&*()____+-=[];',,,./>?:{}";
        public static final Random SEEDED_RANDOM = new Random(19873482374L);
        public static final Random UNSEEDED_RANDOM = new Random();

        /**
         * Get a vector clock with events on the sequence of nodes given So
         * getClock(1,1,2,2,2) means a clock that has two writes on node 1 and 3
         * writes on node 2.
         *
         * @param nodes The sequence of nodes
         * @return A VectorClock initialized with the given sequence of events
         */
        public static VectorClock getClock(int... nodes) {
            VectorClock clock = new VectorClock();
            increment(clock, nodes);
            return clock;
        }

        /**
         * Record events for the given sequence of nodes
         *
         * @param clock The VectorClock to record the events on
         * @param nodes The sequences of node events
         */
        public static void increment(VectorClock clock, int... nodes) {
            for(int n: nodes)
                clock.incrementVersion((short) n, System.currentTimeMillis());
        }

        /**
         * Test two byte arrays for (deep) equality. I think this exists in java 6
         * but not java 5
         *
         * @param a1 Array 1
         * @param a2 Array 2
         * @return True iff a1.length == a2.length and a1[i] == a2[i] for 0 <= i <
         *         a1.length
         */
        public static boolean bytesEqual(byte[] a1, byte[] a2) {
            if(a1 == a2) {
                return true;
            } else if(a1 == null || a2 == null) {
                return false;
            } else if(a1.length != a2.length) {
                return false;
            } else {
                for(int i = 0; i < a1.length; i++)
                    if(a1[i] != a2[i])
                        return false;
            }

            return true;
        }

        /**
         * Create a string with some random letters
         *
         * @param length The length of the string to create
         * @return The string
         */
        public static String randomLetters(int length) {
            return randomString(LETTERS, length);
        }

        /**
         * Create a string that is a random sample (with replacement) from the given
         * string
         *
         * @param sampler The string to sample from
         * @param length The length of the string to create
         * @return The created string
         */
        public static String randomString(String sampler, int length) {
            StringBuilder builder = new StringBuilder(length);
            for(int i = 0; i < length; i++)
                builder.append(sampler.charAt(SEEDED_RANDOM.nextInt(sampler.length())));
            return builder.toString();
        }

        /**
         * Generate an array of random bytes
         *
         * @param length
         * @return
         */
        public static byte[] randomBytes(int length) {
            byte[] bytes = new byte[length];
            SEEDED_RANDOM.nextBytes(bytes);
            return bytes;
        }

        /**
         * Return an array of length count containing random integers in the range
         * (0, max) generated off the test rng.
         *
         * @param max The bound on the random number size
         * @param count The number of integers to generate
         * @return The array of integers
         */
        public static int[] randomInts(int max, int count) {
            int[] vals = new int[count];
            for(int i = 0; i < count; i++)
                vals[i] = SEEDED_RANDOM.nextInt(max);
            return vals;
        }

        /**
         * Weirdly java doesn't seem to have Arrays.shuffle(), this terrible hack
         * does that.
         *
         * @return A shuffled copy of the input
         */
        public static int[] shuffle(int[] input) {
            List<Integer> vals = new ArrayList<Integer>(input.length);
            for(int i = 0; i < input.length; i++)
                vals.add(input[i]);
            Collections.shuffle(vals, SEEDED_RANDOM);
            int[] copy = new int[input.length];
            for(int i = 0; i < input.length; i++)
                copy[i] = vals.get(i);
            return copy;
        }

        /**
         * Compute the requested quantile of the given array
         *
         * @param values The array of values
         * @param quantile The quantile requested (must be between 0.0 and 1.0
         *        inclusive)
         * @return The quantile
         */
        public static long quantile(long[] values, double quantile) {
            if(values == null)
                throw new IllegalArgumentException("Values cannot be null.");
            if(quantile < 0.0 || quantile > 1.0)
                throw new IllegalArgumentException("Quantile must be between 0.0 and 1.0");

            long[] copy = new long[values.length];
            System.arraycopy(values, 0, copy, 0, copy.length);
            Arrays.sort(copy);
            int index = (int) (copy.length * quantile);
            return copy[index];
        }

        /**
         * Compute the mean of the given values
         *
         * @param values The values
         * @return The mean
         */
        public static double mean(long[] values) {
            double total = 0.0;
            for(int i = 0; i < values.length; i++)
                total += values[i];
            return total / values.length;
        }

        /**
         * Create a temporary directory in the directory given by java.io.tmpdir
         *
         * @return The directory created.
         */
        public static File createTempDir() {
            return createTempDir(new File(System.getProperty("java.io.tmpdir")));
        }

        /**
         * Create a temporary directory that is a child of the given directory
         *
         * @param parent The parent directory
         * @return The temporary directory
         */
        public static File createTempDir(File parent) {
            File temp = new File(parent,
                                 Integer.toString(Math.abs(UNSEEDED_RANDOM.nextInt()) % 1000000));
            temp.delete();
            temp.mkdir();
            temp.deleteOnExit();
            return temp;
        }

        /**
         * Wrap the given string in quotation marks. This is slightly more readable
         * then the java inline quotes that require escaping.
         *
         * @param s The string to wrap in quotes
         * @return The string
         */
        public static String quote(String s) {
            return "\"" + s + "\"";
        }

        public static List<Node> createNodes(int[][] partitionMap) {
            ArrayList<Node> nodes = new ArrayList<Node>(partitionMap.length);
            ArrayList<Integer> partitionList = new ArrayList<Integer>();

            for(int i = 0; i < partitionMap.length; i++) {
                partitionList.clear();
                for(int p = 0; p < partitionMap[i].length; p++) {
                    partitionList.add(partitionMap[i][p]);
                }
                nodes.add(new Node(i, "localhost", 8880 + i, 6666 + i, 7000 + i, partitionList));
            }

            return nodes;
        }



        /**
         * Always uses UTF-8.
         */
        public static ByteArray toByteArray(String s) {
            try {
                return new ByteArray(s.getBytes("UTF-8"));
            } catch(UnsupportedEncodingException e) {
                /* Should not happen */
                throw new IllegalStateException(e);
            }
        }


        /**
         * Because java.beans.ReflectionUtils isn't public...
         */

        @SuppressWarnings("unchecked")
        public static <T> T getPrivateValue(Object instance, String fieldName) throws Exception {
            Field eventDataQueueField = instance.getClass().getDeclaredField(fieldName);
            eventDataQueueField.setAccessible(true);
            return (T) eventDataQueueField.get(instance);
        }

}
