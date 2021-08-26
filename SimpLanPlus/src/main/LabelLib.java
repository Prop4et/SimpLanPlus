package main;

import ast.*;

public class LabelLib {
        // Singleton
        private static LabelLib instance = null;

        private static int labelCount = 0;

        /**
         * @return the singleton instance of {@code LabelManager}
         */
        public static LabelLib getInstance() {
            if(instance==null)
                instance = new LabelLib();
            return instance;
        }

        /**
         * Generates a new label, unique.
         *
         * @param key to prepend the unique code
         * @return a fresh label
         */
        public static String freshLabel(String key) {
            labelCount += 1;
            return key + labelCount;
        }
    }