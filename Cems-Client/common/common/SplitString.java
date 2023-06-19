package common;

public int[] SplitString(String examString){

    String trimmedInput = input.substring(1, input.length() - 1);
    String[] elements = trimmedInput.split(",");
    int[] array = new int[elements.length];
    for (int i = 0; i < elements.length; i++) array[i] = Integer.parseInt(elements[i]);
    return array;
}