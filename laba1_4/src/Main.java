import java.lang.reflect.Array;

public class Main {

    // Створення одновимірного масиву
    public static Object createArray(Class<?> type, int length) {
        return Array.newInstance(type, length);
    }

    // Створення матриці
    public static Object createMatrix(Class<?> type, int rows, int columns) {
        return Array.newInstance(type, rows, columns);
    }

    // Зміна розміру масиву зі збереженням значень
    public static Object resizeArray(Object array, int newLength) {
        Class<?> type = array.getClass().getComponentType();
        Object newArray = Array.newInstance(type, newLength);
        int length = Math.min(Array.getLength(array), newLength);
        System.arraycopy(array, 0, newArray, 0, length);
        return newArray;
    }

    // Зміна розміру матриці зі збереженням значень
    public static Object resizeMatrix(Object matrix, int newRows, int newColumns) {
        Class<?> type = matrix.getClass().getComponentType().getComponentType();
        Object newMatrix = Array.newInstance(type, newRows, newColumns);
        int rows = Math.min(Array.getLength(matrix), newRows);
        for (int i = 0; i < rows; i++) {
            Object row = Array.get(matrix, i);
            int columns = Math.min(Array.getLength(row), newColumns);
            Object newRow = Array.newInstance(type, newColumns);
            System.arraycopy(row, 0, newRow, 0, columns);
            Array.set(newMatrix, i, newRow);
        }
        return newMatrix;
    }

    // Перетворення масиву на рядок
    public static String arrayToString(Object array) {
        StringBuilder builder = new StringBuilder();
        builder.append(array.getClass().getComponentType().getName())
                .append("[").append(Array.getLength(array)).append("] = {");
        for (int i = 0; i < Array.getLength(array); i++) {
            if (i > 0) builder.append(", ");
            builder.append(Array.get(array, i));
        }
        builder.append("}");
        return builder.toString();
    }

    // Перетворення матриці на рядок
    public static String matrixToString(Object matrix) {
        StringBuilder builder = new StringBuilder();
        builder.append(matrix.getClass().getComponentType().getComponentType().getName())
                .append("[").append(Array.getLength(matrix)).append("][").append(Array.getLength(Array.get(matrix, 0))).append("] = {");
        for (int i = 0; i < Array.getLength(matrix); i++) {
            if (i > 0) builder.append(", ");
            builder.append(arrayToString(Array.get(matrix, i)));
        }
        builder.append("}");
        return builder.toString();
    }

    public static void main(String[] args) {
        int[] intArray = (int[]) createArray(int.class, 2);
        System.out.println(arrayToString(intArray));

        String[][] stringMatrix = (String[][]) createMatrix(String.class, 3, 5);
        System.out.println(matrixToString(stringMatrix));

        Double[][] doubleMatrix = (Double[][]) createMatrix(Double.class, 5, 3);
        System.out.println(matrixToString(doubleMatrix));

        int[][] intMatrix = { {0, 1, 2, 3, 4}, {10, 11, 12, 13, 14}, {20, 21, 22, 23, 24} };
        System.out.println(matrixToString(intMatrix));

        intMatrix = (int[][]) resizeMatrix(intMatrix, 4, 6);
        System.out.println(matrixToString(intMatrix));

        intMatrix = (int[][]) resizeMatrix(intMatrix, 3, 7);
        System.out.println(matrixToString(intMatrix));

        intMatrix = (int[][]) resizeMatrix(intMatrix, 2, 2);
        System.out.println(matrixToString(intMatrix));
    }
}
