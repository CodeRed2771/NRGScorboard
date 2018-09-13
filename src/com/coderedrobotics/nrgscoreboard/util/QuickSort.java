package com.coderedrobotics.nrgscoreboard.util;

/**
 * This class sorts an array of elements using the quick sort algorithm.
 *
 * Author Username: mdspoehr
 * Date Last Modified: 2/21/2017
 *
 * @author Michael Spoehr
 * @param <E> formal type being sorted
 */
public class QuickSort<E extends Comparable<E>> {

    /**
     * Sorts an array of elements using the quick sort algorithm.
     *
     * @param array Array to sort
     */
    public void sort(E[] array) {
        quickSort(array, 0, array.length - 1);
    }

    /**
     * Primary recursive method of the Quick Sort algorithm, responsible for
     * sorting the elements in the given array.
     *
     * @param array Array to be sorted
     * @param low Minimum value in the array to sort
     * @param high Maximum value in the array to sort
     */
    private void quickSort(E[] array, int low, int high) {
        if (low < high) {
            int pivot = partition(array, low, high);
            quickSort(array, low, pivot - 1);
            quickSort(array, pivot + 1, high);
        }
    }

    /**
     * Divides the data according to how they compare to a pivot, with elements
     * less than the pivot, then the pivot, then elements greater than the
     * pivot.
     *
     * @param array Array to partition
     * @param low Minimum value in the array to partition
     * @param high Maximum value in the array to partition
     * @return Index in the array of the pivot
     */
    private int partition(E[] array, int low, int high) {
        E pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j].compareTo(pivot) < 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    /**
     * Swap two elements in an array.
     *
     * @param array Array containing elements
     * @param i1 Index of one element to be swapped
     * @param i2 Index of another element to be swapped
     */
    private void swap(E[] array, int index1, int index2) {
        E tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }
}
